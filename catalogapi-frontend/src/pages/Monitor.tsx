import { useEffect, useState } from 'react'
import { fetchEventSource } from '@microsoft/fetch-event-source'
import Navbar from '../components/shared/Navbar'
import { useAuthStore } from '../store/authStore'

interface MonitorEvent {
  eventType: string
  payload: unknown
  occurredAt: string
}

const EVENT_COLORS: Record<string, string> = {
  'category.created': 'bg-green-100 text-green-800',
  'category.updated': 'bg-blue-100 text-blue-800',
  'category.deleted': 'bg-red-100 text-red-800',
  'product.created': 'bg-green-100 text-green-800',
  'product.updated': 'bg-blue-100 text-blue-800',
  'product.deleted': 'bg-red-100 text-red-800',
  'user.registered': 'bg-purple-100 text-purple-800',
  'user.logged_in': 'bg-yellow-100 text-yellow-800',
}

const EVENT_DOMAIN: Record<string, string> = {
  'category.created': 'Category',
  'category.updated': 'Category',
  'category.deleted': 'Category',
  'product.created': 'Product',
  'product.updated': 'Product',
  'product.deleted': 'Product',
  'user.registered': 'User',
  'user.logged_in': 'User',
}

export default function Monitor() {
  const [events, setEvents] = useState<MonitorEvent[]>([])
  const [connected, setConnected] = useState(false)
  const token = useAuthStore((state) => state.token)

useEffect(() => {
  const controller = new AbortController()

  fetchEventSource('/api/monitor', {
    headers: {
      Authorization: `Bearer ${token}`,
      Accept: 'text/event-stream',
    },
    signal: controller.signal,
    openWhenHidden: true,
    onopen: async (response) => {
      if (response.ok) {
        setConnected(true)
        return
      }
      throw new Error(`SSE connection failed: ${response.status}`)
    },
    onmessage: (msg) => {
      if (!msg.data || msg.data === '') return
      try {
        const data = JSON.parse(msg.data) as MonitorEvent
        setEvents((prev) => [data, ...prev].slice(0, 100))
      } catch {
        // ignora mensagens malformadas
      }
    },
    onclose: () => {
      setConnected(false)
    },
    onerror: (err) => {
      setConnected(false)
      console.error('SSE error:', err)
      // retorna um número para tentar reconectar após N ms
      // ou throw para parar completamente
      throw err
    },
  })

  return () => {
    controller.abort()
    setConnected(false)
  }
}, [token])

  const counts = events.reduce<Record<string, number>>((acc, e) => {
    const domain = EVENT_DOMAIN[e.eventType] ?? 'Unknown'
    acc[domain] = (acc[domain] ?? 0) + 1
    return acc
  }, {})

  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar />
      <div className="max-w-5xl mx-auto mt-10 p-6">
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-2xl font-bold">Event Monitor</h1>
          <span className={`flex items-center gap-2 text-sm px-3 py-1 rounded-full ${
            connected ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
          }`}>
            <span className={`w-2 h-2 rounded-full ${connected ? 'bg-green-500' : 'bg-red-500'}`} />
            {connected ? 'Connected' : 'Disconnected'}
          </span>
        </div>

        <div className="grid grid-cols-3 gap-4 mb-6">
          {['Category', 'Product', 'User'].map((domain) => (
            <div key={domain} className="bg-white rounded-lg shadow-md p-4">
              <p className="text-sm text-gray-500">{domain} Events</p>
              <p className="text-3xl font-bold mt-1">{counts[domain] ?? 0}</p>
            </div>
          ))}
        </div>

        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          {events.length === 0 ? (
            <p className="p-6 text-center text-gray-500">
              {connected ? 'Waiting for events...' : 'Connecting...'}
            </p>
          ) : (
            <table className="w-full">
              <thead className="bg-gray-50 border-b">
                <tr>
                  <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">Event</th>
                  <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">Payload</th>
                  <th className="text-left px-6 py-3 text-sm font-medium text-gray-500">Time</th>
                </tr>
              </thead>
              <tbody className="divide-y">
                {events.map((event, index) => (
                  <tr key={index} className="hover:bg-gray-50">
                    <td className="px-6 py-4">
                      <span className={`text-xs font-medium px-2 py-1 rounded-full ${
                        EVENT_COLORS[event.eventType] ?? 'bg-gray-100 text-gray-800'
                      }`}>
                        {event.eventType}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-500 font-mono">
                      {JSON.stringify(event.payload).slice(0, 80)}
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-500">
                      {new Date(event.occurredAt).toLocaleTimeString()}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  )
}
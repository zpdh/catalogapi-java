import Navbar from "../components/shared/Navbar";
import { useAuthStore } from "../store/authStore";

export default function Dashboard() {
    const { email, role } = useAuthStore();

    return (
        <div className="min-h-screen bg-gray-100">
            <Navbar />
            <div className="max-w-4xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-md">
                <h1 className="text-2xl font-bold mb-2">Welcome, {email}</h1>
                <p className="text-gray-500">
                    Role: <span className="font-medium">{role}</span>
                </p>
                <p className="mt-4 text-gray-600">
                    Use the navigation above to manage categories and products.
                </p>
            </div>
        </div>
    );
}

package com.zpdh.CatalogApi.domain.monitor;

import com.zpdh.CatalogApi.shared.messaging.SseEmitterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/monitor")
public class MonitorController {
    private final SseEmitterRegistry sseEmitterRegistry;

    public MonitorController(SseEmitterRegistry sseEmitterRegistry) {
        this.sseEmitterRegistry = sseEmitterRegistry;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return sseEmitterRegistry.register();
    }
}

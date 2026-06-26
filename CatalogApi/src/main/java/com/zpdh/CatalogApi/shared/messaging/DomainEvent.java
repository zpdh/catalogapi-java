package com.zpdh.CatalogApi.shared.messaging;

import com.zpdh.CatalogApi.shared.messaging.payload.EventPayload;

public record DomainEvent<E extends EventPayload>(String eventType, E payload, String occurredAt) {

}

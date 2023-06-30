package com.bobbbaich.reward.service.impl;

import com.bobbbaich.reward.dto.EventBridgeEntry;
import com.bobbbaich.reward.service.EventBridgeProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.eventbridge.EventBridgeAsyncClient;
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventBridgeAsyncProducer implements EventBridgeProducer {

    @Value("${service.event-bus.name}")
    private String eventBusName;

    @Value("${spring.application.name}")
    private String serviceName;

    private final EventBridgeAsyncClient eventBridgeAsyncClient;
    private final ObjectMapper objectMapper;

    @Override
    public <T> void send(@Valid EventBridgeEntry<T> entryDTO) {
        PutEventsRequestEntry entry = getEntry(entryDTO);
        eventBridgeAsyncClient
                .putEvents(builder -> builder.entries(entry))
                .thenRun(() -> log.debug("sent event payload={}", entryDTO));
    }

    private <T> PutEventsRequestEntry getEntry(EventBridgeEntry<T> entry) {
        return PutEventsRequestEntry.builder()
                .eventBusName(eventBusName)
                .source(serviceName)
                .detailType(entry.getType().name())
                .detail(getPayloadAsJson(entry.getPayload()))
                .build();
    }

    private <T> String getPayloadAsJson(T payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(format("Error converting payload=%s", payload), e);
        }
    }
}

package com.bobbbaich.reward.service;

import com.bobbbaich.reward.dto.EventBridgeEntry;
import jakarta.validation.Valid;

public interface EventBridgeProducer {
    <T> void send(@Valid EventBridgeEntry<T> entryDTO);
}

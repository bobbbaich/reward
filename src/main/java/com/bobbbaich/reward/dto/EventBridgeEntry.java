package com.bobbbaich.reward.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventBridgeEntry<T> {
    @NotNull
    private Type type;

    @NotNull
    @Valid
    private T payload;

    public enum Type {
        REWARD_LIFECYCLE
    }
}

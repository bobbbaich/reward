package com.bobbbaich.reward.dto;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftDTO {
    private UUID uuid;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}

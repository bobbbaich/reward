package com.bobbbaich.reward.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftDTO {
    private UUID uuid;
    private String name;
    private AuditMetadataDTO auditMetadata;
}

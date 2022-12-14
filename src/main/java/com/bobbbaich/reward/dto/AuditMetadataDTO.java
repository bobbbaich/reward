package com.bobbbaich.reward.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AuditMetadataDTO {
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

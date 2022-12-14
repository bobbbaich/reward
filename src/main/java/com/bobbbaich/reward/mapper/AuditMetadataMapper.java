package com.bobbbaich.reward.mapper;

import com.bobbbaich.reward.domain.AuditMetadata;
import com.bobbbaich.reward.dto.AuditMetadataDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMetadataMapper {

    AuditMetadataDTO map(AuditMetadata auditMetadata);
}

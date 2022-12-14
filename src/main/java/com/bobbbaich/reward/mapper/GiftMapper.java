package com.bobbbaich.reward.mapper;

import com.bobbbaich.reward.domain.Gift;
import com.bobbbaich.reward.dto.CreateGiftDTO;
import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.dto.UpdateGiftDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AuditMetadataMapper.class})
public interface GiftMapper {

    GiftDTO map(Gift gift);

    Gift map(CreateGiftDTO createDTO);

    void map(@MappingTarget Gift gift, UpdateGiftDTO createDTO);
}

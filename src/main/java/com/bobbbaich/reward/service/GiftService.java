package com.bobbbaich.reward.service;

import com.bobbbaich.reward.dto.CreateGiftDTO;
import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.dto.UpdateGiftDTO;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GiftService {

    GiftDTO create(CreateGiftDTO createDTO);

    @Observed
    Page<GiftDTO> readAll(Pageable pageable);

    GiftDTO read(UUID uuid);

    GiftDTO update(UUID uuid, UpdateGiftDTO updateDTO);

    void delete(UUID uuid);
}

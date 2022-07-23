package com.bobbbaich.reward.service.impl;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.bobbbaich.reward.domain.Gift;
import com.bobbbaich.reward.dto.CreateGiftDTO;
import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.dto.UpdateGiftDTO;
import com.bobbbaich.reward.exception.NotFoundException;
import com.bobbbaich.reward.mapper.GiftMapper;
import com.bobbbaich.reward.repository.GiftRepository;
import com.bobbbaich.reward.service.GiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@XRayEnabled
@Service
public class GiftServiceImpl implements GiftService {

    private final GiftMapper giftMapper;
    private final GiftRepository giftRepository;

    @Override
    @Transactional
    public GiftDTO create(CreateGiftDTO createDTO) {
        log.debug("create gift createDTO={}", createDTO);
        Gift gift = giftMapper.map(createDTO);
        gift = giftRepository.save(gift);
        log.debug("gift created gift={}", gift);
        return giftMapper.map(gift);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GiftDTO> readAll(Pageable pageRequest) {
        log.debug("read all gifts pageRequest={}", pageRequest);
        return giftRepository.findAll(pageRequest).map(giftMapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public GiftDTO read(UUID uuid) {
        Gift gift = readByUuidOrThrow(uuid);
        return giftMapper.map(gift);
    }

    @Override
    @Transactional
    public GiftDTO update(UUID uuid, UpdateGiftDTO updateDTO) {
        log.debug("update gift updateDTO={}", updateDTO);
        Gift gift = readByUuidOrThrow(uuid);
        giftMapper.map(gift, updateDTO);
        gift = giftRepository.save(gift);
        log.debug("gift updated gift={}", gift);
        return giftMapper.map(gift);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        log.debug("delete gift uuid={}", uuid);
        giftRepository.deleteByUuid(uuid);
    }

    private Gift readByUuidOrThrow(UUID uuid) {
        return readByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(format("Gift with uuid=%s does not exist", uuid)));
    }

    private Optional<Gift> readByUuid(UUID uuid) {
        log.debug("read gift uuid={}", uuid);
        return giftRepository.findOneByUuid(uuid);
    }
}

package com.bobbbaich.reward.controller;

import com.bobbbaich.reward.dto.CreateGiftDTO;
import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.dto.UpdateGiftDTO;
import com.bobbbaich.reward.service.GiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gifts")
public class GiftController {

    private final GiftService giftService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(CREATED)
    public GiftDTO create(@Valid @RequestBody CreateGiftDTO createDTO) {
        return giftService.create(createDTO);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public Page<GiftDTO> readAll(@PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable) {
        return giftService.readAll(pageable);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public GiftDTO read(@Valid @PathVariable UUID uuid) {
        return giftService.read(uuid);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public GiftDTO update(@PathVariable UUID uuid, @Valid @RequestBody UpdateGiftDTO updateDTO) {
        return giftService.update(uuid, updateDTO);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID uuid) {
        giftService.delete(uuid);
    }
}

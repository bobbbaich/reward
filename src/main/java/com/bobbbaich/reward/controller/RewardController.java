package com.bobbbaich.reward.controller;

import com.bobbbaich.reward.dto.RewardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/rewards/gifts")
public class RewardController {

    @GetMapping
    public List<RewardDTO> read() {

        log.info("read rewards time={}", ZonedDateTime.now());
        log.info("system env={}", System.getenv());
        return List.of(
                new RewardDTO(UUID.randomUUID().toString(), "Flower"),
                new RewardDTO(UUID.randomUUID().toString(), "Macbook")
        );
    }
}

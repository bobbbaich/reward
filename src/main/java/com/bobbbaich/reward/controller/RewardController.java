package com.bobbbaich.reward.controller;

import com.bobbbaich.reward.dto.RewardDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reward")
public class RewardController {

    @GetMapping
    public List<RewardDTO> read() {
        return List.of(
                new RewardDTO("Flower"),
                new RewardDTO("Macbook")
        );
    }
}

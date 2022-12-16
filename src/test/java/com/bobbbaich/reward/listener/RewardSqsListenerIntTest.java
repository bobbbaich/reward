package com.bobbbaich.reward.listener;

import com.bobbbaich.reward.dto.GiftDTO;
import com.bobbbaich.reward.service.impl.SqsAsyncProducer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@ExtendWith({OutputCaptureExtension.class})
public class RewardSqsListenerIntTest extends SqsIntegrationTest {

    private static final String REWARD_CREATED_QUEUE = "reward-created";

    @Autowired
    private SqsAsyncProducer sqsProducer;

    @Test
    void testOnRewardCreatedEventConsumed(CapturedOutput output) {
        GiftDTO giftDTO = getGiftDTO();

        sqsProducer.send(REWARD_CREATED_QUEUE, giftDTO);

        await()
                .atMost(Duration.ofSeconds(3))
                .untilAsserted(() -> assertTrue(output.getAll().contains(giftDTO.getUuid().toString())));
    }

    @NotNull
    private GiftDTO getGiftDTO() {
        GiftDTO giftDTO = new GiftDTO();
        giftDTO.setUuid(UUID.randomUUID());
        return giftDTO;
    }
}

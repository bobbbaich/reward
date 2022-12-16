package com.bobbbaich.reward.listener.sqs;

import com.bobbbaich.reward.dto.GiftDTO;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RewardSqsListener {

    @SqsListener("${service.listener.sqs.reward}")
    public void onRewardCreated(Message<GiftDTO> giftDTO) {
        log.debug("on reward created giftDTO={}", giftDTO);
    }
}

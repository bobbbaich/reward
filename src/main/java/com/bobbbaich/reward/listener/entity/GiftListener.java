package com.bobbbaich.reward.listener.entity;

import com.bobbbaich.reward.domain.Gift;
import com.bobbbaich.reward.service.SqsProducer;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GiftListener {

    @Value("${service.sqs.gift-lifecycle}")
    private String rewardLifecycleQueue;

    private SqsProducer sqsProducer;

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(Gift gift) {
        log.debug("gift insert/update/delete completed uuid={}", gift.getUuid());
        sqsProducer.send(rewardLifecycleQueue, gift);
    }

    @Autowired
    public void setSqsProducer(SqsProducer sqsProducer) {
        this.sqsProducer = sqsProducer;
    }
}

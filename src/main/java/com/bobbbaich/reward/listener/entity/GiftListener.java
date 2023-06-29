package com.bobbbaich.reward.listener.entity;

import com.bobbbaich.reward.domain.Gift;
import com.bobbbaich.reward.dto.EventBridgeEntry;
import com.bobbbaich.reward.mapper.GiftMapper;
import com.bobbbaich.reward.service.EventBridgeProducer;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.bobbbaich.reward.dto.EventBridgeEntry.Type.REWARD_LIFECYCLE;

@Slf4j
@NoArgsConstructor
@Component
public class GiftListener {

//    @Value("${service.sqs.gift-lifecycle}")
//    private String rewardLifecycleQueue;

    //    private SqsProducer sqsProducer;
    private EventBridgeProducer eventBridgeProducer;
    private GiftMapper giftMapper;

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(Gift gift) {
        log.debug("gift insert/update/delete completed uuid={}", gift.getUuid());
//        sqsProducer.send(rewardLifecycleQueue, gift);
        eventBridgeProducer.send(EventBridgeEntry.builder()
                .payload(giftMapper.map(gift))
                .type(REWARD_LIFECYCLE)
                .build());
    }

    @Autowired
    public void setEventBridgeProducer(EventBridgeProducer eventBridgeProducer) {
        this.eventBridgeProducer = eventBridgeProducer;
    }

    @Autowired
    public void setGiftMapper(GiftMapper giftMapper) {
        this.giftMapper = giftMapper;
    }

//    @Autowired
//    public void setSqsProducer(SqsProducer sqsProducer) {
//        this.sqsProducer = sqsProducer;
//    }
}

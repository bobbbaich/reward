package com.bobbbaich.reward.repository;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.bobbbaich.reward.domain.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@XRayEnabled
@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {

    void deleteByUuid(UUID uuid);

    Optional<Gift> findOneByUuid(UUID uuid);
}

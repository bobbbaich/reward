package com.bobbbaich.reward.domain;


import com.bobbbaich.reward.validation.GiftNameConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.UUID.randomUUID;


@Data
@Entity
@DynamicInsert
@Table(name = "gifts")
@EntityListeners(AuditingEntityListener.class)
public class Gift {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "uuid", updatable = false)
    private UUID uuid = randomUUID();

    @GiftNameConstraint
    @Column(name = "name")
    private String name;

    @Embedded
    private AuditMetadata auditMetadata = new AuditMetadata();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gift gift = (Gift) o;
        return id.equals(gift.id) && name.equals(gift.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

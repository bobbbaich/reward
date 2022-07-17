package com.bobbbaich.reward.domain;


import com.bobbbaich.reward.validation.GiftNameConstraint;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.IDENTITY;


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

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

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

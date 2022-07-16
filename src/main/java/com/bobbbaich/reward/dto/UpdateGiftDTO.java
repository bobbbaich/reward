package com.bobbbaich.reward.dto;

import com.bobbbaich.reward.validation.GiftNameConstraint;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGiftDTO {

    @GiftNameConstraint
    private String name;
}

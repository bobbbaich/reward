package com.bobbbaich.reward.dto;

import com.bobbbaich.reward.validation.GiftNameConstraint;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGiftDTO {

    @GiftNameConstraint
    private String name;
}

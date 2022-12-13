package com.bobbbaich.reward.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NotBlank
@Size(min = 1, max = 255)
public @interface GiftNameConstraint {
}

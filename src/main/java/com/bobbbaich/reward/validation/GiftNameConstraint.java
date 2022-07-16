package com.bobbbaich.reward.validation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NotBlank
@Size(min = 1, max = 255)
public @interface GiftNameConstraint {
}

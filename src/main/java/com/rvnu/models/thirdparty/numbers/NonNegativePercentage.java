package com.rvnu.models.thirdparty.numbers;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class NonNegativePercentage extends NonNegativeDecimal {
    public static class ValueCannotBeGreaterThan100 extends Exception {

    }

    @NotNull
    private static final BigDecimal inclusiveMaximumValue = BigDecimal.valueOf(100);

    public NonNegativePercentage(@NotNull final BigDecimal value) throws ValueCannotBeNegative, ValueCannotBeGreaterThan100 {
        super(value);

        if (0 < value.compareTo(inclusiveMaximumValue)) {
            throw new ValueCannotBeGreaterThan100();
        }
    }
}

package com.rvnu.models.thirdparty.numbers;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class NonNegativeDecimal {
    public static class ValueCannotBeNegative extends Exception {
    }

    @NotNull
    private final BigDecimal value;

    public NonNegativeDecimal(@NotNull final BigDecimal value) throws ValueCannotBeNegative {
        if (0 > value.compareTo(BigDecimal.ZERO)) {
            throw new ValueCannotBeNegative();
        }

        this.value = value;
    }

    @NotNull
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonNegativeDecimal that = (NonNegativeDecimal) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

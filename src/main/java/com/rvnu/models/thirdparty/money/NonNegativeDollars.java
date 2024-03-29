package com.rvnu.models.thirdparty.money;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NonNegativeDollars {
    public static final NonNegativeDollars ZERO = new NonNegativeDollars(NaturalNumber.ZERO);

    @NotNull
    private final Money value;

    public NonNegativeDollars(@NotNull final NaturalNumber value) {
        this.value = Money.of(value.getValue(), "USD");
    }

    @NotNull
    public Money getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonNegativeDollars that = (NonNegativeDollars) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

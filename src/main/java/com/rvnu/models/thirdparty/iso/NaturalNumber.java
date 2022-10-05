package com.rvnu.models.thirdparty.iso;

import java.util.Objects;

public class NaturalNumber {
    public static class ValueMustNotBeNegative extends Exception {
    }

    private final long value;

    public NaturalNumber(final long value) throws ValueMustNotBeNegative {
        if (0 > value) {
            throw new ValueMustNotBeNegative();
        }
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NaturalNumber that = (NaturalNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "NaturalNumber{" +
                "value=" + value +
                '}';
    }
}

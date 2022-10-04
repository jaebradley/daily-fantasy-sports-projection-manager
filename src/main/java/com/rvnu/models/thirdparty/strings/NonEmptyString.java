package com.rvnu.models.thirdparty.strings;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NonEmptyString {
    public static class ValueMustNotBeEmpty extends Exception {
    }

    @NotNull
    private final String value;

    public NonEmptyString(@NotNull final String value) throws ValueMustNotBeEmpty {
        if (value.isEmpty()) {
            throw new ValueMustNotBeEmpty();
        }
        this.value = value;
    }

    @NotNull
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonEmptyString that = (NonEmptyString) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

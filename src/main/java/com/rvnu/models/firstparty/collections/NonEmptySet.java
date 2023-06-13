package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public abstract class NonEmptySet<T> {
    public static class ValuesMustNotBeEmpty extends Exception {
    }

    @NotNull
    private final Set<T> values;

    protected NonEmptySet(@NotNull final Set<T> values) throws ValuesMustNotBeEmpty {
        this.values = values;

        if (this.values.isEmpty()) {
            throw new ValuesMustNotBeEmpty();
        }
    }

    @NotNull
    public Set<T> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonEmptySet<?> that = (NonEmptySet<?>) o;
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}

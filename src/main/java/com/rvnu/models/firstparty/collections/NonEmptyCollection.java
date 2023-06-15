package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public abstract class NonEmptyCollection<E, C extends Collection<E>> {
    public static class ValuesCannotBeEmpty extends Exception {
    }

    @NotNull
    protected final C values;

    public NonEmptyCollection(@NotNull final C values) throws ValuesCannotBeEmpty {
        if (values.isEmpty()) {
            throw new ValuesCannotBeEmpty();
        }
        this.values = values;
    }

    @NotNull
    public C getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonEmptyCollection<?, ?> that = (NonEmptyCollection<?, ?>) o;
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }
}

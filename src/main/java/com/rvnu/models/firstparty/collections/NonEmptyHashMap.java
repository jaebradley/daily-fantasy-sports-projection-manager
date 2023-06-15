package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NonEmptyHashMap<K, V> {
    @NotNull
    private final HashMap<K, V> values;

    public NonEmptyHashMap(@NotNull final Map<K, V> values) throws NonEmptyCollection.ValuesCannotBeEmpty {
        if (values.isEmpty()) {
            throw new NonEmptyCollection.ValuesCannotBeEmpty();
        }

        this.values = new HashMap<>(values);
    }

    @NotNull
    public HashMap<K, V> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NonEmptyHashMap<?, ?> that = (NonEmptyHashMap<?, ?>) o;
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), values);
    }
}

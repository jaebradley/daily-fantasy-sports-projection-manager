package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedHashSet;

public class NonEmptyLinkedHashSet<T> extends LinkedHashSet<T> {
    private NonEmptyLinkedHashSet(@NotNull final Collection<? extends T> c) throws CollectionCannotBeEmpty {
        super(c);

        if (c.isEmpty()) {
            throw new CollectionCannotBeEmpty();
        }
    }

    public static class Builder<T> {
        @NotNull
        private final LinkedHashSet<T> values;

        public Builder() {
            this.values = new LinkedHashSet<>();
        }

        public boolean add(@NotNull final T value) {
            return this.values.add(value);
        }

        public boolean addAll(@NotNull final Collection<T> values) {
            return this.values.addAll(values);
        }

        public NonEmptyLinkedHashSet<T> build() throws CollectionCannotBeEmpty {
            return new NonEmptyLinkedHashSet<>(this.values);
        }
    }

    public static <T> NonEmptyLinkedHashSet<T> from(@NotNull final Collection<T> values) throws CollectionCannotBeEmpty {
        final Builder<T> builder = new Builder<>();
        builder.addAll(values);
        return builder.build();
    }

    public static class CollectionCannotBeEmpty extends Exception {
    }
}

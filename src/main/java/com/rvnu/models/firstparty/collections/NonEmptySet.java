package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class NonEmptySet<E> extends NonEmptyCollection<E, Set<E>> {
    public NonEmptySet(@NotNull final Set<E> values) throws ValuesCannotBeEmpty {
        super(values);
    }
}

package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NonEmptyList<E> extends NonEmptyCollection<E, List<E>> {
    public NonEmptyList(@NotNull final List<E> values) throws ValuesCannotBeEmpty {
        super(values);
    }
}

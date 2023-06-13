package com.rvnu.models.firstparty.collections;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetOf3<T> extends NonEmptySet<T> {
    public static class ExpectedThreeDistinctValues extends Exception {
    }

    public SetOf3(
            @NotNull final T first,
            @NotNull final T second,
            @NotNull final T third) throws ExpectedThreeDistinctValues, ValuesMustNotBeEmpty {
        super(Stream.of(first, second, third).collect(Collectors.toUnmodifiableSet()));

        if (3 != Stream.of(first, second, third).collect(Collectors.toUnmodifiableSet()).size()) {
            throw new ExpectedThreeDistinctValues();
        }
    }
}

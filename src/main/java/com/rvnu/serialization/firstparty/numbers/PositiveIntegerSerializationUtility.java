package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class PositiveIntegerSerializationUtility<T extends PositiveInteger> implements Deserializer<T> {
    @NotNull
    private static final PositiveIntegerSerializationUtility<PositiveInteger> DEFAULT_INSTANCE = new PositiveIntegerSerializationUtility<>() {
        @Override
        protected PositiveInteger construct(@NotNull final PositiveInteger value) {
            return value;
        }
    };

    protected PositiveIntegerSerializationUtility() {
    }

    @Override
    public Optional<T> deserialize(@NotNull final String value) {
        final PositiveInteger integer;
        try {
            integer = new PositiveInteger(Long.parseLong(value));
        } catch (NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive | NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(construct(integer));
    }

    protected abstract T construct(@NotNull final PositiveInteger value);

    @NotNull
    public static PositiveIntegerSerializationUtility<PositiveInteger> getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }
}

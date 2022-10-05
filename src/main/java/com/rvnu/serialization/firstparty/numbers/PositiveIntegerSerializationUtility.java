package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PositiveIntegerSerializationUtility implements Deserializer<PositiveInteger> {
    @NotNull
    private static final PositiveIntegerSerializationUtility INSTANCE = new PositiveIntegerSerializationUtility();

    private PositiveIntegerSerializationUtility() {
    }

    @Override
    public Optional<PositiveInteger> deserialize(@NotNull final String value) {
        final PositiveInteger integer;
        try {
            integer = new PositiveInteger(Long.parseLong(value));
        } catch (NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive | NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(integer);
    }

    @NotNull
    public static PositiveIntegerSerializationUtility getInstance() {
        return INSTANCE;
    }
}

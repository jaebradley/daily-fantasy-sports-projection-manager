package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public class NonNegativeDecimalSerializationUtility implements Deserializer<NonNegativeDecimal> {
    @NotNull
    private static final NonNegativeDecimalSerializationUtility INSTANCE = new NonNegativeDecimalSerializationUtility(
            BigDecimalSerializationUtility.getInstance()
    );

    @NotNull
    private final Deserializer<BigDecimal> decimalDeserializer;

    private NonNegativeDecimalSerializationUtility(@NotNull final Deserializer<BigDecimal> decimalDeserializer) {
        this.decimalDeserializer = decimalDeserializer;
    }

    @Override
    public Optional<NonNegativeDecimal> deserialize(@NotNull final String value) {
        return decimalDeserializer
                .deserialize(value)
                .flatMap(v -> {
                    try {
                        return Optional.of(new NonNegativeDecimal(v));
                    } catch (NonNegativeDecimal.ValueCannotBeNegative e) {
                        return Optional.empty();
                    }
                });
    }

    public static NonNegativeDecimalSerializationUtility getInstance() {
        return INSTANCE;
    }
}

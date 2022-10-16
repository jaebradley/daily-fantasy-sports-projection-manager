package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public abstract class NonNegativeDecimalSerializationUtility<T extends NonNegativeDecimal> implements Deserializer<T> {
    @NotNull
    private static final NonNegativeDecimalSerializationUtility<NonNegativeDecimal> DEFAULT_INSTANCE = new NonNegativeDecimalSerializationUtility<NonNegativeDecimal>(
            BigDecimalSerializationUtility.getInstance()
    ) {
        @Override
        protected Optional<NonNegativeDecimal> construct(@NotNull final NonNegativeDecimal value) {
            return Optional.of(value);
        }
    };

    @NotNull
    private final Deserializer<BigDecimal> decimalDeserializer;

    protected NonNegativeDecimalSerializationUtility(@NotNull final Deserializer<BigDecimal> decimalDeserializer) {
        this.decimalDeserializer = decimalDeserializer;
    }

    @Override
    public Optional<T> deserialize(@NotNull final String value) {
        return decimalDeserializer
                .deserialize(value)
                .flatMap(v -> {
                    try {
                        return Optional.of(new NonNegativeDecimal(v));
                    } catch (NonNegativeDecimal.ValueCannotBeNegative e) {
                        return Optional.empty();
                    }
                }).flatMap(this::construct);
    }

    protected abstract Optional<T> construct(@NotNull final NonNegativeDecimal value);

    public static NonNegativeDecimalSerializationUtility<NonNegativeDecimal> getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }
}

package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public class BigDecimalSerializationUtility implements Deserializer<BigDecimal>, Serializer<BigDecimal> {
    private static final BigDecimalSerializationUtility INSTANCE = new BigDecimalSerializationUtility();

    private BigDecimalSerializationUtility() {
    }

    @Override
    public Optional<BigDecimal> deserialize(@NotNull final String value) {
        final BigDecimal v;
        try {
            v = new BigDecimal(value);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        return Optional.of(v);
    }

    public static BigDecimalSerializationUtility getInstance() {
        return INSTANCE;
    }

    @Override
    public String serialize(@NotNull final BigDecimal value) {
        return value.toPlainString();
    }
}

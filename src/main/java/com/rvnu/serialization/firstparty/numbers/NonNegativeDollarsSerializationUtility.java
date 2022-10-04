package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NonNegativeDollarsSerializationUtility implements Deserializer<NonNegativeDollars> {
    @NotNull
    private static final NonNegativeDollarsSerializationUtility INSTANCE = new NonNegativeDollarsSerializationUtility(
            NonNegativeDecimalSerializationUtility.getInstance()
    );

    @NotNull
    private final NonNegativeDecimalSerializationUtility decimalSerializationUtility;

    private NonNegativeDollarsSerializationUtility(@NotNull final NonNegativeDecimalSerializationUtility decimalSerializationUtility) {
        this.decimalSerializationUtility = decimalSerializationUtility;
    }

    @Override
    public Optional<NonNegativeDollars> deserialize(@NotNull final String value) {
        return decimalSerializationUtility
                .deserialize(value)
                .map(NonNegativeDollars::new);
    }

    @NotNull
    public static NonNegativeDollarsSerializationUtility getInstance() {
        return INSTANCE;
    }
}

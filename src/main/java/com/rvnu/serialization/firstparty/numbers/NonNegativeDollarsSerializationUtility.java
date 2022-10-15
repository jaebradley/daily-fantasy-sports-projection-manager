package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NonNegativeDollarsSerializationUtility implements Deserializer<NonNegativeDollars> {
    @NotNull
    private static final NonNegativeDollarsSerializationUtility INSTANCE = new NonNegativeDollarsSerializationUtility(
            NaturalNumberSerializationUtility.getDefaultInstance()
    );

    @NotNull
    private final Deserializer<NaturalNumber> valueDeserializer;

    private NonNegativeDollarsSerializationUtility(@NotNull final Deserializer<NaturalNumber> valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    @Override
    public Optional<NonNegativeDollars> deserialize(@NotNull final String value) {
        return valueDeserializer
                .deserialize(value)
                .map(NonNegativeDollars::new);
    }

    @NotNull
    public static NonNegativeDollarsSerializationUtility getInstance() {
        return INSTANCE;
    }
}

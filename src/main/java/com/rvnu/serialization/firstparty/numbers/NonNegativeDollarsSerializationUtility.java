package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NonNegativeDollarsSerializationUtility implements Deserializer<NonNegativeDollars>, Serializer<NonNegativeDollars> {
    @NotNull
    private static final NonNegativeDollarsSerializationUtility INSTANCE = new NonNegativeDollarsSerializationUtility(
            NaturalNumberSerializationUtility.getDefaultInstance(),
            NaturalNumberSerializationUtility.getDefaultInstance()
    );

    @NotNull
    private final Deserializer<NaturalNumber> valueDeserializer;

    @NotNull
    private final Serializer<NaturalNumber> valueSerializer;

    private NonNegativeDollarsSerializationUtility(@NotNull final Deserializer<NaturalNumber> valueDeserializer, @NotNull final Serializer<NaturalNumber> valueSerializer) {
        this.valueDeserializer = valueDeserializer;
        this.valueSerializer = valueSerializer;
    }

    @Override
    public Optional<NonNegativeDollars> deserialize(@NotNull final String value) {
        return valueDeserializer
                .deserialize(value)
                .map(NonNegativeDollars::new);
    }

    @Override
    public String serialize(@NotNull final NonNegativeDollars value) {
        try {
            return valueSerializer.serialize(new NaturalNumber(value.getValue().getNumber().longValue()));
        } catch (NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    @NotNull
    public static NonNegativeDollarsSerializationUtility getInstance() {
        return INSTANCE;
    }
}

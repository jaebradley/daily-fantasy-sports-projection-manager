package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.numbers.PositiveIntegerSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlayerIdSerializationUtility implements Deserializer<PlayerId> {
    @NotNull
    private static final PlayerIdSerializationUtility INSTANCE = new PlayerIdSerializationUtility(
            PositiveIntegerSerializationUtility.getInstance()
    );

    @NotNull
    private final Deserializer<PositiveInteger> positiveIntegerDeserializer;

    private PlayerIdSerializationUtility(@NotNull final Deserializer<PositiveInteger> positiveIntegerDeserializer) {
        this.positiveIntegerDeserializer = positiveIntegerDeserializer;
    }

    @Override
    public Optional<PlayerId> deserialize(@NotNull final String value) {
        return positiveIntegerDeserializer.deserialize(value)
                .map(PositiveInteger::getValue)
                .flatMap(v -> {
                    try {
                        return Optional.of(new PlayerId(v));
                    } catch (NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive e) {
                        throw new RuntimeException("unexpected", e);
                    }
                });
    }

    @NotNull
    public static PlayerIdSerializationUtility getInstance() {
        return INSTANCE;
    }
}

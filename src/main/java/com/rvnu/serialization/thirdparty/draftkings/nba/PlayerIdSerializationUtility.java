package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.numbers.PositiveIntegerSerializationUtility;
import org.jetbrains.annotations.NotNull;

public class PlayerIdSerializationUtility extends PositiveIntegerSerializationUtility<PlayerId> {
    @NotNull
    private static final PlayerIdSerializationUtility INSTANCE = new PlayerIdSerializationUtility();

    @Override
    protected final PlayerId construct(@NotNull final PositiveInteger value) {
        try {
            return new PlayerId(value.getValue());
        } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    @NotNull
    public static PlayerIdSerializationUtility getInstance() {
        return INSTANCE;
    }
}

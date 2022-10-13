package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.FixtureListId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.numbers.PositiveIntegerSerializationUtility;
import org.jetbrains.annotations.NotNull;

public class FixtureListIdSerializationUtility extends PositiveIntegerSerializationUtility<FixtureListId> {

    @NotNull
    private static final FixtureListIdSerializationUtility INSTANCE = new FixtureListIdSerializationUtility();

    @Override
    protected final FixtureListId construct(@NotNull final PositiveInteger value) {
        try {
            return new FixtureListId(value.getValue());
        } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    @NotNull
    public static FixtureListIdSerializationUtility getInstance() {
        return INSTANCE;
    }
}

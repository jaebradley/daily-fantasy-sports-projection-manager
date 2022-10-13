package com.rvnu.models.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;

public class FixtureListId extends PositiveInteger {
    public FixtureListId(final long value) throws ValueMustBePositive, ValueMustNotBeNegative {
        super(value);
    }
}

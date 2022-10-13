package com.rvnu.models.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;

public class PlayerId extends PositiveInteger {
    public PlayerId(final long value) throws ValueMustBePositive, ValueMustNotBeNegative {
        super(value);
    }
}

package com.rvnu.services.thirdparty.rotogrinders.nba.interfaces;

import com.rvnu.models.thirdparty.rotogrinders.nba.Slates;

import java.time.ZonedDateTime;

public interface SlatesAccessor {
    class UnableToGetSlates extends Exception {
    }

    Slates getSlates(final ZonedDateTime time) throws UnableToGetSlates;
}

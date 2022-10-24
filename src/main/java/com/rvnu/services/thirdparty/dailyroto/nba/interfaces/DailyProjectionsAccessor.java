package com.rvnu.services.thirdparty.dailyroto.nba.interfaces;

import com.rvnu.models.thirdparty.dailyroto.nba.DailyProjections;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.util.function.Consumer;

public interface DailyProjectionsAccessor {
    class UnableToGetProjections extends Exception {
    }

    void getProjections(@NotNull final ZonedDateTime time, @NotNull final Consumer<DailyProjections> projectionsConsumer) throws UnableToGetProjections;
}

package com.rvnu.services.thirdparty.rotogrinders.nba.interfaces;

import com.rvnu.models.thirdparty.rotogrinders.nba.SlatePlayerProjection;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.List;

public interface SlateProjectionsAccessor {
    class UnableToGetProjections extends Exception {
    }

    List<SlatePlayerProjection> getProjections(@NotNull URI projectionPath) throws UnableToGetProjections;
}

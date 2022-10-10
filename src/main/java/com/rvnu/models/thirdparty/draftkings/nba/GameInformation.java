package com.rvnu.models.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.nba.Team;

import java.time.ZonedDateTime;

public record GameInformation(
        Team visitingTeam,
        Team homeTeam,
        ZonedDateTime startTime
) {
}

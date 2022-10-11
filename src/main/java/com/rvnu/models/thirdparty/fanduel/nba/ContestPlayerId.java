package com.rvnu.models.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;

public record ContestPlayerId(
        PositiveInteger fixtureListId,
        PositiveInteger playerId
) {
}

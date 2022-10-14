package com.rvnu.data.thirdparty.csv.sabersim.record.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.fanduel.nba.ContestPlayerId;
import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import com.rvnu.models.thirdparty.sabersim.nba.FanDuelPlayerProjection;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.thirdparty.fanduel.nba.ContestPlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.sabersim.nba.fanduel.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.sabersim.nba.fanduel.TeamSerializationUtility;
import org.jetbrains.annotations.NotNull;

public class FanDuelPlayerProjectionDeserializer extends BaseDeserializer<ContestPlayerId, Position, FanDuelPlayerProjection> {
    @NotNull
    private static final FanDuelPlayerProjectionDeserializer INSTANCE = new FanDuelPlayerProjectionDeserializer(
            ContestPlayerIdSerializationUtility.getInstance(),
            PositionsSerializationUtility.getInstance()
    );

    private FanDuelPlayerProjectionDeserializer(
            @NotNull final Deserializer<ContestPlayerId> playerIdDeserializer,
            @NotNull final Deserializer<NonEmptyLinkedHashSet<Position>> positionsDeserializer
    ) {
        super(playerIdDeserializer, positionsDeserializer, TeamSerializationUtility.getInstance(), TeamSerializationUtility.getInstance());
    }

    @Override
    @NotNull
    protected final FanDuelPlayerProjection construct(@NotNull final BaseSitePlayerProjection<ContestPlayerId, Position> projection) {
        return new FanDuelPlayerProjection(
                projection.getPlayerId(),
                projection.getName(),
                projection.getEligiblePositions(),
                projection.getTeam(),
                projection.getOpponent(),
                projection.getSalary(),
                projection.getProjectedPoints(),
                projection.getProjectedOwnership()
        );
    }

    @NotNull
    public static FanDuelPlayerProjectionDeserializer getInstance() {
        return INSTANCE;
    }
}

package com.rvnu.data.thirdparty.csv.sabersim.record.nba;

import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.thirdparty.draftkings.nba.PlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.sabersim.nba.draftkings.TeamSerializationUtility;
import org.jetbrains.annotations.NotNull;

public class DraftKingsPlayerProjectionDeserializer extends BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection> {
    @NotNull
    private static final DraftKingsPlayerProjectionDeserializer INSTANCE = new DraftKingsPlayerProjectionDeserializer(
            PlayerIdSerializationUtility.getInstance(),
            PositionsSerializationUtility.getInstance()
    );

    private DraftKingsPlayerProjectionDeserializer(
            @NotNull final Deserializer<PlayerId> playerIdDeserializer,
            @NotNull final Deserializer<NonEmptyLinkedHashSet<Position>> positionsDeserializer
    ) {
        super(playerIdDeserializer, positionsDeserializer, TeamSerializationUtility.getInstance(), TeamSerializationUtility.getInstance());
    }

    @Override
    protected final @NotNull DraftKingsPlayerProjection construct(@NotNull final BaseSitePlayerProjection<PlayerId, Position> projection) {
        return new DraftKingsPlayerProjection(
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
    public static DraftKingsPlayerProjectionDeserializer getInstance() {
        return INSTANCE;
    }
}

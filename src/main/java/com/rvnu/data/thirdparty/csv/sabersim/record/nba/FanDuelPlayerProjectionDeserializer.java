package com.rvnu.data.thirdparty.csv.sabersim.record.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.fanduel.nba.PlayerId;
import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import com.rvnu.models.thirdparty.sabersim.nba.FanDuelPlayerProjection;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.thirdparty.fanduel.nba.PlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.fanduel.nba.PositionsSerializationUtility;
import org.jetbrains.annotations.NotNull;

public class FanDuelPlayerProjectionDeserializer extends BaseDeserializer<PlayerId, Position, FanDuelPlayerProjection> {
    @NotNull
    private static final FanDuelPlayerProjectionDeserializer INSTANCE = new FanDuelPlayerProjectionDeserializer(
            PlayerIdSerializationUtility.getInstance(),
            PositionsSerializationUtility.getInstance()
    );

    private FanDuelPlayerProjectionDeserializer(
            @NotNull final Deserializer<PlayerId> playerIdDeserializer,
            @NotNull final Deserializer<NonEmptyLinkedHashSet<Position>> positionsDeserializer
    ) {
        super(playerIdDeserializer, positionsDeserializer);
    }

    @Override
    @NotNull
    protected final FanDuelPlayerProjection construct(@NotNull final BaseSitePlayerProjection<PlayerId, Position> projection) {
        try {
            return new FanDuelPlayerProjection(
                    projection.getPlayerId(),
                    projection.getName(),
                    NonEmptyLinkedHashSet.from(projection.getEligiblePositions()),
                    projection.getTeam(),
                    projection.getOpponent(),
                    projection.getSalary(),
                    projection.getProjectedPoints(),
                    projection.getProjectedOwnership()
            );
        } catch (NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    @NotNull
    public static FanDuelPlayerProjectionDeserializer getInstance() {
        return INSTANCE;
    }
}

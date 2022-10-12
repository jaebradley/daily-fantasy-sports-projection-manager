package com.rvnu.data.thirdparty.csv.sabersim.record.nba;

import com.rvnu.data.firstparty.csv.record.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.interfaces.Deserializer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.serialization.thirdparty.draftkings.nba.PlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.PositionsSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;

public class DraftKingsPlayerProjectionDeserializer extends BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection> {
    @NotNull
    private static final DraftKingsPlayerProjectionDeserializer INSTANCE = new DraftKingsPlayerProjectionDeserializer(
            new BaseValueDeserializer<>(PlayerIdSerializationUtility.getInstance(), Column.DFS_ID, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_DFS_ID),
            new BaseValueDeserializer<>(PositionsSerializationUtility.getInstance(), Column.Pos, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Pos)
    );

    private DraftKingsPlayerProjectionDeserializer(
            @NotNull final Deserializer<PlayerId, Column, Error> playerIdDeserializer,
            @NotNull final Deserializer<LinkedHashSet<Position>, Column, Error> positionsDeserializer
    ) {
        super(playerIdDeserializer, positionsDeserializer);
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

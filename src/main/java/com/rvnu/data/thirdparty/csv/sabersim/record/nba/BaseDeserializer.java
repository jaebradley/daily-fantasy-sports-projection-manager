package com.rvnu.data.thirdparty.csv.sabersim.record.nba;

import com.rvnu.data.firstparty.csv.record.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.interfaces.Deserializer;
import com.rvnu.data.firstparty.csv.record.interfaces.Record;
import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.BigDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.sabersim.nba.TeamSerializationUtility;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public abstract class BaseDeserializer<SitePlayerId, SitePosition, PlayerProjection extends BaseSitePlayerProjection<SitePlayerId, SitePosition>> implements Deserializer<PlayerProjection, BaseDeserializer.Column, BaseDeserializer.Error> {
    public enum Column {
        DFS_ID,
        Name,
        Pos,
        Team,
        Opp,
        Status,
        Salary,
        Actual,
        SS_Proj,
        My_Proj,
        Value,
        SS_Own,
        My_Own,
        Min_Exp,
        Max_Exp,
        Saber_Team,
        Saber_Total,
        dk_points,
        dk_25_percentile,
        dk_50_percentile,
        dk_75_percentile,
        dk_85_percentile,
        dk_95_percentile,
        dk_99_percentile,
        fd_points,
        fd_25_percentile,
        fd_50_percentile,
        fd_75_percentile,
        fd_85_percentile,
        fd_95_percentile,
        fd_99_percentile,
        yahoo_points,
        yahoo_25_percentile,
        yahoo_50_percentile,
        yahoo_75_percentile,
        yahoo_85_percentile,
        yahoo_95_percentile,
        yahoo_99_percentile,
        dk_std,
        fd_std,
        yahoo_std,
        PTS,
        Min,
        _2PT,
        _3PT,
        RB,
        Off_Reb,
        Def_Reb,
        AST,
        STL,
        BLK,
        TO,
    }

    public enum Error {
        COLUMN_DOES_NOT_EXIST,
        INVALID_DFS_ID,
        INVALID_Name,
        INVALID_Pos,
        INVALID_Team,
        INVALID_Opp,
        INVALID_Status,
        INVALID_Salary,
        INVALID_Actual,
        INVALID_SS_Proj,
        INVALID_My_Proj,
        INVALID_Value,
        INVALID_SS_Own,
        INVALID_My_Own,
        INVALID_Min_Exp,
        INVALID_Max_Exp,
        INVALID_Saber_Team,
        INVALID_Saber_Total,
        INVALID_dk_points,
        INVALID_dk_25_percentile,
        INVALID_dk_50_percentile,
        INVALID_dk_75_percentile,
        INVALID_dk_85_percentile,
        INVALID_dk_95_percentile,
        INVALID_dk_99_percentile,
        INVALID_fd_points,
        INVALID_fd_25_percentile,
        INVALID_fd_50_percentile,
        INVALID_fd_75_percentile,
        INVALID_fd_85_percentile,
        INVALID_fd_95_percentile,
        INVALID_fd_99_percentile,
        INVALID_yahoo_points,
        INVALID_yahoo_25_percentile,
        INVALID_yahoo_50_percentile,
        INVALID_yahoo_75_percentile,
        INVALID_yahoo_85_percentile,
        INVALID_yahoo_95_percentile,
        INVALID_yahoo_99_percentile,
        INVALID_dk_std,
        INVALID_fd_std,
        INVALID_yahoo_std,
        INVALID_PTS,
        INVALID_Min,
        INVALID__2PT,
        INVALID__3PT,
        INVALID_RB,
        INVALID_Off_Reb,
        INVALID_Def_Reb,
        INVALID_AST,
        INVALID_STL,
        INVALID_BLK,
        INVALID_TO,
    }

    @NotNull
    private final Deserializer<SitePlayerId, Column, Error> playerIdDeserializer;

    @NotNull
    private final Deserializer<NonEmptyString, Column, Error> nameDeserializer;

    @NotNull
    private final Deserializer<NonEmptyLinkedHashSet<SitePosition>, Column, Error> positionsDeserializer;

    @NotNull
    private final Deserializer<Team, Column, Error> teamDeserializer;

    @NotNull
    private final Deserializer<Team, Column, Error> opponentDeserializer;

    @NotNull
    private final Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer;

    @NotNull
    private final Deserializer<BigDecimal, Column, Error> projectedPointsDeserializer;

    @NotNull
    private final Deserializer<NonNegativeDecimal, Column, Error> projectedOwnershipDeserializer;

    private BaseDeserializer(
            @NotNull final Deserializer<SitePlayerId, Column, Error> playerIdDeserializer,
            @NotNull final Deserializer<NonEmptyString, Column, Error> nameDeserializer,
            @NotNull final Deserializer<NonEmptyLinkedHashSet<SitePosition>, Column, Error> positionsDeserializer,
            @NotNull final Deserializer<Team, Column, Error> teamDeserializer,
            @NotNull final Deserializer<Team, Column, Error> opponentDeserializer,
            @NotNull final Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer,
            @NotNull final Deserializer<BigDecimal, Column, Error> projectedPointsDeserializer,
            @NotNull final Deserializer<NonNegativeDecimal, Column, Error> projectedOwnershipDeserializer
    ) {
        this.playerIdDeserializer = playerIdDeserializer;
        this.nameDeserializer = nameDeserializer;
        this.positionsDeserializer = positionsDeserializer;
        this.teamDeserializer = teamDeserializer;
        this.opponentDeserializer = opponentDeserializer;
        this.salaryDeserializer = salaryDeserializer;
        this.projectedPointsDeserializer = projectedPointsDeserializer;
        this.projectedOwnershipDeserializer = projectedOwnershipDeserializer;
    }

    protected BaseDeserializer(
            @NotNull final com.rvnu.serialization.firstparty.interfaces.Deserializer<SitePlayerId> playerIdDeserializer,
            @NotNull final com.rvnu.serialization.firstparty.interfaces.Deserializer<NonEmptyLinkedHashSet<SitePosition>> positionsDeserializer
    ) {
        this.playerIdDeserializer = new BaseValueDeserializer<>(playerIdDeserializer, Column.DFS_ID, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_DFS_ID);
        this.positionsDeserializer = new BaseValueDeserializer<>(positionsDeserializer, Column.Pos, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Pos);

        this.nameDeserializer = new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.Name, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Name);
        this.teamDeserializer = new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Team, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Team);
        this.opponentDeserializer = new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Opp, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Opp);
        this.salaryDeserializer = new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.Salary, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Salary);
        this.projectedPointsDeserializer = new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.SS_Proj, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_SS_Proj);
        this.projectedOwnershipDeserializer = new BaseValueDeserializer<>(NonNegativeDecimalSerializationUtility.getInstance(), Column.SS_Own, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_SS_Own);

    }

    @NotNull
    abstract protected PlayerProjection construct(@NotNull final BaseSitePlayerProjection<SitePlayerId, SitePosition> projection);

    @Override
    public @NotNull Either<Error, PlayerProjection> deserialize(@NotNull final Record<Column> record) {
        return playerIdDeserializer.deserialize(record)
                .flatMap(playerId -> nameDeserializer.deserialize(record)
                        .flatMap(name -> positionsDeserializer.deserialize(record)
                                .flatMap(positions -> teamDeserializer.deserialize(record)
                                        .flatMap(team -> opponentDeserializer.deserialize(record)
                                                .flatMap(opponent -> salaryDeserializer.deserialize(record)
                                                        .flatMap(salary -> projectedPointsDeserializer.deserialize(record)
                                                                .flatMap(projectedPoints -> projectedOwnershipDeserializer.deserialize(record)
                                                                        .map(projectedOwnership -> construct(
                                                                                new BaseSitePlayerProjection<>(
                                                                                        playerId,
                                                                                        name,
                                                                                        positions,
                                                                                        team,
                                                                                        opponent,
                                                                                        salary,
                                                                                        projectedPoints,
                                                                                        projectedOwnership
                                                                                )
                                                                        )))))))));
    }
}

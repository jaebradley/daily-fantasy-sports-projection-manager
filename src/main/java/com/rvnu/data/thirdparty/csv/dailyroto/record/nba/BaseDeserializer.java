package com.rvnu.data.thirdparty.csv.dailyroto.record.nba;

import com.rvnu.data.firstparty.csv.record.deserialization.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer;
import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Record;
import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.dailyroto.nba.Projection;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.numbers.NonNegativePercentage;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.BigDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativePercentageSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.dailyroto.nba.TeamSerializationUtility;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public abstract class BaseDeserializer<SitePosition, SitePlayerId> implements Deserializer<Projection<SitePosition, SitePlayerId>,
        BaseDeserializer.Column,
        BaseDeserializer.Error> {
    public enum Column {
        Team,
        Opp,
        Player,
        Slate_PlayerID,
        Unnamed_Column_1,
        Minutes,
        Usage_Rate,
        Rebound_Rate,
        AssistRate,
        Unnamed_Column_2,
        Pos,
        Public_Percentage,
        Optimal_Percentage,
        Leverage,
        Salary,
        Points,
        Value
    }

    public enum Error {
        COLUMN_NOT_FOUND,
        INVALID_Team,
        INVALID_Opp,
        INVALID_Player,
        INVALID_Slate_PlayerID,
        INVALID_Unnamed_Column_1,
        INVALID_Minutes,
        INVALID_Usage_Rate,
        INVALID_Rebound_Rate,
        INVALID_AssistRate,
        INVALID_Unnamed_Column_2,
        INVALID_Pos,
        INVALID_Public_Percentage,
        INVALID_Optimal_Percentage,
        INVALID_Leverage,
        INVALID_Salary,
        INVALID_Points,
        INVALID_Value
    }

    @NotNull
    private final Deserializer<Team, Column, Error> teamDeserializer;

    @NotNull
    private final Deserializer<Team, Column, Error> opponentDeserializer;

    @NotNull
    private final Deserializer<Optional<SitePlayerId>, Column, Error> playerIdDeserializer;

    @NotNull
    private final Deserializer<NonEmptyString, Column, Error> nameDeserializer;

    @NotNull
    private final Deserializer<NonNegativeDecimal, Column, Error> minutesPlayedDeserializer;

    @NotNull
    private final Deserializer<NonNegativePercentage, Column, Error> usageRateDeserializer;

    @NotNull
    private final Deserializer<NonNegativePercentage, Column, Error> reboundRateDeserializer;

    @NotNull
    private final Deserializer<NonNegativePercentage, Column, Error> assistRateDeserializer;

    @NotNull
    private final Deserializer<NonEmptyLinkedHashSet<SitePosition>, Column, Error> positionsDeserializer;

    @NotNull
    private final Deserializer<NonNegativePercentage, Column, Error> publicOwnershipPercentageDeserializer;

    @NotNull
    private final Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer;

    @NotNull
    private final Deserializer<BigDecimal, Column, Error> pointsDeserializer;

    @NotNull
    private final Deserializer<BigDecimal, Column, Error> valueDeserializer;

    protected BaseDeserializer(
            @NotNull final com.rvnu.serialization.firstparty.interfaces.Deserializer<NonEmptyLinkedHashSet<SitePosition>> positionsDeserializer,
            @NotNull final com.rvnu.serialization.firstparty.interfaces.Deserializer<Optional<SitePlayerId>> playerIdDeserializer
    ) {
        this.teamDeserializer = new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Team, Error.COLUMN_NOT_FOUND, Error.INVALID_Team);
        this.opponentDeserializer = new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Opp, Error.COLUMN_NOT_FOUND, Error.INVALID_Opp);
        this.playerIdDeserializer = new BaseValueDeserializer<>(playerIdDeserializer, Column.Slate_PlayerID, Error.COLUMN_NOT_FOUND, Error.INVALID_Slate_PlayerID);
        this.nameDeserializer = new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.Player, Error.COLUMN_NOT_FOUND, Error.INVALID_Player);
        this.minutesPlayedDeserializer = new BaseValueDeserializer<>(NonNegativeDecimalSerializationUtility.getDefaultInstance(), Column.Minutes, Error.COLUMN_NOT_FOUND, Error.INVALID_Minutes);
        this.usageRateDeserializer = new BaseValueDeserializer<>(NonNegativePercentageSerializationUtility.getInstance(), Column.Usage_Rate, Error.COLUMN_NOT_FOUND, Error.INVALID_Usage_Rate);
        this.reboundRateDeserializer = new BaseValueDeserializer<>(NonNegativePercentageSerializationUtility.getInstance(), Column.Rebound_Rate, Error.COLUMN_NOT_FOUND, Error.INVALID_Rebound_Rate);
        this.assistRateDeserializer = new BaseValueDeserializer<>(NonNegativePercentageSerializationUtility.getInstance(), Column.AssistRate, Error.COLUMN_NOT_FOUND, Error.INVALID_AssistRate);
        this.positionsDeserializer = new BaseValueDeserializer<>(positionsDeserializer, Column.Pos, Error.COLUMN_NOT_FOUND, Error.INVALID_Pos);
        this.publicOwnershipPercentageDeserializer = new BaseValueDeserializer<>(NonNegativePercentageSerializationUtility.getInstance(), Column.Public_Percentage, Error.COLUMN_NOT_FOUND, Error.INVALID_Public_Percentage);
        this.salaryDeserializer = new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.Salary, Error.COLUMN_NOT_FOUND, Error.INVALID_Salary);
        this.pointsDeserializer = new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.Points, Error.COLUMN_NOT_FOUND, Error.INVALID_Points);
        this.valueDeserializer = new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.Value, Error.COLUMN_NOT_FOUND, Error.INVALID_Value);
    }

    @Override
    public @NotNull Either<Error, Projection<SitePosition, SitePlayerId>> deserialize(@NotNull final Record<Column> record) {
        return teamDeserializer.deserialize(record)
                .flatMap(team -> opponentDeserializer.deserialize(record)
                        .flatMap(opponent -> playerIdDeserializer.deserialize(record).flatMap(
                                playerId -> nameDeserializer.deserialize(record)
                                        .flatMap(name -> minutesPlayedDeserializer.deserialize(record)
                                                .flatMap(minutesPlayed -> usageRateDeserializer.deserialize(record)
                                                        .flatMap(usageRate -> reboundRateDeserializer.deserialize(record)
                                                                .flatMap(reboundRate -> assistRateDeserializer.deserialize(record)
                                                                        .flatMap(assistRate -> positionsDeserializer.deserialize(record)
                                                                                .flatMap(positions -> publicOwnershipPercentageDeserializer.deserialize(record)
                                                                                        .flatMap(publicOwnershipPercentage -> salaryDeserializer.deserialize(record)
                                                                                                .flatMap(salary -> pointsDeserializer.deserialize(record)
                                                                                                        .flatMap(points -> valueDeserializer.deserialize(record)
                                                                                                                .map(value -> new Projection<>(
                                                                                                                        team,
                                                                                                                        opponent,
                                                                                                                        playerId,
                                                                                                                        name,
                                                                                                                        minutesPlayed,
                                                                                                                        usageRate,
                                                                                                                        reboundRate,
                                                                                                                        assistRate,
                                                                                                                        positions,
                                                                                                                        publicOwnershipPercentage,
                                                                                                                        salary,
                                                                                                                        points,
                                                                                                                        value
                                                                                                                ))))))))))))));
    }
}

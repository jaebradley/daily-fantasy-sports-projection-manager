package com.rvnu.data.thirdparty.csv.stokastic.record.nba;

import com.rvnu.data.firstparty.csv.record.deserialization.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Record;
import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.stokastic.nba.Position;
import com.rvnu.models.thirdparty.stokastic.nba.Projection;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.BigDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.stokastic.nba.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.stokastic.nba.TeamSerializationUtility;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Deserializer implements com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Projection, Deserializer.Column, Deserializer.Error> {
    public enum Column {
        Name,
        Fpts,
        Position,
        Team,
        Opponent,
        Minutes,
        Salary,
        Pts_$,
        Value,
    }

    public enum Error {
        COLUMN_DOES_NOT_EXIST,
        INVALID_NAME,
        INVALID_FPTS,
        INVALID_POSITION,
        INVALID_TEAM,
        INVALID_OPPONENT,
        INVALID_MINUTES,
        INVALID_SALARY,
        INVALID_PTS_$,
        INVALID_VALUE,
    }

    @NotNull
    private static final Deserializer INSTANCE = new Deserializer(
            new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.Name, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_NAME),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.Fpts, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_FPTS),
            new BaseValueDeserializer<>(PositionsSerializationUtility.getInstance(), Column.Position, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_POSITION),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Team, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_TEAM),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Opponent, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_OPPONENT),
            new BaseValueDeserializer<>(NonNegativeDecimalSerializationUtility.getDefaultInstance(), Column.Minutes, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_MINUTES),
            new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.Salary, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_SALARY),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.Pts_$, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_PTS_$),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.Value, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_VALUE)
    );

    private Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> opponentDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDecimal, Column, Error> minutesDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsPerDollarDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> valueDeserializer
    ) {
        this.nameDeserializer = nameDeserializer;
        this.fantasyPointsDeserializer = fantasyPointsDeserializer;
        this.positionsDeserializer = positionsDeserializer;
        this.teamDeserializer = teamDeserializer;
        this.opponentDeserializer = opponentDeserializer;
        this.minutesDeserializer = minutesDeserializer;
        this.salaryDeserializer = salaryDeserializer;
        this.fantasyPointsPerDollarDeserializer = fantasyPointsPerDollarDeserializer;
        this.valueDeserializer = valueDeserializer;
    }

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> opponentDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDecimal, Column, Error> minutesDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsPerDollarDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> valueDeserializer;

    @Override
    public @NotNull Either<Error, Projection> deserialize(@NotNull final Record<Column> record) {
        return nameDeserializer.deserialize(record)
                .flatMap(name -> fantasyPointsDeserializer.deserialize(record)
                        .flatMap(fantasyPoints -> positionsDeserializer.deserialize(record)
                                .flatMap(positions -> teamDeserializer.deserialize(record)
                                        .flatMap(team -> opponentDeserializer.deserialize(record)
                                                .flatMap(opponent -> minutesDeserializer.deserialize(record)
                                                        .flatMap(minutes -> salaryDeserializer.deserialize(record)
                                                                .flatMap(salary -> fantasyPointsPerDollarDeserializer.deserialize(record)
                                                                        .flatMap(fantasyPointsPerDollar -> valueDeserializer.deserialize(record)
                                                                                .map(value -> new Projection(
                                                                                        name,
                                                                                        fantasyPoints,
                                                                                        positions,
                                                                                        team,
                                                                                        opponent,
                                                                                        minutes,
                                                                                        salary,
                                                                                        fantasyPointsPerDollar,
                                                                                        value
                                                                                ))))))))));
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}

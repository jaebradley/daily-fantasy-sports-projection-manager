package com.rvnu.data.thirdparty.csv.stokastic.record.mlb;

import com.rvnu.data.firstparty.csv.record.deserialization.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Record;
import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.stokastic.mlb.Position;
import com.rvnu.models.thirdparty.stokastic.mlb.Projection;
import com.rvnu.models.thirdparty.stokastic.mlb.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.BigDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.stokastic.mlb.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.stokastic.mlb.TeamSerializationUtility;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class Deserializer implements com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Projection, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> {

    public enum Column {
        Name,
        Fpts,
        Tm,
        Status,
        Pos,
        Ord,
        H,
        Sal,
        Pts_$,
        Value,
        Total,
        PPD_percentage,
        Slate,
    }

    public enum Error {
        COLUMN_DOES_NOT_EXIST,
        INVALID_NAME,
        INVALID_FPTS,
        INVALID_TEAM,
        INVALID_STATUS,
        INVALID_POSITION,
        INVALID_ORDER,
        INVALID_HITTING_SIDE,
        INVALID_SALARY,
        INVALID_PTS_$,
        INVALID_VALUE,
        INVALID_TOTAL,
        INVALID_PPD_PERCENTAGE,
        INVALID_SLATE,
    }

    @NotNull
    private static final Deserializer INSTANCE = new Deserializer(
            new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column.Name, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_NAME),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column.Fpts, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_FPTS),
            new BaseValueDeserializer<>(PositionsSerializationUtility.getInstance(), Column.Pos, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_POSITION),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column.Tm, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_TEAM),
            new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.Sal, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_SALARY),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column.Pts_$, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_PTS_$),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column.Value, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.COLUMN_DOES_NOT_EXIST, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error.INVALID_VALUE)
    );

    private Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsPerDollarDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> valueDeserializer) {
        this.nameDeserializer = nameDeserializer;
        this.fantasyPointsDeserializer = fantasyPointsDeserializer;
        this.positionsDeserializer = positionsDeserializer;
        this.teamDeserializer = teamDeserializer;
        this.salaryDeserializer = salaryDeserializer;
        this.fantasyPointsPerDollarDeserializer = fantasyPointsPerDollarDeserializer;
        this.valueDeserializer = valueDeserializer;
    }

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> nameDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> fantasyPointsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> positionsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> teamDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> salaryDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> fantasyPointsPerDollarDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> valueDeserializer;


    @Override
    public @NotNull Either<Error, Projection> deserialize(@NotNull final Record<Column> record) {
        return nameDeserializer.deserialize(record)
                .flatMap(name -> fantasyPointsDeserializer.deserialize(record)
                        .flatMap(fantasyPoints -> positionsDeserializer.deserialize(record)
                                .flatMap(positions -> teamDeserializer.deserialize(record)
                                        .flatMap(team -> salaryDeserializer.deserialize(record)
                                                .flatMap(salary -> fantasyPointsPerDollarDeserializer.deserialize(record)
                                                        .flatMap(fantasyPointsPerDollar -> valueDeserializer.deserialize(record)
                                                                .map(value -> new Projection(
                                                                        name,
                                                                        fantasyPoints,
                                                                        team,
                                                                        positions,
                                                                        salary,
                                                                        fantasyPointsPerDollar,
                                                                        value
                                                                ))))))));
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}

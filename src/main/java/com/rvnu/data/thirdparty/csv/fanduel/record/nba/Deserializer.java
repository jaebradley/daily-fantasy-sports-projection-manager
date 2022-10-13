package com.rvnu.data.thirdparty.csv.fanduel.record.nba;

import com.rvnu.data.firstparty.csv.record.columns.BaseOptionalValueDeserializer;
import com.rvnu.data.firstparty.csv.record.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.interfaces.Record;
import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.fanduel.nba.*;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.fanduel.nba.ContestPlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.fanduel.nba.InjuryIndicatorSerializationUtility;
import com.rvnu.serialization.thirdparty.fanduel.nba.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.fanduel.nba.TeamSerializationUtility;
import com.rvnu.models.thirdparty.nba.Team;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Deserializer implements com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<ContestPlayer, Deserializer.Column, Deserializer.Error> {

    public enum Column {
        Id,
        Position,
        First_Name,
        Nickname,
        Last_Name,
        FPPG,
        Played,
        Salary,
        Game,
        Team,
        Opponent,
        Injury_Indicator,
        Injury_Details,
        Tier,
        Unnamed_Column_1,
        Unnamed_Column_2,
        Roster_Position;
    }


    public enum Error {
        COLUMN_DOES_NOT_EXIST,
        INVALID_Id,
        INVALID_Position,
        INVALID_First_Name,
        INVALID_Nickname,
        INVALID_Last_Name,
        INVALID_FPPG,
        INVALID_Played,
        INVALID_Salary,
        INVALID_Game,
        INVALID_Team,
        INVALID_Opponent,
        INVALID_Injury_Indicator,
        INVALID_Injury_Details,
        INVALID_Tier,
        INVALID_Unnamed_Column_1,
        INVALID_Unnamed_Column_2,
        INVALID_Roster_Position,

    }

    @NotNull
    private static final Deserializer INSTANCE = new Deserializer(
            new BaseValueDeserializer<>(ContestPlayerIdSerializationUtility.getInstance(), Column.Id, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Id),
            new BaseValueDeserializer<>(PositionsSerializationUtility.getInstance(), Column.Position, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Position),
            new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.First_Name, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_First_Name),
            new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.Last_Name, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Last_Name),
            new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.Salary, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Salary),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Team, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Team),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.Opponent, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Opponent),
            new BaseOptionalValueDeserializer<>(InjuryIndicatorSerializationUtility.getInstance(), Column.Injury_Indicator, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Injury_Indicator),
            new BaseOptionalValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.Injury_Details, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Injury_Details)
    );

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<ContestPlayerId, Column, Error> idDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonEmptyString, Column, Error> firstNameDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonEmptyString, Column, Error> lastNameDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Team, Column, Error> teamDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Team, Column, Error> opponentDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Optional<InjuryIndicator>, Column, Error> injuryIndicatorDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Optional<NonEmptyString>, Column, Error> injuryDetailsDeserializer;

    private Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<ContestPlayerId, Column, Error> idDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonEmptyString, Column, Error> firstNameDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonEmptyString, Column, Error> lastNameDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Team, Column, Error> teamDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Team, Column, Error> opponentDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Optional<InjuryIndicator>, Column, Error> injuryIndicatorDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Optional<NonEmptyString>, Column, Error> injuryDetailsDeserializer
    ) {
        this.idDeserializer = idDeserializer;
        this.positionsDeserializer = positionsDeserializer;
        this.firstNameDeserializer = firstNameDeserializer;
        this.lastNameDeserializer = lastNameDeserializer;
        this.salaryDeserializer = salaryDeserializer;
        this.teamDeserializer = teamDeserializer;
        this.opponentDeserializer = opponentDeserializer;
        this.injuryIndicatorDeserializer = injuryIndicatorDeserializer;
        this.injuryDetailsDeserializer = injuryDetailsDeserializer;
    }

    @Override
    public @NotNull Either<Error, ContestPlayer> deserialize(@NotNull final Record<Column> record) {
        return idDeserializer.deserialize(record)
                .flatMap(id -> positionsDeserializer.deserialize(record)
                        .flatMap(positions -> firstNameDeserializer.deserialize(record)
                                .flatMap(firstName -> lastNameDeserializer.deserialize(record)
                                        .flatMap(lastName -> salaryDeserializer.deserialize(record)
                                                .flatMap(salary -> teamDeserializer.deserialize(record)
                                                        .flatMap(team -> opponentDeserializer.deserialize(record)
                                                                .flatMap(opponent -> injuryIndicatorDeserializer.deserialize(record)
                                                                        .flatMap(injuryIndicator -> injuryDetailsDeserializer.deserialize(record)
                                                                                .map(injuryDetails -> new ContestPlayer(
                                                                                        id,
                                                                                        positions,
                                                                                        firstName,
                                                                                        lastName,
                                                                                        salary,
                                                                                        team,
                                                                                        opponent,
                                                                                        injuryIndicator,
                                                                                        injuryDetails
                                                                                ))))))))));
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}

package com.rvnu.data.thirdparty.csv.draftkings.record.nba;

import com.rvnu.data.firstparty.csv.record.deserialization.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Record;
import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.GameInformation;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.AbbreviatedPositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.GameInformationSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.PlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.TeamSerializationUtility;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

public class Deserializer implements com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<ContestPlayer, Deserializer.Column, Deserializer.Error> {

    public enum Column {
        Position,
        Name_and_ID,
        Name,
        ID,
        Roster_Position,
        Salary,
        Game_Info,
        TeamAbbrev,
        AvgPointsPerGame
    }

    public enum Error {
        COLUMN_DOES_NOT_EXIST,
        INVALID_Position,
        INVALID_Name_and_ID,
        INVALID_Name,
        INVALID_ID,
        INVALID_Roster_Position,
        INVALID_Salary,
        INVALID_Game_Info,
        INVALID_TeamAbbrev,
        INVALID_AvgPointsPerGame,
    }

    @NotNull
    private static final Deserializer INSTANCE = new Deserializer(
            new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.Name, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Name),
            new BaseValueDeserializer<>(PlayerIdSerializationUtility.getInstance(), Column.ID, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_ID),
            new BaseValueDeserializer<>(AbbreviatedPositionsSerializationUtility.getInstance(), Column.Roster_Position, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Roster_Position),
            new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.Salary, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Salary),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.TeamAbbrev, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_TeamAbbrev),
            new BaseValueDeserializer<>(GameInformationSerializationUtility.getInstance(), Column.Game_Info, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_Game_Info)
    );

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<PlayerId, Column, Error> idDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> eligiblePositionsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<GameInformation, Column, Error> gameInformationDeserializer;

    private Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<PlayerId, Column, Error> idDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> eligiblePositionsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<GameInformation, Column, Error> gameInformationDeserializer
    ) {
        this.nameDeserializer = nameDeserializer;
        this.idDeserializer = idDeserializer;
        this.eligiblePositionsDeserializer = eligiblePositionsDeserializer;
        this.salaryDeserializer = salaryDeserializer;
        this.teamDeserializer = teamDeserializer;
        this.gameInformationDeserializer = gameInformationDeserializer;
    }

    @Override
    public @NotNull Either<Error, ContestPlayer> deserialize(@NotNull final Record<Column> record) {
        return nameDeserializer.deserialize(record)
                .flatMap(name -> idDeserializer.deserialize(record)
                        .flatMap(id -> eligiblePositionsDeserializer.deserialize(record)
                                .flatMap(eligiblePositions -> salaryDeserializer.deserialize(record)
                                        .flatMap(salary -> teamDeserializer.deserialize(record)
                                                .flatMap(team -> gameInformationDeserializer.deserialize(record)
                                                        .map(gameInformation -> new ContestPlayer(
                                                                name,
                                                                id,
                                                                eligiblePositions,
                                                                salary,
                                                                team,
                                                                gameInformation
                                                        )))))));
    }

    public static Deserializer getInstance() {
        return INSTANCE;
    }
}

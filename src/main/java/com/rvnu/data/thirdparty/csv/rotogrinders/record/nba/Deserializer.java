package com.rvnu.data.thirdparty.csv.rotogrinders.record.nba;

import com.rvnu.data.firstparty.csv.record.deserialization.columns.BaseValueDeserializer;
import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Record;
import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.stokastic.nba.Position;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.rotogrinders.nba.Projection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.numbers.BigDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.PositiveIntegerSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.stokastic.nba.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.rotogrinders.nba.TeamSerializationUtility;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public class Deserializer implements com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Projection, Deserializer.Column, Deserializer.Error> {
    public enum Column {
        player_id,
        team,
        opp,
        pos,
        name,
        fpts,
        proj_own,
        smash,
        minutes,
        ceil,
        floor,
        min_exposure,
        max_exposure,
        rg_value,
        salary,
        custom,
        rg_id,
        partner_id
    }

    public enum Error {
        COLUMN_DOES_NOT_EXIST,
        INVALID_player_id,
        INVALID_team,
        INVALID_opp,
        INVALID_pos,
        INVALID_name,
        INVALID_fpts,
        INVALID_proj_own,
        INVALID_smash,
        INVALID_minutes,
        INVALID_ceil,
        INVALID_floor,
        INVALID_min_exposure,
        INVALID_max_exposure,
        INVALID_rg_value,
        INVALID_salary,
        INVALID_custom,
        INVALID_rg_id,
        INVALID_partner_id
    }

    private static final Deserializer INSTANCE = new Deserializer(
            new BaseValueDeserializer<>(PositiveIntegerSerializationUtility.getDefaultInstance(), Column.player_id, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_player_id),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.team, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_team),
            new BaseValueDeserializer<>(TeamSerializationUtility.getInstance(), Column.opp, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_opp),
            new BaseValueDeserializer<>(PositionsSerializationUtility.getInstance(), Column.pos, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_pos),
            new BaseValueDeserializer<>(NonEmptyStringSerializationUtility.getInstance(), Column.name, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_name),
            new BaseValueDeserializer<>(value -> {
                try {
                    return Optional.of(new BigDecimal(value));
                } catch (NumberFormatException e) {
                    return Optional.of(BigDecimal.ZERO);
                }
            }, Column.fpts, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_fpts),
            new BaseValueDeserializer<>(BigDecimalSerializationUtility.getInstance(), Column.rg_value, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_rg_value),
            new BaseValueDeserializer<>(NonNegativeDollarsSerializationUtility.getInstance(), Column.salary, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_salary),
            // TODO: @jbradley move this to it's own deserializer
            new BaseValueDeserializer<>(value -> {
                try {
                    return Optional.of(Long.parseLong(value));
                } catch (NumberFormatException e) {
                    return Optional.empty();
                }
            }, Column.rg_id, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_rg_id),
            new BaseValueDeserializer<>(PositiveIntegerSerializationUtility.getDefaultInstance(), Column.partner_id, Error.COLUMN_DOES_NOT_EXIST, Error.INVALID_partner_id)
    );

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<PositiveInteger, Column, Error> playerIdDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> oppositionDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> rotogrindersValueDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Long, Column, Error> rotogrindersIdDeserializer;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<PositiveInteger, Column, Error> partnerIdDeserializer;

    private Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<PositiveInteger, Column, Error> playerIdDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> teamDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Team, Column, Error> oppositionDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyLinkedHashSet<Position>, Column, Error> positionsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonEmptyString, Column, Error> nameDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> fantasyPointsDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<BigDecimal, Column, Error> rotogrindersValueDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<NonNegativeDollars, Column, Error> salaryDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Long, Column, Error> rotogrindersIdDeserializer,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<PositiveInteger, Column, Error> partnerIdDeserializer) {
        this.playerIdDeserializer = playerIdDeserializer;
        this.teamDeserializer = teamDeserializer;
        this.oppositionDeserializer = oppositionDeserializer;
        this.positionsDeserializer = positionsDeserializer;
        this.nameDeserializer = nameDeserializer;
        this.fantasyPointsDeserializer = fantasyPointsDeserializer;
        this.rotogrindersValueDeserializer = rotogrindersValueDeserializer;
        this.salaryDeserializer = salaryDeserializer;
        this.rotogrindersIdDeserializer = rotogrindersIdDeserializer;
        this.partnerIdDeserializer = partnerIdDeserializer;
    }

    @Override
    public @NotNull Either<Error, Projection> deserialize(@NotNull final Record<Column> record) {
        return playerIdDeserializer.deserialize(record)
                .flatMap(playerId -> teamDeserializer.deserialize(record)
                        .flatMap(team -> oppositionDeserializer.deserialize(record)
                                .flatMap(opposition -> positionsDeserializer.deserialize(record)
                                        .flatMap(positions -> nameDeserializer.deserialize(record)
                                                .flatMap(name -> fantasyPointsDeserializer.deserialize(record)
                                                        .flatMap(fantasyPoints -> rotogrindersValueDeserializer.deserialize(record)
                                                                .flatMap(rotogrindersValue -> salaryDeserializer.deserialize(record)
                                                                        .flatMap(salary -> rotogrindersIdDeserializer.deserialize(record)
                                                                                .flatMap(rotogrindersId -> partnerIdDeserializer.deserialize(record)
                                                                                        .map(partnerId -> new Projection(
                                                                                                playerId,
                                                                                                team,
                                                                                                opposition,
                                                                                                positions,
                                                                                                name,
                                                                                                fantasyPoints,
                                                                                                salary,
                                                                                                rotogrindersId,
                                                                                                partnerId
                                                                                        )))))))))));
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}

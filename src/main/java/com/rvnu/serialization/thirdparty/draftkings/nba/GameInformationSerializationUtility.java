package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.GameInformation;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Example format: PHI@NOP 10/20/2021 08:00PM ET
 */
public class GameInformationSerializationUtility implements Deserializer<GameInformation> {
    @NotNull
    private static final GameInformationSerializationUtility INSTANCE = new GameInformationSerializationUtility(
            TeamSerializationUtility.getInstance(),
            DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma v")
    );

    @NotNull
    private final Deserializer<Team> teamDeserializer;
    @NotNull
    private final DateTimeFormatter dateTimeFormat;

    private GameInformationSerializationUtility(
            @NotNull final Deserializer<Team> teamDeserializer,
            @NotNull final DateTimeFormatter dateTimeFormat
    ) {
        this.teamDeserializer = teamDeserializer;
        this.dateTimeFormat = dateTimeFormat;
    }

    @Override
    public Optional<GameInformation> deserialize(@NotNull final String value) {
        final String[] parts = value.split(" ");
        if (4 != parts.length) {
            return Optional.empty();
        }

        final Team visitingTeam;
        final Team homeTeam;
        {
            final String teams = parts[0];
            final String[] teamParts = teams.split("@");
            if (2 != teamParts.length) {
                return Optional.empty();
            }

            final Optional<Team> visitors = teamDeserializer.deserialize(teamParts[0]);
            if (visitors.isEmpty()) {
                return Optional.empty();
            }

            visitingTeam = visitors.get();

            final Optional<Team> home = teamDeserializer.deserialize(teamParts[1]);
            if (home.isEmpty()) {
                return Optional.empty();
            }
            homeTeam = home.get();
        }

        final ZonedDateTime startTime;
        try {
            startTime = ZonedDateTime.parse(String.join(" ", parts[1], parts[2], parts[3]), dateTimeFormat);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }


        return Optional.of(
                new GameInformation(
                        visitingTeam,
                        homeTeam,
                        startTime
                )
        );
    }

    public static GameInformationSerializationUtility getInstance() {
        return INSTANCE;
    }
}

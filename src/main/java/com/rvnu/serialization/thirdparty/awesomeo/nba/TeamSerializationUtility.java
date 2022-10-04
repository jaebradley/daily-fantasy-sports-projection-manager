package com.rvnu.serialization.thirdparty.awesomeo.nba;

import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.serialization.firstparty.enumerations.AbstractEnumeratedValuesSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamSerializationUtility extends AbstractEnumeratedValuesSerializationUtility<Team> {
    private static final TeamSerializationUtility INSTANCE = new TeamSerializationUtility(
            new EnumMap<Team, String>(
                    Stream.of(
                            Map.entry(Team.GOLDEN_STATE_WARRIORS, "GSW"),
                            Map.entry(Team.SACRAMENTO_KINGS, "SAC")
                    ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue))
            ),
            Team.class
    );

    private TeamSerializationUtility(
            @NotNull final EnumMap<Team, String> serializationsByValue,
            @NotNull final Class<Team> keyClass
    ) {
        super(serializationsByValue, keyClass);
    }

    public static TeamSerializationUtility getInstance() {
        return INSTANCE;
    }
}
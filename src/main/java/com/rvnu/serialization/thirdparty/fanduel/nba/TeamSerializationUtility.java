package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Team;
import com.rvnu.serialization.firstparty.enumerations.AbstractEnumeratedValuesSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TeamSerializationUtility extends AbstractEnumeratedValuesSerializationUtility<Team> {
    @NotNull
    private static final TeamSerializationUtility INSTANCE = new TeamSerializationUtility(
            new EnumMap<Team, String>(
                    Stream.of(
                            Map.entry(Team.Boston_Celtics, "BOS"),
                            Map.entry(Team.Golden_State_Warriors, "GS"),
                            Map.entry(Team.Los_Angeles_Lakers, "LAL")
                    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            ),
            Team.class
    );

    private TeamSerializationUtility(@NotNull final EnumMap<Team, String> serializationsByValue, @NotNull final Class<Team> keyClass) {
        super(serializationsByValue, keyClass);
    }

    public static TeamSerializationUtility getInstance() {
        return INSTANCE;
    }
}

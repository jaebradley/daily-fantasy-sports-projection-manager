package com.rvnu.serialization.thirdparty.draftkings.nba;


import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.serialization.firstparty.enumerations.AbstractEnumeratedValuesSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class TeamSerializationUtility extends AbstractEnumeratedValuesSerializationUtility<Team> {
    @NotNull
    private static final TeamSerializationUtility INSTANCE = new TeamSerializationUtility(
            new EnumMap<Team, String>(
                    Map.of(
                            Team.GOLDEN_STATE_WARRIORS, "GSW",
                            Team.PHILADELPHIA_76ERS, "PHI",
                            Team.LOS_ANGELES_LAKERS, "LAL",
                            Team.MILWAUKEE_BUCKS, "MIL",
                            Team.MINNESOTA_TIMBERWOLVES, "MIN",
                            Team.NEW_ORLEANS_PELICANS, "NOP",
                            Team.SACRAMENTO_KINGS, "SAC",
                            Team.SAN_ANTONIO_SPURS, "SAS"
                    )
            ),
            Team.class
    );

    private TeamSerializationUtility(@NotNull final EnumMap<Team, String> serializationsByValue, @NotNull final Class<Team> keyClass) {
        super(serializationsByValue, keyClass);
    }

    @NotNull
    public static TeamSerializationUtility getInstance() {
        return INSTANCE;
    }
}

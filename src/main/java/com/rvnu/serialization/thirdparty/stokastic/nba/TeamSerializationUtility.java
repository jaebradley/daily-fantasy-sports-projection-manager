package com.rvnu.serialization.thirdparty.stokastic.nba;

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
                            Map.entry(Team.ATLANTA_HAWKS, "ATL"),
                            Map.entry(Team.BOSTON_CELTICS, "BOS"),
                            Map.entry(Team.BROOKLYN_NETS, "BKN"),
                            Map.entry(Team.CHARLOTTE_HORNETS, "CHA"),
                            Map.entry(Team.CHICAGO_BULLS, "CHI"),
                            Map.entry(Team.CLEVELAND_CAVALIERS, "CLE"),
                            Map.entry(Team.DALLAS_MAVERICKS, "DAL"),
                            Map.entry(Team.DENVER_NUGGETS, "DEN"),
                            Map.entry(Team.GOLDEN_STATE_WARRIORS, "GSW"),
                            Map.entry(Team.INDIANA_PACERS, "IND"),
                            Map.entry(Team.PHILADELPHIA_76ERS, "PHI"),
                            Map.entry(Team.LOS_ANGELES_CLIPPERS, "LAC"),
                            Map.entry(Team.LOS_ANGELES_LAKERS, "LAL"),
                            Map.entry(Team.MIAMI_HEAT, "MIA"),
                            Map.entry(Team.MILWAUKEE_BUCKS, "MIL"),
                            Map.entry(Team.MINNESOTA_TIMBERWOLVES, "MIN"),
                            Map.entry(Team.OKLAHOMA_CITY_THUNDER, "OKC"),
                            Map.entry(Team.ORLANDO_MAGIC, "ORL"),
                            Map.entry(Team.NEW_ORLEANS_PELICANS, "NOP"),
                            Map.entry(Team.NEW_YORK_KNICKS, "NYK"),
                            Map.entry(Team.PHOENIX_SUNS, "PHX"),
                            Map.entry(Team.SACRAMENTO_KINGS, "SAC"),
                            Map.entry(Team.SAN_ANTONIO_SPURS, "SAS"),
                            Map.entry(Team.TORONTO_RAPTORS, "TOR"),
                            Map.entry(Team.DETROIT_PISTONS, "DET"),
                            Map.entry(Team.HOUSTON_ROCKETS, "HOU"),
                            Map.entry(Team.MEMPHIS_GRIZZLIES, "MEM"),
                            Map.entry(Team.UTAH_JAZZ, "UTA"),
                            Map.entry(Team.WASHINGTON_WIZARDS, "WAS"),
                            Map.entry(Team.PORTLAND_TRAILBLAZERS, "POR")
                    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
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

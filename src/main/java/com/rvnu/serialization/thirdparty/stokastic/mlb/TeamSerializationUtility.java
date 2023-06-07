package com.rvnu.serialization.thirdparty.stokastic.mlb;

import com.rvnu.models.thirdparty.stokastic.mlb.Team;
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
                            Map.entry(Team.ARIZONA_DIAMONDBACKS, "ARI"),
                            Map.entry(Team.ATLANTA_BRAVES, "ATL"),
                            Map.entry(Team.BALTIMORE_ORIOLES, "BAL"),
                            Map.entry(Team.BOSTON_RED_SOX, "BOS"),
                            Map.entry(Team.CHICAGO_CUBS, "CHC"),
                            Map.entry(Team.CINCINNATI_REDS, "CIN"),
                            Map.entry(Team.CLEVELAND_GUARDIANS, "CLE"),
                            Map.entry(Team.COLORADO_ROCKIES, "COL"),
                            Map.entry(Team.CHICAGO_WHITE_SOX, "CWS"),
                            Map.entry(Team.DETROIT_TIGERS, "DET"),
                            Map.entry(Team.HOUSTON_ASTROS, "HOU"),
                            Map.entry(Team.KANSAS_CITY_ROYALS, "KC"),
                            Map.entry(Team.LOS_ANGELES_ANGELS, "LAA"),
                            Map.entry(Team.LOS_ANGELES_DODGERS, "LAD"),
                            Map.entry(Team.MIAMI_MARLINS, "MIA"),
                            Map.entry(Team.MILWAUKEE_BREWERS, "MIL"),
                            Map.entry(Team.MINNESOTA_TWINS, "MIN"),
                            Map.entry(Team.NEW_YORK_METS, "NYM"),
                            Map.entry(Team.NEW_YORK_YANKEES, "NYY"),
                            Map.entry(Team.OAKLAND_ATHLETICS, "OAK"),
                            Map.entry(Team.PHILADELPHIA_PHILLIES, "PHI"),
                            Map.entry(Team.PITTSBURGH_PIRATES, "PIT"),
                            Map.entry(Team.SAN_DIEGO_PADRES, "SD"),
                            Map.entry(Team.SEATTLE_MARINERS, "SEA"),
                            Map.entry(Team.SAN_FRANCISCO_GIANTS, "SF"),
                            Map.entry(Team.ST_LOUIS_CARDINALS, "STL"),
                            Map.entry(Team.TAMPA_BAY_RAYS, "TB"),
                            Map.entry(Team.TEXAS_RANGERS, "TEX"),
                            Map.entry(Team.TORONTO_BLUE_JAYS, "TOR"),
                            Map.entry(Team.WASHINGTON_NATIONALS, "WAS")
                    ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue))
            ),
            Team.class
    );

    private TeamSerializationUtility(
            @NotNull final EnumMap<Team, String> serializationsByValue,
            @NotNull final Class<Team> keyClass) {
        super(serializationsByValue, keyClass);
    }

    @NotNull
    public static TeamSerializationUtility getInstance() {
        return INSTANCE;
    }
}

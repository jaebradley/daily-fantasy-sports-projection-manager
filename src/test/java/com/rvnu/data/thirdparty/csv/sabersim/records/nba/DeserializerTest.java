package com.rvnu.data.thirdparty.csv.sabersim.records.nba;

import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeserializerTest extends TestCase {

    public void test() {
        try {
            Deserializer
                    .getDraftKingsDeserializer()
                    .deserialize(
                            new ByteArrayInputStream(
                                    ("""
                                            DFS ID,Name,Pos,Team,Opp,Status,Salary,Actual,SS Proj,My Proj,Value,SS Own,My Own,Min Exp,Max Exp,Saber Team,Saber Total,dk_points,dk_25_percentile,dk_50_percentile,dk_75_percentile,dk_85_percentile,dk_95_percentile,dk_99_percentile,fd_points,fd_25_percentile,fd_50_percentile,fd_75_percentile,fd_85_percentile,fd_95_percentile,fd_99_percentile,yahoo_points,yahoo_25_percentile,yahoo_50_percentile,yahoo_75_percentile,yahoo_85_percentile,yahoo_95_percentile,yahoo_99_percentile,dk_std,fd_std,yahoo_std,PTS,Min,2PT,3PT,RB,Off Reb,Def Reb,AST,STL,BLK,TO
                                            20840977,LeBron James,"PG,SF,F,G,UTIL",LAL,MIN,Confirmed,12000,51.25,53.34,53.34,4.445,24.3,24.3,,,113.0243,222.2351,53.337,46.5,53.25,60.25,64.5,71.5,78.75,51.2304,44.4,51.3,58,62.3,69.22,76.644,51.2304,44.4,51.3,58,62.3,69.22,76.644,10.5865,10.6244,10.6244,28.4665,36.322,7.81798,2.41415,9.0059,1.67133,7.33456,5.86957,1.36993,1.08032,4.19823
                                            """).getBytes(StandardCharsets.UTF_8)
                            ),
                            (draftKingsPlayerProjection -> {
                                try {
                                    assertEquals(
                                            new DraftKingsPlayerProjection(
                                                    new PlayerId(20840977),
                                                    new NonEmptyString("LeBron James"),
                                                    Stream.of(Position.POINT_GUARD, Position.SMALL_FORWARD, Position.FORWARD, Position.GUARD, Position.UTILITY).collect(Collectors.toCollection(LinkedHashSet::new)),
                                                    Team.LOS_ANGELES_LAKERS,
                                                    Team.MINNESOTA_TIMBERWOLVES,
                                                    new NonNegativeDollars(new NonNegativeDecimal(new BigDecimal("12000"))),
                                                    new BigDecimal("53.34"),
                                                    new NonNegativeDecimal(new BigDecimal("24.3"))
                                            ),
                                            draftKingsPlayerProjection
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NonNegativeDecimal.ValueCannotBeNegative | NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
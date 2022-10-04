package com.rvnu.data.thirdparty.csv.awesomeo.records.nba;

import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Position;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DeserializerTest extends TestCase {

    public void test() {
        try {
            Deserializer
                    .getInstance()
                    .deserialize(
                            new ByteArrayInputStream(
                                    ("""
                                            "Name","Fpts","Position","Team","Opponent","Minutes","Salary","Pts/$","Value"
                                            "Stephen Curry","56.41","PG","GSW","SAC","37.9","11,000","5.13","1.41"
                                            """).getBytes(StandardCharsets.UTF_8)
                            ),
                            (projection -> {
                                try {
                                    assertEquals(
                                            new Projection(
                                                    new NonEmptyString("Stephen Curry"),
                                                    new BigDecimal("56.41"),
                                                    Set.of(Position.POINT_GUARD),
                                                    Team.GOLDEN_STATE_WARRIORS,
                                                    Team.SACRAMENTO_KINGS,
                                                    new NonNegativeDecimal(new BigDecimal("37.9")),
                                                    new NonNegativeDollars(new NonNegativeDecimal(new BigDecimal("11,000"))),
                                                    new BigDecimal("5.13"),
                                                    new BigDecimal("1.41")
                                            ),
                                            projection
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NonNegativeDecimal.ValueCannotBeNegative e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
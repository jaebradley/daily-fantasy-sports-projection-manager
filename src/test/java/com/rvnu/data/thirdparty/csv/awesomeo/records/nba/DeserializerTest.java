package com.rvnu.data.thirdparty.csv.awesomeo.records.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.awesomeo.nba.Position;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DeserializerTest extends TestCase {

    public void test() {
        final AtomicInteger count = new AtomicInteger(0);
        final Map<com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
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
                                                    NonEmptyLinkedHashSet.from(List.of(Position.POINT_GUARD)),
                                                    Team.GOLDEN_STATE_WARRIORS,
                                                    Team.SACRAMENTO_KINGS,
                                                    new NonNegativeDecimal(new BigDecimal("37.9")),
                                                    new NonNegativeDollars(new NaturalNumber(11000)),
                                                    new BigDecimal("5.13"),
                                                    new BigDecimal("1.41")
                                            ),
                                            projection
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NonNegativeDecimal.ValueCannotBeNegative | NonEmptyLinkedHashSet.CollectionCannotBeEmpty | NaturalNumber.ValueMustNotBeNegative e) {
                                    throw new RuntimeException("unexpected", e);
                                } finally {
                                    count.getAndIncrement();
                                }
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        assertTrue(result.isEmpty());
        assertEquals(1, count.get());
    }
}
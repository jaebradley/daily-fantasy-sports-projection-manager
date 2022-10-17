package com.rvnu.data.thirdparty.csv.dailyroto.records.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.dailyroto.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.numbers.NonNegativePercentage;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DeserializerTest extends TestCase {

    public void testDraftKings() {
        final AtomicInteger counter = new AtomicInteger(0);
        try {
            Deserializer
                    .getDraftKingsDeserializer()
                    .deserialize(
                            new ByteArrayInputStream(
                                    ("""
                                            Team,Opp,Player,Slate PlayerID,,Minutes,Usage Rate,Rebound Rate,AssistRate,,Pos,Public %,Optimal%,Leverage,Salary,Points,Value
                                            PHI,BOS,Joel Embiid,24617404,,35.5,33.56,18.05,17.58,,C,28.9,47,18.1,9900,51.01,4.41
                                            """).getBytes(StandardCharsets.UTF_8)
                            ),
                            (contestPlayer -> {
                                try {
                                    assertEquals(
                                            new Projection<>(
                                                    Team.PHILADELPHIA_76ERS,
                                                    Team.BOSTON_CELTICS,
                                                    Optional.of(new PlayerId(24617404)),
                                                    new NonEmptyString("Joel Embiid"),
                                                    new NonNegativeDecimal(new BigDecimal("35.5")),
                                                    new NonNegativePercentage(new BigDecimal("33.56")),
                                                    new NonNegativePercentage(new BigDecimal("18.05")),
                                                    new NonNegativePercentage(new BigDecimal("17.58")),
                                                    NonEmptyLinkedHashSet.from(List.of(Position.CENTER)),
                                                    new NonNegativePercentage(new BigDecimal("28.9")),
                                                    new NonNegativeDollars(new NaturalNumber(9900)),
                                                    new BigDecimal("51.01"),
                                                    new BigDecimal("4.41")
                                            ),
                                            contestPlayer
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NaturalNumber.ValueMustNotBeNegative | NonEmptyLinkedHashSet.CollectionCannotBeEmpty | NonNegativeDecimal.ValueCannotBeNegative | NonNegativePercentage.ValueCannotBeGreaterThan100 | PositiveInteger.ValueMustBePositive e) {
                                    throw new RuntimeException("unexpected", e);
                                }

                                counter.getAndIncrement();
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        assertEquals(1, counter.get());
    }
}
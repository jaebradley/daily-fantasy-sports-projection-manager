package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.ContestPlayerId;
import com.rvnu.models.thirdparty.fanduel.nba.FixtureListId;
import com.rvnu.models.thirdparty.fanduel.nba.PlayerId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Optional;
import java.util.stream.Stream;

public class ContestPlayerIdSerializationUtilityTest extends TestCase {

    public void test() {
        try {
            Assert.assertEquals(
                    Optional.of(new ContestPlayerId(new FixtureListId(91576), new PlayerId(9488))),
                    ContestPlayerIdSerializationUtility.getInstance().deserialize("91576-9488"));
        } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }

        Stream.of(
                        "foo",
                        "1-2-3",
                        "1-",
                        "-1-2",
                        // TODO: @jbradley modify the PositiveIntegerSerializationUtility to handle +1-2 case
                        "*1-2"
                )
                .forEach(v -> assertEquals(Optional.empty(), ContestPlayerIdSerializationUtility.getInstance().deserialize(v)));
    }
}
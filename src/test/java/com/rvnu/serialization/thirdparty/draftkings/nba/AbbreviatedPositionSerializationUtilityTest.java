package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.Position;
import io.vavr.collection.Map;
import junit.framework.TestCase;

import java.util.Optional;
import java.util.stream.Stream;

public class AbbreviatedPositionSerializationUtilityTest extends TestCase {

    public void test() {
        Stream.of(
                        Map.entry(Position.CENTER, "C"),
                        Map.entry(Position.UTILITY, "UTIL")
                )
                .map(e -> Map.entry(Optional.of(e._1()), e._2()))
                .forEach(e -> assertEquals(e._1(), AbbreviatedPositionSerializationUtility.getInstance().deserialize(e._2())));
    }
}
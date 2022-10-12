package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Position;
import junit.framework.TestCase;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class PositionsSerializationUtilityTest extends TestCase {

    public void test() {
        Stream.of(
                Map.entry("SF", Optional.of(Set.of(Position.SMALL_FORWARD))),
                Map.entry("SF/PF", Optional.of(Set.of(Position.SMALL_FORWARD, Position.POWER_FORWARD))),
                Map.entry("foo", Optional.empty()),
                Map.entry("", Optional.empty()),
                Map.entry("blah/blah", Optional.empty()),
                Map.entry("SF/SF", Optional.empty())
        ).forEach(e -> assertEquals(e.getValue(), PositionsSerializationUtility.getInstance().deserialize(e.getKey())));
    }
}
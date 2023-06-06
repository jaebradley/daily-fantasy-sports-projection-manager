package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import io.vavr.collection.Map;
import junit.framework.TestCase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class AbbreviatedPositionsSerializationUtilityTest extends TestCase {

    public void test() {
        try {
            Stream.of(
                            Map.entry(NonEmptyLinkedHashSet.from(List.of(Position.CENTER)), "C"),
                            Map.entry(NonEmptyLinkedHashSet.from(List.of(Position.CENTER, Position.UTILITY)), "C/UTIL")
                    )
                    .map(e -> Map.entry(Optional.of(e._1()), e._2()))
                    .forEach(e -> assertEquals(e._1(), AbbreviatedPositionsSerializationUtility.getInstance().deserialize(e._2())));
        } catch (NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
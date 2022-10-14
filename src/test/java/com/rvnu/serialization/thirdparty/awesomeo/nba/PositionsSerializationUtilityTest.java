package com.rvnu.serialization.thirdparty.awesomeo.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.awesomeo.nba.Position;
import junit.framework.TestCase;

import java.util.List;
import java.util.Optional;

public class PositionsSerializationUtilityTest extends TestCase {

    public void test() {
        try {
            assertEquals(Optional.of(NonEmptyLinkedHashSet.from(List.of(Position.POINT_GUARD))), PositionsSerializationUtility.getInstance().deserialize("PG"));
            assertEquals(Optional.of(NonEmptyLinkedHashSet.from(List.of(Position.POINT_GUARD, Position.SHOOTING_GUARD))), PositionsSerializationUtility.getInstance().deserialize("PG/SG"));
        } catch (NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
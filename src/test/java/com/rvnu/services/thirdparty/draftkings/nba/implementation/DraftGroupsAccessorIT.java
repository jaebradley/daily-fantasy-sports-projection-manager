package com.rvnu.services.thirdparty.draftkings.nba.implementation;

import com.rvnu.data.thirdparty.csv.draftkings.records.nba.Deserializer;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;

import java.net.http.HttpClient;

public class DraftGroupsAccessorIT extends TestCase {
    public void test() {
        try {
            new DraftGroupsAccessor(
                    HttpClient.newBuilder().build(),
                    Deserializer.getInstance()
            ).getPlayers(PositiveInteger.ONE, TestCase::assertNotNull);
        } catch (com.rvnu.services.thirdparty.draftkings.nba.interfaces.DraftGroupsAccessor.UnableToAccessPlayers e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
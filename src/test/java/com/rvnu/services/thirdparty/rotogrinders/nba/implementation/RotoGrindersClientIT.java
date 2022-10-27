package com.rvnu.services.thirdparty.rotogrinders.nba.implementation;

import com.rvnu.models.thirdparty.rotogrinders.nba.SlatePlayerProjection;
import com.rvnu.models.thirdparty.rotogrinders.nba.Slates;
import com.rvnu.services.thirdparty.rotogrinders.nba.interfaces.SlateProjectionsAccessor;
import com.rvnu.services.thirdparty.rotogrinders.nba.interfaces.SlatesAccessor;
import junit.framework.TestCase;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.ZonedDateTime;
import java.util.List;

public class RotoGrindersClientIT extends TestCase {
    public void testGetSlates() {
        try {
            final Slates slates = new RotoGrindersClient(HttpClient.newBuilder().build())
                    .getSlates(ZonedDateTime.parse("2022-10-24T00:00:00.000Z"));
            assertNotNull(slates);
            assertFalse(slates.getSlatesBySiteName().isEmpty());
        } catch (SlatesAccessor.UnableToGetSlates e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    public void testGetSlateProjections() {
        try {
            final List<SlatePlayerProjection> projections = new RotoGrindersClient(HttpClient.newBuilder().build()).getProjections(
                    URI.create("http://json.rotogrinders.com.s3.amazonaws.com/v2.00/slates/draftkings/25/76625.json")
            );
            assertNotNull(projections);
            assertFalse(projections.isEmpty());
        } catch (SlateProjectionsAccessor.UnableToGetProjections e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
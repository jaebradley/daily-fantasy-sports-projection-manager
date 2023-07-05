package com.rvnu.services.thirdparty.draftkings.nba.implementation;

import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer;
import com.rvnu.models.thirdparty.draftkings.draftgroups.DraftGroupDetails;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class DraftGroupsAccessor implements com.rvnu.services.thirdparty.draftkings.nba.interfaces.DraftGroupsAccessor {

    @NotNull
    private final HttpClient client;

    @NotNull
    private final AbstractDeserializer<ContestPlayer, Deserializer.Column, Deserializer.Error> recordDeserializer;

    public DraftGroupsAccessor(@NotNull final HttpClient client, @NotNull final AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error> recordDeserializer) {
        this.client = client;
        this.recordDeserializer = recordDeserializer;
    }

    @Override
    public void getDraftGroups(@NotNull final Consumer<DraftGroupDetails> draftGroupDetailsConsumer) throws UnableToAccessContests {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void getPlayers(@NotNull final PositiveInteger draftGroupId, @NotNull final Consumer<ContestPlayer> playerConsumer) throws UnableToAccessPlayers {
        final HttpResponse<InputStream> responseStream;
        try {
            responseStream = client.sendAsync(
                    HttpRequest
                            .newBuilder()
                            .uri(URI.create("https://www.draftkings.com/lineup/getavailableplayerscsv?draftGroupId=84962"))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofInputStream()
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new UnableToAccessPlayers();
        }

        final Map<Deserializer.Error, PositiveInteger> results;
        try {
            results = this.recordDeserializer.deserialize(responseStream.body(), playerConsumer);
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new UnableToAccessPlayers();
        }

        if (!results.isEmpty()) {
            throw new UnableToAccessPlayers();
        }
    }
}

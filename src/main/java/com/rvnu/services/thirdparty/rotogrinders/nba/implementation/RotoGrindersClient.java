package com.rvnu.services.thirdparty.rotogrinders.nba.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rvnu.models.thirdparty.dailyroto.nba.DailyProjections;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.rotogrinders.nba.SlatePlayerProjection;
import com.rvnu.models.thirdparty.rotogrinders.nba.Slates;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import com.rvnu.serialization.firstparty.json.numbers.PositiveIntegerKeyDeserializer;
import com.rvnu.services.thirdparty.dailyroto.nba.interfaces.DailyProjectionsAccessor;
import com.rvnu.services.thirdparty.rotogrinders.nba.interfaces.SlateProjectionsAccessor;
import com.rvnu.services.thirdparty.rotogrinders.nba.interfaces.SlatesAccessor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RotoGrindersClient implements SlatesAccessor, SlateProjectionsAccessor {
    @NotNull
    private final HttpClient httpClient;

    public RotoGrindersClient(@NotNull final HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<SlatePlayerProjection> getProjections(@NotNull final URI projectionPath) throws UnableToGetProjections {
        try {
            return httpClient.sendAsync(
                    HttpRequest.newBuilder(projectionPath)
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofInputStream()
            ).thenApplyAsync(HttpResponse::body)
                    .thenApplyAsync(body -> {
                        try {
                            return (List<SlatePlayerProjection>) new ObjectMapper().readerForListOf(SlatePlayerProjection.class).readValue(body, List.class);
                        } catch (IOException e) {
                            throw new RuntimeException("unexpected", e);
                        }
                    }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new UnableToGetProjections();
        }
    }

    @Override
    public Slates getSlates(@NotNull final ZonedDateTime time) throws UnableToGetSlates {
        // TODO: @jbradley handle non-200 response bodies and unable to read bodies
        final CompletableFuture<Slates> future;
        try {
            future = httpClient.sendAsync(
                            HttpRequest.newBuilder()
                                    .uri(new URI(String.format("https://s3.amazonaws.com/json.rotogrinders.com/v2.00/%s/%s/%s/slates/nba-master.json", time.getYear(), time.getMonthValue(), time.getDayOfMonth())))
                                    .GET()
                                    .build(),
                            HttpResponse.BodyHandlers.ofInputStream())
                    .thenApplyAsync(HttpResponse::body)
                    .thenApplyAsync(v -> {
                                try {
                                    // TODO: @jbradley move this to its own serializer
                                    return new ObjectMapper().registerModule(new SimpleModule().addKeyDeserializer(PositiveInteger.class, new PositiveIntegerKeyDeserializer())).readerFor(Slates.class).readValue(v, Slates.class);
                                } catch (IOException e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            }
                    );
        } catch (URISyntaxException e) {
            throw new RuntimeException("unexpected", e);
        }

        if (future.isCancelled() || future.isCompletedExceptionally()) {
            throw new UnableToGetSlates();
        }

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new UnableToGetSlates();
        }
    }
}

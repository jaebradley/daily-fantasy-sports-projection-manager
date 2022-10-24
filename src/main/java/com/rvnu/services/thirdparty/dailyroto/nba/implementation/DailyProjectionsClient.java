package com.rvnu.services.thirdparty.dailyroto.nba.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvnu.models.thirdparty.dailyroto.nba.DailyProjections;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import com.rvnu.services.thirdparty.dailyroto.nba.interfaces.DailyProjectionsAccessor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class DailyProjectionsClient implements DailyProjectionsAccessor {
    @NotNull
    private static final URI BASE_URL = URI.create("https://dailyroto.com/OPTO/data/data_all.php?sport=NBA&action=undefined&slateID=undefined");

    @NotNull
    private final HttpClient httpClient;

    @NotNull
    private final Serializer<ZonedDateTime> yearMonthDaySerializer;

    public DailyProjectionsClient(@NotNull final HttpClient httpClient, @NotNull final Serializer<ZonedDateTime> yearMonthDaySerializer) {
        this.httpClient = httpClient;
        this.yearMonthDaySerializer = yearMonthDaySerializer;
    }

    @Override
    public void getProjections(@NotNull ZonedDateTime time, @NotNull Consumer<DailyProjections> projectionsConsumer) throws UnableToGetProjections {
        // TODO: @jbradley handle non-200 response bodies and unable to read bodies
        final CompletableFuture<DailyProjections> future;
        try {
            future = httpClient.sendAsync(
                            HttpRequest.newBuilder()
                                    .uri(new URI(
                                            BASE_URL.getScheme(),
                                            BASE_URL.getAuthority(),
                                            BASE_URL.getPath(),
                                            BASE_URL.getQuery() + "&date=" + yearMonthDaySerializer.serialize(time),
                                            BASE_URL.getFragment()))
                                    .timeout(Duration.ofSeconds(10))
                                    .GET()
                                    .build(),
                            HttpResponse.BodyHandlers.ofInputStream())
                    .thenApplyAsync(HttpResponse::body)
                    .thenApplyAsync(v -> {
                                try {
                                    return new ObjectMapper().readerFor(DailyProjections.class).readValue(v, DailyProjections.class);
                                } catch (IOException e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            }
                    );
        } catch (URISyntaxException e) {
            throw new RuntimeException("unexpected", e);
        }

        if (future.isCancelled() || future.isCompletedExceptionally()) {
            throw new UnableToGetProjections();
        }

        try {
            projectionsConsumer.accept(future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new UnableToGetProjections();
        }
    }
}

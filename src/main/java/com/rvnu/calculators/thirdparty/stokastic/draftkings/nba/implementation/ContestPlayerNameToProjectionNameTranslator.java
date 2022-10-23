package com.rvnu.calculators.thirdparty.stokastic.draftkings.nba.implementation;

import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContestPlayerNameToProjectionNameTranslator implements Function<NonEmptyString, NonEmptyString> {
    @NotNull
    private static final ContestPlayerNameToProjectionNameTranslator INSTANCE = new ContestPlayerNameToProjectionNameTranslator(
            Stream.of(
                            Map.entry("Deandre Ayton", "DeAndre Ayton"),
                            Map.entry("Aaron Nesmith", "Aaron NeSmith")
                    ).map(v -> {
                        try {
                            return Map.entry(new NonEmptyString(v.getKey()), new NonEmptyString(v.getValue()));
                        } catch (NonEmptyString.ValueMustNotBeEmpty e) {
                            throw new RuntimeException("unexpected", e);
                        }
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
    );

    @NotNull
    private final Map<NonEmptyString, NonEmptyString> projectionNamesBySiteName;

    private ContestPlayerNameToProjectionNameTranslator(@NotNull final Map<NonEmptyString, NonEmptyString> projectionNamesBySiteName) {
        this.projectionNamesBySiteName = projectionNamesBySiteName;
    }

    @Override
    public @NotNull NonEmptyString apply(@NotNull final NonEmptyString projectionName) {
        return projectionNamesBySiteName.getOrDefault(projectionName, projectionName);
    }

    @NotNull
    public static ContestPlayerNameToProjectionNameTranslator getInstance() {
        return INSTANCE;
    }
}

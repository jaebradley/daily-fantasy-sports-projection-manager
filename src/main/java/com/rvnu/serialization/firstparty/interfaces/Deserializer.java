package com.rvnu.serialization.firstparty.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Deserializer<T> {
    Optional<T> deserialize(@NotNull String value);
}

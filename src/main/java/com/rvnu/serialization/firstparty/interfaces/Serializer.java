package com.rvnu.serialization.firstparty.interfaces;

import org.jetbrains.annotations.NotNull;

public interface Serializer<T> {
    String serialize(@NotNull T value);
}

package com.rvnu.data.firstparty.csv.record.printing.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public interface ColumnValuesPrinter<T, Column extends Enum<Column>> {
    EnumMap<Column, String> printColumnValues(@NotNull T value);
}

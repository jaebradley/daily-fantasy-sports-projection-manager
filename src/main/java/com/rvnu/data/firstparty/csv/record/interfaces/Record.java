package com.rvnu.data.firstparty.csv.record.interfaces;

import java.util.Optional;

public interface Record<Column extends Enum<Column>> {
    Optional<String> getValue(Column column);
}


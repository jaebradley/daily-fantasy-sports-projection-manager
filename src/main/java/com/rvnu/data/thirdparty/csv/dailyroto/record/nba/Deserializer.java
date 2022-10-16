package com.rvnu.data.thirdparty.csv.dailyroto.record.nba;

import com.rvnu.data.firstparty.csv.record.interfaces.Record;
import com.rvnu.models.thirdparty.dailyroto.nba.Projection;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

public abstract class Deserializer<SitePosition> implements com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Projection<SitePosition>, Deserializer.Column, Deserializer.Error> {
    public enum Column {

    }

    public enum Error {

    }

    @Override
    public @NotNull Either<Error, Projection<SitePosition>> deserialize(@NotNull final Record<Column> record) {
        return null;
    }
}

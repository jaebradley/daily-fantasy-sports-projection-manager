package com.rvnu.data.firstparty.csv.draftkings.record.nba.projections;

import com.rvnu.applications.nba.draftkings.ProjectionsMerger;
import com.rvnu.data.firstparty.csv.record.printing.interfaces.ColumnValuesPrinter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Printer implements com.rvnu.data.firstparty.csv.record.printing.interfaces.Printer<ProjectionsMerger.Projections> {

    @NotNull
    private static final Printer INSTANCE = new Printer(com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.printing.ColumnValuesPrinter.getInstance());

    public enum Column {
        DraftKingsPlayerId,
        Name,
        Salary,
        Positions,
        StokasticProjection,
        RotoGrindersProjection,
        SaberSimProjection,
        DailyRotoProjection,
    }


    @NotNull
    private final ColumnValuesPrinter<ProjectionsMerger.Projections, Column> valuesPrinter;

    public Printer(@NotNull ColumnValuesPrinter<ProjectionsMerger.Projections, Column> valuesPrinter) {
        this.valuesPrinter = valuesPrinter;
    }

    @Override
    public void print(@NotNull final ProjectionsMerger.Projections record, @NotNull final Consumer<String> valueConsumer) {
        final EnumMap<Column, String> serializedValuesByColumn = valuesPrinter.printColumnValues(record);
        for (final Column column : Arrays.stream(Column.values()).sorted(Comparator.comparingInt(Enum::ordinal)).collect(Collectors.toList())) {
            valueConsumer.accept(serializedValuesByColumn.get(column));
        }
    }

    @NotNull
    public static Printer getInstance() {
        return INSTANCE;
    }
}

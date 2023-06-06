package com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.printing;

import com.rvnu.applications.nba.draftkings.ProjectionsMerger;
import com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.Printer;
import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import com.rvnu.serialization.firstparty.numbers.BigDecimalSerializationUtility;
import com.rvnu.serialization.firstparty.numbers.NonNegativeDollarsSerializationUtility;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.PlayerIdSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.PositionsSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColumnValuesPrinter implements com.rvnu.data.firstparty.csv.record.printing.interfaces.ColumnValuesPrinter<ProjectionsMerger.Projections, Printer.Column> {
    @NotNull
    private static final ColumnValuesPrinter INSTANCE = new ColumnValuesPrinter(
            PlayerIdSerializationUtility.getInstance(),
            NonEmptyStringSerializationUtility.getInstance(),
            NonNegativeDollarsSerializationUtility.getInstance(),
            PositionsSerializationUtility.getInstance(),
            BigDecimalSerializationUtility.getInstance(),
            BigDecimalSerializationUtility.getInstance(),
            BigDecimalSerializationUtility.getInstance(),
            BigDecimalSerializationUtility.getInstance()
    );

    @NotNull
    private final Serializer<PlayerId> playerIdSerializer;

    @NotNull
    private final Serializer<NonEmptyString> playerNameSerializer;

    @NotNull
    private final Serializer<NonNegativeDollars> salarySerializer;

    @NotNull
    private final Serializer<NonEmptyLinkedHashSet<Position>> positionsSerializer;

    @NotNull
    private final Serializer<BigDecimal> stokasticProjectionSerializer;

    @NotNull
    private final Serializer<BigDecimal> rotogrindersProjectionSerializer;

    @NotNull
    private final Serializer<BigDecimal> sabersimProjectionSerializer;

    @NotNull
    private final Serializer<BigDecimal> dailyRotoProjectionSerializer;

    private ColumnValuesPrinter(@NotNull Serializer<PlayerId> playerIdSerializer, @NotNull Serializer<NonEmptyString> playerNameSerializer, @NotNull Serializer<NonNegativeDollars> salarySerializer, @NotNull Serializer<NonEmptyLinkedHashSet<Position>> positionsSerializer, @NotNull Serializer<BigDecimal> stokasticProjectionSerializer, @NotNull Serializer<BigDecimal> rotogrindersProjectionSerializer, @NotNull Serializer<BigDecimal> sabersimProjectionSerializer, @NotNull Serializer<BigDecimal> dailyRotoProjectionSerializer) {
        this.playerIdSerializer = playerIdSerializer;
        this.playerNameSerializer = playerNameSerializer;
        this.salarySerializer = salarySerializer;
        this.positionsSerializer = positionsSerializer;
        this.stokasticProjectionSerializer = stokasticProjectionSerializer;
        this.rotogrindersProjectionSerializer = rotogrindersProjectionSerializer;
        this.sabersimProjectionSerializer = sabersimProjectionSerializer;
        this.dailyRotoProjectionSerializer = dailyRotoProjectionSerializer;
    }

    @Override
    public EnumMap<Printer.Column, String> printColumnValues(@NotNull final ProjectionsMerger.Projections value) {
        return new EnumMap<Printer.Column, String>(
                Stream.of(
                        Map.entry(Printer.Column.DraftKingsPlayerId, playerIdSerializer.serialize(value.contestPlayer().id())),
                        Map.entry(Printer.Column.Name, playerNameSerializer.serialize(value.contestPlayer().name())),
                        Map.entry(Printer.Column.Salary, salarySerializer.serialize(value.contestPlayer().salary())),
                        Map.entry(Printer.Column.Positions, positionsSerializer.serialize(value.contestPlayer().eligiblePositions())),
                        Map.entry(Printer.Column.StokasticProjection, value.awesomeo().map(Projection::fantasyPoints).map(stokasticProjectionSerializer::serialize).orElse("")),
                        Map.entry(Printer.Column.DailyRotoProjection, value.dailyRoto().map(com.rvnu.models.thirdparty.dailyroto.nba.Projection::points).map(dailyRotoProjectionSerializer::serialize).orElse("")),
                        Map.entry(Printer.Column.RotoGrindersProjection, value.rotogrinders().map(com.rvnu.models.thirdparty.rotogrinders.nba.Projection::fantasyPoints).map(rotogrindersProjectionSerializer::serialize).orElse("")),
                        Map.entry(Printer.Column.SaberSimProjection, value.sabersim().map(com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection::getProjectedPoints).map(sabersimProjectionSerializer::serialize).orElse(""))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    @NotNull
    public static ColumnValuesPrinter getInstance() {
        return INSTANCE;
    }
}


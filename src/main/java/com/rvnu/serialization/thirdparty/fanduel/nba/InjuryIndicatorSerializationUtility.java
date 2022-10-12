package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.InjuryIndicator;
import com.rvnu.serialization.firstparty.enumerations.AbstractEnumeratedValuesSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjuryIndicatorSerializationUtility extends AbstractEnumeratedValuesSerializationUtility<InjuryIndicator> {
    @NotNull
    private static final InjuryIndicatorSerializationUtility INSTANCE = new InjuryIndicatorSerializationUtility(
            new EnumMap<InjuryIndicator, String>(
                    Stream.of(
                            Map.entry(InjuryIndicator.Game_Time_Decision, "GTD"),
                            Map.entry(InjuryIndicator.Out, "O")
                    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            ),
            InjuryIndicator.class
    );

    private InjuryIndicatorSerializationUtility(
            @NotNull final EnumMap<InjuryIndicator, String> serializationsByValue,
            @NotNull final Class<InjuryIndicator> keyClass
    ) {
        super(serializationsByValue, keyClass);
    }

    @NotNull
    public static InjuryIndicatorSerializationUtility getInstance() {
        return INSTANCE;
    }
}

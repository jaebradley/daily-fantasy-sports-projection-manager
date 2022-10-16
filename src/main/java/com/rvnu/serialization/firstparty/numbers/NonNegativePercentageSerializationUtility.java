package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.numbers.NonNegativePercentage;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public class NonNegativePercentageSerializationUtility extends NonNegativeDecimalSerializationUtility<NonNegativePercentage> {

    @NotNull
    private static final NonNegativePercentageSerializationUtility INSTANCE = new NonNegativePercentageSerializationUtility(
            BigDecimalSerializationUtility.getInstance()
    );

    private NonNegativePercentageSerializationUtility(@NotNull final Deserializer<BigDecimal> decimalDeserializer) {
        super(decimalDeserializer);
    }

    @Override
    protected Optional<NonNegativePercentage> construct(@NotNull final NonNegativeDecimal value) {
        final NonNegativePercentage nonNegativePercentage;
        try {
            nonNegativePercentage = new NonNegativePercentage(value.getValue());
        } catch (NonNegativeDecimal.ValueCannotBeNegative valueCannotBeNegative) {
            throw new RuntimeException("unexpected", valueCannotBeNegative);
        } catch (NonNegativePercentage.ValueCannotBeGreaterThan100 valueCannotBeGreaterThan100) {
            return Optional.empty();
        }
        return Optional.of(nonNegativePercentage);
    }

    public static NonNegativePercentageSerializationUtility getInstance() {
        return INSTANCE;
    }
}

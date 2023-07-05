package com.rvnu.models.thirdparty.draftkings.draftgroups;

import com.rvnu.models.thirdparty.iso.PositiveInteger;
import org.jetbrains.annotations.NotNull;

public record DraftGroupDetails(
        @NotNull PositiveInteger contestTypeId,
        @NotNull PositiveInteger id
) {
}

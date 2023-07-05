package com.rvnu.services.thirdparty.draftkings.nba.interfaces;

import com.rvnu.models.thirdparty.draftkings.draftgroups.DraftGroupDetails;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface DraftGroupsAccessor {
    class UnableToAccessContests extends Exception {
    }

    class UnableToAccessPlayers extends Exception {
    }

    void getDraftGroups(@NotNull final Consumer<DraftGroupDetails> draftGroupDetailsConsumer) throws UnableToAccessContests;

    void getPlayers(@NotNull final PositiveInteger draftGroupId, @NotNull final Consumer<ContestPlayer> playerConsumer) throws UnableToAccessPlayers;

}

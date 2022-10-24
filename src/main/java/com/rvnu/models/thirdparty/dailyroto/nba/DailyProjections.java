package com.rvnu.models.thirdparty.dailyroto.nba;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.json.numbers.PositiveIntegerDeserializer;
import com.rvnu.serialization.firstparty.json.numbers.PositiveIntegerKeyDeserializer;
import com.rvnu.serialization.firstparty.json.strings.NonEmptyStringDeserializer;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyProjections {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BaselineProjections {

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class PlayerProjection {

            @NotNull
            private final PositiveInteger id;

            @NotNull
            private final NonEmptyString name;

            @NotNull
            private final BigDecimal draftKingsProjection;

            @NotNull
            private final BigDecimal fanDuelProjection;

            @JsonCreator
            public PlayerProjection(
                    @JsonProperty("DK") @NotNull final BigDecimal draftKingsProjection,
                    @JsonProperty("FD") @NotNull final BigDecimal fanDuelProjection,
                    @JsonProperty("Name") @JsonDeserialize(using = NonEmptyStringDeserializer.class)  @NotNull final NonEmptyString name,
                    @JsonProperty("PlayerID") @JsonDeserialize(using = PositiveIntegerDeserializer.class) @NotNull final PositiveInteger id
            ) {
                this.draftKingsProjection = draftKingsProjection;
                this.fanDuelProjection = fanDuelProjection;
                this.name = name;
                this.id = id;
            }

            @NotNull
            public BigDecimal getDraftKingsProjection() {
                return draftKingsProjection;
            }

            @NotNull
            public BigDecimal getFanDuelProjection() {
                return fanDuelProjection;
            }

            @NotNull
            public NonEmptyString getName() {
                return name;
            }

            public PositiveInteger getId() {
                return id;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PlayerProjection that = (PlayerProjection) o;
                return id.equals(that.id) && name.equals(that.name) && draftKingsProjection.equals(that.draftKingsProjection) && fanDuelProjection.equals(that.fanDuelProjection);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id, name, draftKingsProjection, fanDuelProjection);
            }
        }

        @NotNull
        private final List<PlayerProjection> playerProjections;

        @JsonCreator
        public BaselineProjections(@JsonProperty("Players") @NotNull final List<PlayerProjection> playerProjections) {
            this.playerProjections = playerProjections;
        }

        @NotNull
        public List<PlayerProjection> getPlayerProjections() {
            return playerProjections;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BaselineProjections baselineProjections = (BaselineProjections) o;
            return playerProjections.equals(baselineProjections.playerProjections);
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerProjections);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Slate {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SitePlayer {
            @NotNull
            private final NonEmptyString sitePlayerId;

            @JsonCreator
            public SitePlayer(
                    @JsonProperty("SlatePlayerID") @JsonDeserialize(using = NonEmptyStringDeserializer.class) @NotNull final NonEmptyString sitePlayerId
            ) {
                this.sitePlayerId = sitePlayerId;
            }

            @NotNull
            public NonEmptyString getSitePlayerId() {
                return sitePlayerId;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                SitePlayer that = (SitePlayer) o;
                return sitePlayerId.equals(that.sitePlayerId);
            }

            @Override
            public int hashCode() {
                return Objects.hash(sitePlayerId);
            }
        }

        @NotNull
        private final PositiveInteger slateId;

        // TODO: @jbradley move this to custom serialization utility
        @NotNull
        private final NonEmptyString site;

        // TODO: @jbradley there is a relationship between sites and game types i.e. certain sites may only have certain game types
        @NotNull
        private final NonEmptyString gameType;

        @NotNull
        // Apparently both of these @JsonDeserialize annotations need to exist
        @JsonDeserialize(keyUsing = PositiveIntegerKeyDeserializer.class)
        private final LinkedHashMap<PositiveInteger, SitePlayer> playersById;

        @JsonCreator
        public Slate(
                @JsonProperty("SlateID") @JsonDeserialize(using = PositiveIntegerDeserializer.class) @NotNull final PositiveInteger slateId,
                @JsonProperty("Operator") @JsonDeserialize(using = NonEmptyStringDeserializer.class) @NotNull final NonEmptyString site,
                @JsonProperty("GameType") @JsonDeserialize(using = NonEmptyStringDeserializer.class) @NotNull final NonEmptyString gameType,
                @JsonProperty("Players") @JsonDeserialize(keyUsing = PositiveIntegerKeyDeserializer.class) @NotNull final LinkedHashMap<PositiveInteger, SitePlayer> playersById
        ) {
            this.slateId = slateId;
            this.site = site;
            this.gameType = gameType;
            this.playersById = playersById;
        }

        @NotNull
        public PositiveInteger getSlateId() {
            return slateId;
        }

        @NotNull
        public NonEmptyString getSite() {
            return site;
        }

        @NotNull
        public NonEmptyString getGameType() {
            return gameType;
        }

        @NotNull
        public LinkedHashMap<PositiveInteger, SitePlayer> getPlayersById() {
            return playersById;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slate slate = (Slate) o;
            return slateId.equals(slate.slateId) && site.equals(slate.site) && gameType.equals(slate.gameType) && playersById.equals(slate.playersById);
        }

        @Override
        public int hashCode() {
            return Objects.hash(slateId, site, gameType, playersById);
        }
    }

    @NotNull
    private final DailyProjections.BaselineProjections baselineProjections;

    @NotNull
    private final List<Slate> slates;

    @JsonCreator
    public DailyProjections(
            @JsonProperty("Baselines") @NotNull final DailyProjections.BaselineProjections baselineProjections,
            @JsonProperty("Slates") @NotNull final List<Slate> slates
    ) {
        this.baselineProjections = baselineProjections;
        this.slates = slates;
    }

    @NotNull
    public BaselineProjections getBaselineProjections() {
        return baselineProjections;
    }

    @NotNull
    public List<Slate> getSlates() {
        return slates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyProjections that = (DailyProjections) o;
        return baselineProjections.equals(that.baselineProjections) && slates.equals(that.slates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baselineProjections, slates);
    }
}

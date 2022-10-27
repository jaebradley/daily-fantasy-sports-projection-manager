package com.rvnu.models.thirdparty.rotogrinders.nba;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SlatePlayerProjection {
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Player {
        // TODO: @jbradley make this generic for different site / slate ids (DK is a number but FD is not)
        @NotNull
        private final PositiveInteger id;

        @NotNull
        private final NonEmptyString firstName;

        @NotNull
        private final NonEmptyString lastName;

        @JsonCreator
        public Player(
                @JsonProperty("id") @NotNull final PositiveInteger id,
                @JsonProperty("first_name") @NotNull final NonEmptyString firstName,
                @JsonProperty("last_name") @NotNull final NonEmptyString lastName
        ) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @NotNull
        public PositiveInteger getId() {
            return id;
        }

        @NotNull
        public NonEmptyString getFirstName() {
            return firstName;
        }

        @NotNull
        public NonEmptyString getLastName() {
            return lastName;
        }
    }

    @NotNull
    private final BigDecimal projection;

    @NotNull
    private final Player player;

    @JsonCreator
    public SlatePlayerProjection(
            @JsonProperty("fpts") @NotNull final BigDecimal projection,
            @JsonProperty("player") @NotNull final Player player
    ) {
        this.projection = projection;
        this.player = player;
    }

    @NotNull
    public BigDecimal getProjection() {
        return projection;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlatePlayerProjection that = (SlatePlayerProjection) o;
        return projection.equals(that.projection) && player.equals(that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projection, player);
    }
}

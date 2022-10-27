package com.rvnu.models.thirdparty.rotogrinders.nba;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.json.strings.NonEmptyStringDeserializer;
import com.rvnu.serialization.firstparty.json.strings.NonEmptyStringKeyDeserializer;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Slates {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Slate {

        @NotNull
        private final URI projectionDataUri;

        @NotNull
        private final NonEmptyString type;

        private final boolean isDefault;

        @JsonCreator
        public Slate(
                @JsonProperty("slate_path") @NotNull URI projectionDataUri,
                @JsonProperty("type") @JsonDeserialize(using = NonEmptyStringDeserializer.class) @NotNull NonEmptyString type,
                @JsonProperty("default") final boolean isDefault
        ) {
            this.projectionDataUri = projectionDataUri;
            this.type = type;
            this.isDefault = isDefault;
        }

        @NotNull
        public URI getProjectionDataUri() {
            return projectionDataUri;
        }

        @NotNull
        public NonEmptyString getType() {
            return type;
        }

        public boolean isDefault() {
            return isDefault;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slate slate = (Slate) o;
            return isDefault == slate.isDefault && projectionDataUri.equals(slate.projectionDataUri) && type.equals(slate.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(projectionDataUri, type, isDefault);
        }
    }

    @NotNull
    @JsonDeserialize(keyUsing = NonEmptyStringKeyDeserializer.class)
    private final LinkedHashMap<NonEmptyString, LinkedHashMap<PositiveInteger, Slate>> slatesBySiteName;

    @JsonCreator
    public Slates(
            @JsonDeserialize(keyUsing = NonEmptyStringKeyDeserializer.class) @NotNull final LinkedHashMap<NonEmptyString, LinkedHashMap<PositiveInteger, Slate>> slatesBySiteName
    ) {
        this.slatesBySiteName = slatesBySiteName;
    }

    @NotNull
    public LinkedHashMap<NonEmptyString, LinkedHashMap<PositiveInteger, Slate>> getSlatesBySiteName() {
        return slatesBySiteName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slates slates = (Slates) o;
        return slatesBySiteName.equals(slates.slatesBySiteName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slatesBySiteName);
    }
}

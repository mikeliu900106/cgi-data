package org.example.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelInfoEntity extends BasicEntity {
        @JsonProperty("source_id")
        private String sourceId;
        @JsonProperty("source_area_id")
        private String sourceAreaId;
        @JsonProperty("is_used")
        private boolean isUsed;
        @JsonProperty("p_type_2")
        private String pType2;

}

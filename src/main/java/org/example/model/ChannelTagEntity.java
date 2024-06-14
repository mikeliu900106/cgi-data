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
public class ChannelTagEntity extends BasicEntity{
    @JsonProperty("s_area_id")
    private String sAreaId;
    @JsonProperty("tag_id")
    private int TagId;
}

package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelTagEntity extends BasicEntity{
    @SerializedName("s_area_id")
    private String sAreaId;
    @SerializedName("tag_id")
    private int TagId;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return ChannelTagEntity.class;
    }
}

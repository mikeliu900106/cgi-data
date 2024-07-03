package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.annotation.DataBaseName;
import org.example.annotation.DatabaseColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DataBaseName(databaseName = "channel_tag_mapping")
public class ChannelTagEntity extends BasicEntity{
    @DatabaseColumn(columnName = "s_area_id")
    @SerializedName("s_area_id")
    private String sAreaId;
    @DatabaseColumn(columnName = "tag_id")
    @SerializedName("tag_id")
    private int TagId;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return ChannelTagEntity.class;
    }
}

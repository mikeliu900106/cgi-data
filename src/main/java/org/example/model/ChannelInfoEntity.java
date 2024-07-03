package org.example.model;


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
@DataBaseName(databaseName = "channel_info")
public class ChannelInfoEntity extends BasicEntity {

    @DatabaseColumn(columnName = "source_id")
    @SerializedName("source_id")
    private String sourceId;

    @DatabaseColumn(columnName = "source_area_id")
    @SerializedName("source_area_id")
    private String sourceAreaId;

    @DatabaseColumn(columnName = "is_used")
    @SerializedName("is_used")
    private boolean isUsed;

    @DatabaseColumn(columnName = "p_type_2")
    @SerializedName("p_type_2")
    private String pType2;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return ChannelInfoEntity.class;
    }
}

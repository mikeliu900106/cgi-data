package org.example.model;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelInfoEntity extends BasicEntity {
    @SerializedName("source_id")
    private String sourceId;
    @SerializedName("source_area_id")
    private String sourceAreaId;
    @SerializedName("is_used")
    private boolean isUsed;
    @SerializedName("p_type_2")
    private String pType2;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return ChannelInfoEntity.class;
    }
}

package org.example.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelInfosEntity extends BasicEntity {
    @SerializedName("channel_info")
    private List<ChannelInfoEntity> channelInfoEntities;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return ChannelInfosEntity.class;
    }
}

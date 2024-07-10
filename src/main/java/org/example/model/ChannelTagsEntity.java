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
public class ChannelTagsEntity extends BasicEntity {
    @SerializedName("channel_tag_mapping")
    private List<ChannelTagEntity> channelTagEntities;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return ChannelTagsEntity.class;
    }
}

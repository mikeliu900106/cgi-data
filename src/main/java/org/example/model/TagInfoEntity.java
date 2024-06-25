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
public class TagInfoEntity extends BasicEntity{
    @SerializedName("tag_id")
    private int tagId;
    @SerializedName("tag_name")
    private String tagName;
    @SerializedName("type")
    private int type;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return TagInfoEntity.class;
    }
}

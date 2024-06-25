package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TagInfosEntity extends BasicEntity{
    @SerializedName("tag_info")
    List<TagInfoEntity> tagInfoEntities;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return TagInfosEntity.class;
    }
}

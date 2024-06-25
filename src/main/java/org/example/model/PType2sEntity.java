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
public class PType2sEntity extends BasicEntity{
    @SerializedName("p_type_2_info")
    private List<PType2Entity> pType2Entities;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return PType2sEntity.class;
    }
}

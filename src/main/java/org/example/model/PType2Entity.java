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
public class PType2Entity extends BasicEntity{
    @SerializedName("category")
    private String category;
    @SerializedName("name")
    private String name;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return PType2Entity.class;
    }

    @Override
    public String toString() {
        return "PType2Entity{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

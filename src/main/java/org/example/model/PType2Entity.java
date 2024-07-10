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
@DataBaseName(databaseName = "p_type_2")
public class PType2Entity extends BasicEntity{
    @DatabaseColumn(columnName = "category")
    @SerializedName("category")
    private String category;
    @DatabaseColumn(columnName = "name")
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

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
@DataBaseName(databaseName = "tag_info")
public class TagInfoEntity extends BasicEntity{
    @DatabaseColumn(columnName = "tag_id")
    @SerializedName("tag_id")
    private int tagId;
    @DatabaseColumn(columnName = "tag_name")
    @SerializedName("tag_name")
    private String tagName;
    @DatabaseColumn(columnName = "type")
    @SerializedName("type")
    private int type;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return TagInfoEntity.class;
    }
}

package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagInfoEntity extends BasicEntity{
    @JsonProperty("tag_id")
    private int tagId;
    @JsonProperty("tag_name")
    private String tagName;
    @JsonProperty("type")
    private int type;
}

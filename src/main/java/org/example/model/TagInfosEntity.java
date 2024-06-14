package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("tag_info")
    List<TagInfoEntity> tagInfoEntities;
}

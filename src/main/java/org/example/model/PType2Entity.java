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
public class PType2Entity extends BasicEntity{
    @JsonProperty("category")
    private String category;
    @JsonProperty("name")
    private String name;
}

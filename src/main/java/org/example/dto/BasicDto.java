package org.example.dto;

import lombok.Builder;
import lombok.Data;
import org.example.dao.BasicDao;
import org.example.model.BasicEntity;
@Data
@Builder
public class BasicDto {
    BasicDao basicDao;
    BasicEntity basicEntity;
}

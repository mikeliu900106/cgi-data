package org.example.model;

import java.util.List;

public class BasicsEntity extends BasicEntity {

    private List<? extends BasicEntity> basicEntities;

    @Override
    public Class<? extends BasicEntity> getEntityClass() {
        return BasicsEntity.class;
    }

}

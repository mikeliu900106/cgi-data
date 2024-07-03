package org.example.dao;

import org.example.model.BasicEntity;

import java.sql.SQLException;
import java.util.List;

public interface BasicDao {
    void insertAll(List<? extends BasicEntity> list) throws SQLException, ClassNotFoundException;
    List<? extends BasicEntity> findAll() throws SQLException, ClassNotFoundException;
    List<String> getColumnName(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException;
    String getTableName(Class<? extends BasicEntity> entityClass);
}


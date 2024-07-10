package org.example.dao;

import org.example.model.BasicEntity;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

public interface BasicDao {
    /**
     * 描述插入資料庫的行為
     *
     * @param list<? extends BasicEntity> 資料庫交互檔案通常傳送資料庫映射檔案
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void insertAll(List<? extends BasicEntity> list) throws SQLException, ClassNotFoundException, UnsupportedEncodingException;
    /**
     * 從資料庫查詢的行為
     *
     * @return List<? extends BasicEntity>
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    List<? extends BasicEntity> findAll() throws SQLException, ClassNotFoundException;

    /**
     *  獲取資料庫欄位名稱
     *
     * @param entityClass
     * @return List<String>
     * @throws ClassNotFoundException
     */
    List<String> getColumnName(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException;

    /**
     * 獲取資料庫名稱
     *
     * @param entityClass
     * @return String
     */
    String getTableName(Class<? extends BasicEntity> entityClass);
}


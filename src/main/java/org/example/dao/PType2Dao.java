package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.BasicEntity;
import org.example.model.PType2Entity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class PType2Dao extends AbstractBasicDao {

    private static final Logger logger = Logger.getLogger(ChannelInfo2Dao.class);

    private Connection connection;

    public PType2Dao(Connection connection) {
        super(connection);
    }


    @Override
    protected String getInsertSQL() throws ClassNotFoundException, UnsupportedEncodingException {
        return getInsertAllSQL(PType2Entity.class);
    }

    @Override
    protected String getSelectSQL() throws ClassNotFoundException {
        return getSelectAllSQL(PType2Entity.class);
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException, ClassNotFoundException, IllegalAccessException {
        List<Class<?>> columnType = getFiledType(PType2Entity.class);
        Field[] fields = getClassField(PType2Entity.class);
        logger.debug("columnType size:" + columnType);
        loopColumnTypeSetValue(statement, entity, columnType, fields);
    }

    @Override
    protected BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        List<String> columnNames = getColumnName(PType2Entity.class);
        Field[] fields = getClassField(PType2Entity.class);
        PType2Entity entity = new PType2Entity();
        entity = (PType2Entity) loopColumnGetValue(columnNames, fields, resultSet, entity);
//        logger.debug("開始把資料配置");
        return entity;
    }
}

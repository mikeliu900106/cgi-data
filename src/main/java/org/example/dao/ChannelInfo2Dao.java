package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.BasicEntity;
import org.example.model.ChannelInfoEntity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ChannelInfo2Dao extends AbstractBasicDao {
    private static final Logger logger = Logger.getLogger(ChannelInfo2Dao.class);
    public ChannelInfo2Dao(Connection connection) {
        super(connection);
    }
    @Override
    protected String getInsertSQL() throws ClassNotFoundException, UnsupportedEncodingException {
        return getInsertAllSQL(ChannelInfoEntity.class);
    }

    @Override
    protected String getSelectSQL() throws ClassNotFoundException {
        return getSelectAllSQL(ChannelInfoEntity.class);
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException, ClassNotFoundException, IllegalAccessException {
        List<Class<?>> columnType = getFiledType(ChannelInfoEntity.class);
        Field[] fields = getClassField(ChannelInfoEntity.class);
        logger.debug("columnType size:" + columnType);
        loopColumnTypeSetValue(statement,entity,columnType, fields ) ;
    }

    @Override
    protected BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        List<String> columnNames = getColumnName(ChannelInfoEntity.class);
        Field[] fields = getClassField(ChannelInfoEntity.class);
        ChannelInfoEntity entity = new ChannelInfoEntity();
        entity = (ChannelInfoEntity) loopColumnGetValue(columnNames,fields, resultSet, entity);
//        logger.debug("開始把資料配置");
        return entity;
    }


}

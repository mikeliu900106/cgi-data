package org.example.dao;

import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.example.model.BasicEntity;
import org.example.model.ChannelTagEntity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
public class ChannelTagDao extends AbstractBasicDao {

    private static final Logger logger = Logger.getLogger(ChannelTagDao.class);

    public ChannelTagDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getInsertSQL() throws ClassNotFoundException, UnsupportedEncodingException {
        return getInsertAllSQL(ChannelTagEntity.class);
    }

    @Override
    protected String getSelectSQL() throws ClassNotFoundException {
        return getSelectAllSQL(ChannelTagEntity.class);
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException, ClassNotFoundException, IllegalAccessException {
        List<Class<?>> columnType = getFiledType(ChannelTagEntity.class);
        Field[] fields = getClassField(ChannelTagEntity.class);
        logger.debug("columnType size:" + columnType);
        loopColumnTypeSetValue(statement, entity, columnType, fields);
    }

    @Override
    protected BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        List<String> columnNames = getColumnName(ChannelTagEntity.class);
        Field[] fields = getClassField(ChannelTagEntity.class);
        ChannelTagEntity entity = new ChannelTagEntity();
        entity = (ChannelTagEntity) loopColumnGetValue(columnNames, fields, resultSet, entity);
//        logger.debug("開始把資料配置");
        return entity;
    }


}
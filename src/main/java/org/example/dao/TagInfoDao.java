package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.example.model.BasicEntity;
import org.example.model.ChannelInfoEntity;
import org.example.model.TagInfoEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class TagInfoDao extends AbstractBasicDao {

    private static final Logger logger = Logger.getLogger(TagInfoDao.class);

    private Connection connection;

    public TagInfoDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getInsertSQL() throws ClassNotFoundException {
        return getInsertAllSQL(TagInfoEntity.class);
    }

    @Override
    protected String getSelectSQL() throws ClassNotFoundException {
        return getSelectAllSQL(TagInfoEntity.class);
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException, ClassNotFoundException, IllegalAccessException {
        List<Class<?>> columnType = getColumnType(TagInfoEntity.class);
        Field[] fields = getClassField(TagInfoEntity.class);
        logger.debug("columnType size:" + columnType);
        loopColumnTypeSetValue(statement,entity,columnType, fields ) ;
    }

    @Override
    protected BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        List<String> columnNames = getColumnName(TagInfoEntity.class);
        Field[] fields = getClassField(TagInfoEntity.class);
        TagInfoEntity entity = new TagInfoEntity();
        entity = (TagInfoEntity) BasicEntity(columnNames,fields, resultSet, entity);
        logger.debug("開始把資料配置");
        return entity;
    }
}
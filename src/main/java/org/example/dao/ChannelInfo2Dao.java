package org.example.dao;

import org.apache.log4j.Logger;
import org.example.model.BasicEntity;
import org.example.model.ChannelInfoEntity;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ChannelInfo2Dao extends AbstractBasicDao {
    private static final Logger logger = Logger.getLogger(ChannelInfo2Dao.class);
    private ChannelInfoEntity channelInfoEntity;
    public ChannelInfo2Dao(Connection connection) {
        super(connection);
    }
    @Override
    protected String getInsertSQL() throws ClassNotFoundException {
        return getInsertAllSQL(ChannelInfoEntity.class);
    }

    @Override
    protected String getSelectSQL() throws ClassNotFoundException {
        return getSelectAllSQL(ChannelInfoEntity.class);
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException, ClassNotFoundException, IllegalAccessException {
        List<Class<?>> columnType = getColumnType(ChannelInfoEntity.class);
        Field[] fields = getClassField(ChannelInfoEntity.class);
        for (int i = 0; i < columnType.size(); i++) {
            int z = i + 1;
            Object value = fields[i].get(entity);
            if (columnType.get(i).equals(String.class)) {
                statement.setString(z, (String) value);
            }
            if (columnType.get(i).equals(Integer.class)) {
                statement.setInt(z, (Integer) value);
            }
            if (columnType.get(i).equals(Boolean.class)) {
                statement.setBoolean(z, (Boolean) value);
            }
            if (columnType.get(i).equals(Short.class)) {
                statement.setShort(z, (Short) value);
            }
        }


    }

    @Override
    protected BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        List<String> columnNames = getColumnName(this.channelInfoEntity.getClass());
        Field[] fields = getClassField(this.channelInfoEntity.getClass());
        ChannelInfoEntity entity = new ChannelInfoEntity();
        logger.debug("開始把資料配置");
        for (int i = 0; i < columnNames.size(); i++) {
            // 變數名稱
            String fieldName = fields[i].getName();
            // 判斷自斷是否在類別裡面
            try {
                // 獲取自斷類型
                Class<?> fieldType = fields[i].getType();
                fields[i].setAccessible(true);
                logger.debug("開始把資料配置" + fieldName);

                if (fieldType == int.class || fieldType == Integer.class) {
                    fields[i].set(entity, resultSet.getInt(columnNames.get(i)));
                } else if (fieldType == long.class || fieldType == Long.class) {
                    fields[i].set(entity, resultSet.getLong(columnNames.get(i)));
                } else if (fieldType == float.class || fieldType == Float.class) {
                    fields[i].set(entity, resultSet.getFloat(columnNames.get(i)));
                } else if (fieldType == double.class || fieldType == Double.class) {
                    fields[i].set(entity, resultSet.getDouble(columnNames.get(i)));
                } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                    fields[i].set(entity, resultSet.getBoolean(columnNames.get(i)));
                } else if (fieldType == String.class) {
                    fields[i].set(entity, resultSet.getString(columnNames.get(i)));
                } else if (fieldType == Date.class) {
                    fields[i].set(entity, resultSet.getDate(columnNames.get(i)));
                }
            } catch (SQLException | IllegalAccessException e) {
                throw new RuntimeException("Failed to set field value", e);
            }

        }
        return entity;
    }


}

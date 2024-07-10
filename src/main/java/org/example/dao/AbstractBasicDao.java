package org.example.dao;

import org.apache.log4j.Logger;
import org.example.annotation.DataBaseName;
import org.example.annotation.DatabaseColumn;
import org.example.model.BasicEntity;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractBasicDao implements BasicDao {

    private static final Logger logger = Logger.getLogger(AbstractBasicDao.class);
    protected Connection connection;

    public AbstractBasicDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * 我把寫inseert sql語法的實際行為下放給子類
     *
     * @return String
     * @throws ClassNotFoundException
     */
    protected abstract String getInsertSQL() throws ClassNotFoundException, UnsupportedEncodingException;

    /**
     * 我把寫 select sql語法的實際行為下放給子類
     *
     * @return String
     * @throws ClassNotFoundException
     */
    protected abstract String getSelectSQL() throws ClassNotFoundException;

    /**
     * 把資料庫配置參數的行為下放給子類
     *
     * @param statement
     * @param entity    資料庫映射檔案
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     */
    protected abstract void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException, ClassNotFoundException, IllegalAccessException;

    /**
     * 把接受查詢完的資料行為下放給子類
     *
     * @param resultSet
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    protected abstract BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException, ClassNotFoundException;

    /**
     * 輸入資料庫
     *
     * @param entities
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public void insertAll(List<? extends BasicEntity> entities) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        String sql = getInsertSQL();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BasicEntity entity : entities) {
                setInsertParameters(statement, entity);
                statement.addBatch();
            }
            statement.executeBatch();
//            logger.debug("完成");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查詢資料庫
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<? extends BasicEntity> findAll() throws SQLException, ClassNotFoundException {
        List<BasicEntity> entities = new ArrayList<>();
        String sql = getSelectSQL();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                BasicEntity entity = createEntityFromResultSet(resultSet);
                entities.add(entity);
            }
        }

        return entities;
    }

    /**
     * 獲取資料庫欄位
     *
     * @param entityClass
     * @return
     * @throws ClassNotFoundException
     */
    public List<String> getColumnName(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException {
        List<String> columns = new ArrayList<String>();
        Class<?> clazz = entityClass;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof DatabaseColumn) {
                    DatabaseColumn databaseColumn = (DatabaseColumn) annotation;
                    columns.add(databaseColumn.columnName());
                }
            }
        }
        return columns;
    }

    /**
     * 獲取資料庫table名稱
     *
     * @param entityClass
     * @return
     */
    public String getTableName(Class<? extends BasicEntity> entityClass) {
        Class<?> clazz = entityClass;
        Annotation[] annotations = clazz.getAnnotations();
        String tableName = null;
        for (Annotation annotation : annotations) {
            // 判断是否是 DataBaseName 注解
            if (annotation instanceof DataBaseName) {
                DataBaseName dataBaseName = (DataBaseName) annotation;
                tableName = dataBaseName.databaseName();

            }
        }
//        logger.debug("資料庫名稱:" + tableName);
        return tableName;
    }

    /**
     * 獲取變數型別
     *
     * @param entityClass
     * @return
     * @throws ClassNotFoundException
     */
    public List<Class<?>> getFiledType(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException {
        List<Class<?>> columns = new ArrayList();
        Class<?> clazz = entityClass;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            columns.add(field.getType());
        }
//        logger.debug("變數型別:" + columns);
        return columns;
    }

    /**
     * 獲取所有變數
     *
     * @param entityClass
     * @return
     * @throws ClassNotFoundException
     */
    public Field[] getClassField(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException {
        return entityClass.getDeclaredFields();
    }

    protected String getInsertAllSQL(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException, UnsupportedEncodingException {
        return getInsertAllSQL(getColumnName(entityClass), getTableName(entityClass));
    }

    /**
     * 自動撰寫出插入所有資料的sql
     *
     * @param column
     * @param dataBaseName
     * @return
     */
    protected String getInsertAllSQL(List<String> column, String dataBaseName) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder("Insert into ")
                .append(dataBaseName)
                .append(" (");
        for (int i = 0; i < column.size(); i++) {
            stringBuilder.append(column.get(i));
            if (i != column.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(" ) VAlUES (");
        for (int i = 0; i < column.size(); i++) {
            stringBuilder.append("?");
            if (i != column.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
//        logger.debug("INSERT 語法:" + stringBuilder);
        String sql = stringBuilder.toString();
        return new String(sql.getBytes("UTF-8"), "UTF-8");

    }

    protected String getSelectAllSQL(Class<? extends BasicEntity> entityClass) throws ClassNotFoundException {
        return getSelectAllSQL(getColumnName(entityClass), getTableName(entityClass));
    }

    /**
     * 自動撰寫查尋所有資料的sql
     *
     * @param column
     * @param dataBaseName
     * @return
     */
    protected String getSelectAllSQL(List<String> column, String dataBaseName) {
//        logger.debug("資料庫欄位:" + column);
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM ")
                .append(dataBaseName);
//        logger.debug("slelect 語法:" + stringBuilder);
        return stringBuilder.toString();
    }

    /**
     * 自動把資料配置進檔案
     *
     * @param statement
     * @param entity
     * @param columnType
     * @param fields
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    protected void loopColumnTypeSetValue(PreparedStatement statement, BasicEntity entity, List<Class<?>> columnType, Field[] fields) throws SQLException, IllegalAccessException, ClassNotFoundException {
        for (int i = 0; i < columnType.size(); i++) {
            int parameterIndex = i + 1;
            fields[i].setAccessible(true);
            Object value = fields[i].get(entity);
            if (columnType.get(i).equals(String.class)) {
//                logger.debug(value);
                statement.setString(parameterIndex, (String) value);
            } else if (columnType.get(i).equals(Integer.class)) {
                statement.setInt(parameterIndex, (Integer) value);
            } else if (columnType.get(i).equals(boolean.class)) {
                statement.setBoolean(parameterIndex, (Boolean) value);
            } else if (columnType.get(i).equals(Short.class)) {
                statement.setShort(parameterIndex, (Short) value);
            } else if (columnType.get(i).equals(int.class)) {
                statement.setInt(parameterIndex, (int) value);
            } else {
                throw new ClassNotFoundException("沒有這涮");
            }

        }
//        logger.debug("statement" + statement);
    }

    /**
     * 自動取出資料
     *
     * @param columnNames
     * @param fields
     * @param resultSet
     * @param entity
     * @return
     */
    protected BasicEntity loopColumnGetValue(List<String> columnNames, Field[] fields, ResultSet resultSet, BasicEntity entity) {
        for (int i = 0; i < columnNames.size(); i++) {
            // 變數名稱
            String fieldName = fields[i].getName();
            // 判斷自斷是否在類別裡面
            try {
                // 獲取自斷類型
                Class<?> fieldType = fields[i].getType();
                fields[i].setAccessible(true);
//                logger.debug("開始把資料配置" + fieldName);

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

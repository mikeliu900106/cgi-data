package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.model.BasicEntity;
import org.example.model.ChannelTagEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChannelTagDao extends AbstractBasicDao {


    public ChannelTagDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getInsertSQL() {
        return "";
    }

    @Override
    protected String getSelectSQL() {
        return "";
    }

    @Override
    protected void setInsertParameters(PreparedStatement statement, BasicEntity entity) throws SQLException {

    }

    @Override
    protected BasicEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public Class<? extends BasicDao> getDaoClass() {
        return null;
    }
}
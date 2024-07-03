package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.BasicEntity;
import org.example.model.PType2Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PType2Dao implements BasicDao {

    private Connection connection;

    @Override
    public void insertAll(List<? extends BasicEntity> entities) throws SQLException {
        String sql = "INSERT INTO p_type_2 (category, name) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BasicEntity entity : entities) {
                if (entity instanceof PType2Entity) {
                    PType2Entity pType2 = (PType2Entity) entity;
                    statement.setString(1, pType2.getCategory());
                    statement.setString(2, pType2.getName());
                    statement.addBatch();
                }else {
                    throw new IllegalArgumentException("Unsupported entity type: " + entity.getClass());
                }
            }
            statement.executeBatch();
            System.out.println("Batch insertion completed.");
        }
    }

    @Override
    public List<PType2Entity> findAll() throws SQLException {
        List<PType2Entity> pType2Entities = new ArrayList<>();

        String sql = "SELECT category, name FROM p_type_2";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                String name = resultSet.getString("name");

                PType2Entity pType2Entity = new PType2Entity(category, name);
                pType2Entities.add(pType2Entity);
            }
        }

        return pType2Entities;
    }

    @Override
    public Class<? extends BasicDao> getDaoClass() {
        return PType2Dao.class;
    }

}

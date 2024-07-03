package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.BasicEntity;
import org.example.model.TagInfoEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TagInfoDao implements BasicDao {

    private Connection connection;

    public void insertAll(List<? extends BasicEntity> entities) throws SQLException {
        String query = "INSERT INTO tag_info (tag_name, type) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (BasicEntity entity : entities) {
                if (entity instanceof TagInfoEntity) {
                    TagInfoEntity tagInfo = (TagInfoEntity) entity;
                    stmt.setString(1, tagInfo.getTagName());
                    stmt.setInt(2, tagInfo.getType());
                    stmt.addBatch();
                }else {
                    throw new IllegalArgumentException("Unsupported entity type: " + entity.getClass());
                }

            }
            stmt.executeBatch();
        }
    }


    public List<TagInfoEntity> findAll() throws SQLException {
        List<TagInfoEntity> tagInfos = new ArrayList<>();
        String query = "SELECT tag_id, tag_name, type FROM tag_info";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int tagId = rs.getInt("tag_id");
                String tagName = rs.getString("tag_name");
                int type = rs.getInt("type");

                TagInfoEntity tagInfo = new TagInfoEntity(tagId, tagName, type);
                tagInfos.add(tagInfo);
            }
        }
        return tagInfos;
    }

    @Override
    public Class<? extends BasicDao> getDaoClass() {
        return TagInfoDao.class;
    }

}
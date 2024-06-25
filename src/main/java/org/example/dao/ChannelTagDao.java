package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.BasicEntity;
import org.example.model.ChannelTagEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChannelTagDao implements BasicDao {

    private Connection connection;

    public void insertAll(List<? extends BasicEntity> entities) throws SQLException {
        String sql = "INSERT INTO channel_tag_mapping (s_area_id, tag_id) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BasicEntity entity : entities) {
                if (entity instanceof ChannelTagEntity) {
                    ChannelTagEntity channelTag = (ChannelTagEntity) entity;
                    statement.setString(1, channelTag.getSAreaId());
                    statement.setInt(2, channelTag.getTagId());
                    statement.addBatch();
                }
            }
            statement.executeBatch();
            System.out.println("完成");
        }
    }

    @Override
    public List<ChannelTagEntity> findAll() throws SQLException {
        List<ChannelTagEntity> channelTags = new ArrayList<>();

        String sql = "SELECT s_area_id, tag_id FROM channel_tag_mapping";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String sAreaId = resultSet.getString("s_area_id");
                int tagId = resultSet.getInt("tag_id");

                ChannelTagEntity channelTag = new ChannelTagEntity(sAreaId, tagId);
                channelTags.add(channelTag);
            }
        }

        return channelTags;
    }

    @Override
    public Class<? extends BasicDao> getDaoClass() {
        return ChannelTagDao.class;
    }


}
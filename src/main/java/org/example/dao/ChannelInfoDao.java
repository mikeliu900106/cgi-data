package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.model.BasicEntity;
import org.example.model.ChannelInfoEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class ChannelInfoDao implements BasicDao{
    private Connection connection;

    public void insertAll(List<? extends BasicEntity> entities) throws SQLException {
        String sql = "INSERT INTO channel_info (source_id,source_area_id, is_used,  p_type_2) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BasicEntity entity : entities) {
                if (entity instanceof ChannelInfoEntity) {
                    ChannelInfoEntity channelInfo = (ChannelInfoEntity) entity;
                    statement.setString(1, channelInfo.getSourceId());
                    statement.setString(2, channelInfo.getSourceAreaId());
                    statement.setBoolean(3, channelInfo.isUsed());
                    statement.setString(4, channelInfo.getPType2());
                    statement.addBatch();
                } else {
                    throw new IllegalArgumentException("Unsupported entity type: " + entity.getClass());
                }
            }

            statement.executeBatch();
            System.out.println("数据插入完成");
        }
    }

    // 查询所有 ChannelInfoEntity 对象
    public List<ChannelInfoEntity> findAll() throws SQLException {
        List<ChannelInfoEntity> entities = new ArrayList<>();
        String sql = "SELECT * FROM channel_info";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ChannelInfoEntity entity = new ChannelInfoEntity();
                entity.setSourceAreaId(resultSet.getString("source_area_id"));
                entity.setUsed(resultSet.getBoolean("is_used"));
                entity.setSourceId(resultSet.getString("source_id"));
                entity.setPType2(resultSet.getString("p_type_2"));

                entities.add(entity);
            }
        }

        return entities;
    }
}

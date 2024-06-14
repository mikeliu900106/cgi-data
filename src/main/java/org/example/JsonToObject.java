package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.ChannelInfoDao;
import org.example.dao.ChannelTagDao;
import org.example.dao.PType2Dao;
import org.example.dao.TagInfoDao;
import org.example.model.*;
import org.example.util.DatabaseUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.BaseStream;

public class JsonToObject {
    private static final String EXPORT_PATH = "C:\\test\\export";
    private static final String IMPORT_PATH = "C:\\test\\import";
    public static void main(String[] args) {
        try {
            System.out.println(dataTransfer(args[0] ,args[1]));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String dataTransfer(String actionName, String fileName) throws Exception {
        if(HandleAction(actionName) == 1){
            File file = findJson(fileName);
            inputData(fileName,file);
            return "上傳成功";
        }else {
            String jsonData = exportData(fileName);
            writeToFile(jsonData,fileName);
            return "下載成功";
        }
    }

    private static void writeToFile(String jsonData,String fileName) throws IOException {
        File directory = new File(EXPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        System.out.println("檔案:" + jsonData);
        StringBuilder filePath = new StringBuilder(EXPORT_PATH)
                .append("\\")
                .append(fileName);
        System.out.println(filePath);
        FileWriter fileWriter = new FileWriter(filePath.toString());
        fileWriter.write(jsonData);
        fileWriter.flush();
        System.out.println("Successfully wrote to the file.");
    }



    private static File findJson(String fileName) {
        File directory = new File(IMPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        StringBuilder path = new StringBuilder(IMPORT_PATH);
        path.append("\\");
        path.append(fileName);
        return new File(path.toString());
    }

    //要改盡量0用enum
    private static int HandleAction(String actionName) throws Exception {
        if(actionName.equals("--import")){
            return 1;
        } else if (actionName.equals("--export")) {
            return 2;
        }else{
            throw new Exception("沒有這個行為");
        }
    }

    private static void inputData(String fileName , File file) throws SQLException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        if(fileName.contains("channel_tag_mapping")){
            ChannelTagsEntity channelTagsEntity =mapper.readValue(file,ChannelTagsEntity.class);
            ChannelTagDao channelTagDao = new ChannelTagDao(DatabaseUtil.getConnection());
            channelTagDao.insertAll(channelTagsEntity.getChannelTagEntities());
        } else if (fileName.contains("channel_info")) {
            ChannelInfosEntity channelInfosEntity = mapper.readValue(file,ChannelInfosEntity.class);
            ChannelInfoDao channelInfoDao = new ChannelInfoDao(DatabaseUtil.getConnection());
            channelInfoDao.insertAll(channelInfosEntity.getChannelInfoEntities());
        } else if (fileName.contains("p_type_2_list")) {
            PType2sEntity pType2sEntity = mapper.readValue(file,PType2sEntity.class);
            PType2Dao pType2Dao = new PType2Dao(DatabaseUtil.getConnection());
            pType2Dao.insertAll(pType2sEntity.getPType2Entities());
        } else if (fileName.contains("tag_info")) {
            TagInfosEntity tagInfosEntity = mapper.readValue(file,TagInfosEntity.class);
            TagInfoDao tagInfoDao = new TagInfoDao(DatabaseUtil.getConnection());
            tagInfoDao.insertAll(tagInfosEntity.getTagInfoEntities());
        }
        else {
            throw new IOException("檔案名不對");
        }
    }
    private static  String exportData(String fileName) throws SQLException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        if(fileName.contains("channel_tag_mapping")){
            ChannelTagDao channelTagDao = new ChannelTagDao(DatabaseUtil.getConnection());
            ChannelTagsEntity channelTagsEntity = ChannelTagsEntity
                    .builder()
                    .channelTagEntities(channelTagDao.findAll())
                    .build();
            String jsonString = mapper.writeValueAsString(channelTagsEntity);
            System.out.println(jsonString);
            return jsonString;
        } else if (fileName.contains("channel_info")) {
            ChannelInfoDao channelInfoDao = new ChannelInfoDao(DatabaseUtil.getConnection());
            ChannelInfosEntity channelInfosEntity = ChannelInfosEntity
                    .builder()
                    .channelInfoEntities(channelInfoDao.findAll())
                    .build();
            String jsonString = mapper.writeValueAsString(channelInfosEntity);
            System.out.println(jsonString);
            return  jsonString;
        } else if (fileName.contains("p_type_2_list")) {
            PType2Dao pType2Dao = new PType2Dao(DatabaseUtil.getConnection());
            PType2sEntity pType2sEntity = PType2sEntity
                    .builder()
                    .pType2Entities(pType2Dao.findAll())
                    .build();
            String jsonString = mapper.writeValueAsString(pType2sEntity);
            System.out.println(jsonString);
            return  jsonString;
        } else if (fileName.contains("tag_info")) {
            TagInfoDao tagInfoDao = new TagInfoDao(DatabaseUtil.getConnection());
            TagInfosEntity tagInfosEntity = TagInfosEntity
                    .builder()
                    .tagInfoEntities(tagInfoDao.findAll())
                    .build();
            String jsonString = mapper.writeValueAsString(tagInfosEntity);
            System.out.println(jsonString);
            return  jsonString;
        }
        else {
            throw new IOException("檔案名不對");
        }
    }


//    private static  BasicEntity jsonToObject(BasicEntity basicEntity,File file){
//        ObjectMapper mapper = new ObjectMapper();
//        BasicEntity Entity;
//        Entity = mapper.readValue(file,basicEntity);
//    }


}

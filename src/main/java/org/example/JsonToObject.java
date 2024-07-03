package org.example;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.example.dao.*;
import org.example.dto.BasicDto;
import org.example.model.*;
import org.example.util.DatabaseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import com.google.gson.reflect.TypeToken;

public class JsonToObject {
    private static final Logger logger = Logger.getLogger(JsonToObject.class);
    private static String EXPORT_PATH;
    private static String IMPORT_PATH;
    private static final Properties props = new Properties();
    private static final String configPath = "src\\main\\resources\\config.properties";

    public static void main(String[] args) {
        try {
            handleFilePathConfiguration(props);
            dataTransfer( "--import", "channel_info.json");
//            System.out.println(dataTransfer(args[0], args[1]));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String dataTransfer(String actionName, String fileName) throws Exception {
        if (handleAction(actionName) == 1) {
            File file = findJson(fileName);
            inputData(fileName, file);
            return "上傳成功";
        } else {
            String jsonData = exportData(fileName);
            writeToFile(jsonData, fileName);
            return "下載成功";
        }
    }

    private static void writeToFile(String jsonData, String fileName) throws IOException {
        File directory = new File(EXPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        logger.debug("資料夾建立成功");
        StringBuilder filePath = new StringBuilder(EXPORT_PATH)
                .append("\\")
                .append(fileName);

        FileWriter fileWriter = new FileWriter(filePath.toString());
        fileWriter.write(jsonData);
        fileWriter.flush();
        fileWriter.close();
        logger.info("資料已寫入檔案.");
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
    private static int handleAction(String actionName) throws Exception {
        if (actionName.equals("--import")) {
            return 1;
        } else if (actionName.equals("--export")) {
            return 2;
        } else {
            throw new Exception("沒有這個行為");
        }
    }

    //TypeToken<List<? extends BasicEntity>>()不能用藥重點處理
    private static void inputData(String fileName, File file) throws SQLException, IOException, ClassNotFoundException {
        Gson gson = new Gson();
        BasicDto basicDto = handleFileName(fileName);
        if (basicDto == null) {
            throw new IOException("沒有該檔案名稱,後面記得變成");
        }
        BasicDao basicDao = basicDto.getBasicDao();
        Type type = new TypeToken<List<? extends BasicEntity>>() {}.getType();
        String json = new String(Files.readAllBytes(file.toPath()));
        List<? extends BasicEntity> entities = gson.fromJson(json, type);
        logger.debug("讀取: " + entities);
        basicDao.insertAll(entities);
//        BasicEntity basicsEntity = basicDto.getBasicEntity();
//        basicsEntity.getEntityClass() c = basicsEntity
//        BasicDao basicDao = basicDto.getBasicDao();
//        Type type = new TypeToken<List<? extends BasicEntity>>() {}.getType();
//        List<? extends BasicEntity> insertBasicEntity = gson.fromJson(new FileReader(file),type);
//        System.out.println(insertBasicEntity);
//        basicDao.insertAll(insertBasicEntity);
//        if (fileName.contains("channel_tag_mapping")) {
//
//            ChannelTagsEntity channelTagsEntity = mapper.readValue(file, ChannelTagsEntity.class);
//            ChannelTagDao channelTagDao = new ChannelTagDao(DatabaseUtil.getConnection());
//            channelTagDao.insertAll(channelTagsEntity.getChannelTagEntities());
//        } else if (fileName.contains("channel_info")) {
//            ChannelInfosEntity channelInfosEntity = mapper.readValue(file, ChannelInfosEntity.class);
//            ChannelInfoDao channelInfoDao = new ChannelInfoDao(DatabaseUtil.getConnection());
//            channelInfoDao.insertAll(channelInfosEntity.getChannelInfoEntities());
//        } else if (fileName.contains("p_type_2_list")) {
//            PType2sEntity pType2sEntity = mapper.readValue(file, PType2sEntity.class);
//            PType2Dao pType2Dao = new PType2Dao(DatabaseUtil.getConnection());
//            pType2Dao.insertAll(pType2sEntity.getPType2Entities());
//        } else if (fileName.contains("tag_info")) {
//            TagInfosEntity tagInfosEntity = mapper.readValue(file, TagInfosEntity.class);
//            TagInfoDao tagInfoDao = new TagInfoDao(DatabaseUtil.getConnection());
//            tagInfoDao.insertAll(tagInfosEntity.getTagInfoEntities());
//        } else {
//            throw new IOException("檔案名不對");
//        }
    }

    private static String exportData(String fileName) throws Exception {
        Gson gson = new Gson();
        BasicDto basicDto = handleFileName(fileName);
        if (basicDto == null) {
            logger.warn("沒有該檔案名稱,後面記得變成.txt檔案");
            throw new IOException("沒有該檔案名稱,後面記得變成.txt檔案");
        }
        BasicDao basicDao = basicDto.getBasicDao();
        String jsonString = gson.toJson(basicDao.findAll());
//        logger.info("寫入的資料" + jsonString);
        return jsonString;
    }

    private static BasicDto handleFileName(String fileName) throws SQLException {
        if (fileName.contains("channel_tag_mapping")) {
            return BasicDto.builder()
                    .basicEntity(new ChannelTagsEntity())
                    .basicDao(new ChannelTagDao(DatabaseUtil.getConnection()))
                    .build();
        }
        if (fileName.contains("channel_info")) {
            return BasicDto.builder()
                    .basicEntity(new ChannelInfoEntity())
                    .basicDao(new ChannelInfo2Dao(DatabaseUtil.getConnection()))
                    .build();
        }
        else if (fileName.contains("p_type_2_list")) {
            return BasicDto.builder()
                    .basicEntity(new PType2Entity())
                    .basicDao(new PType2Dao(DatabaseUtil.getConnection()))
                    .build();
        }
        else if (fileName.contains("tag_info")) {
            return BasicDto.builder()
                    .basicEntity(new TagInfoEntity())
                    .basicDao(new TagInfoDao(DatabaseUtil.getConnection()))
                    .build();
        }
        else {
            return null;
        }
    }

    private static void handleFilePathConfiguration(Properties props) throws IOException {
        FileInputStream fis = new FileInputStream(configPath);
        props.load(fis);
        EXPORT_PATH = props.getProperty("exportPath");
        IMPORT_PATH = props.getProperty("importPath");
    }

}

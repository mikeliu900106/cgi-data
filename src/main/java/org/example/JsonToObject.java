package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.example.dao.*;
import org.example.dto.BasicDto;
import org.example.model.*;
import org.example.util.DatabaseUtil;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

public class JsonToObject {
    private static final Logger logger = Logger.getLogger(JsonToObject.class);
    private static String EXPORT_PATH;
    private static String IMPORT_PATH;
    private static final Properties props = new Properties();


    public static void main(String[] args) {
        try {
//            PropertyConfigurator.configure("src/main/resources/log4j.properties");
            handleFilePathConfiguration(props);
//            dataTransfer("--import", "channel_info.json");
            logger.debug(dataTransfer(args[0], args[1]));
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
            return "upload over";
        } else {
            String jsonData = exportData(fileName);
            writeToFile(jsonData, fileName);
            return "export over";
        }
    }

    private static void writeToFile(String jsonData, String fileName) throws IOException {
        File directory = new File(EXPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
//        logger.debug("資料夾建立成功");
        StringBuilder filePath = new StringBuilder(EXPORT_PATH)
                .append("\\")
                .append(fileName);

        FileWriter fileWriter = new FileWriter(filePath.toString());
        fileWriter.write(jsonData);
        fileWriter.flush();
        fileWriter.close();
//        logger.info("資料已寫入檔案.");
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
        Type type = null;

        BasicDto basicDto = handleFileName(fileName);
        if (basicDto == null) {
            throw new IOException("沒有該檔案名稱,後面記得變成");
        }
        BasicDao basicDao = basicDto.getBasicDao();
        if (basicDto.getBasicEntity() instanceof ChannelInfoEntity) {
            type = new TypeToken<List<ChannelInfoEntity>>() {
            }.getType();
        } else if (basicDto.getBasicEntity() instanceof TagInfoEntity) {
            type = new TypeToken<List<TagInfoEntity>>() {
            }.getType();
        } else if (basicDto.getBasicEntity() instanceof ChannelTagEntity) {
            type = new TypeToken<List<ChannelTagEntity>>() {
            }.getType();
        } else if (basicDto.getBasicEntity() instanceof PType2Entity) {
            type = new TypeToken<List<PType2Entity>>() {
            }.getType();
        } else {
            throw new ClassNotFoundException("沒有這個行為");
        }
        String json = new String(Files.readAllBytes(file.toPath()));
        List<? extends BasicEntity> entities = gson.fromJson(json, type);
//        logger.debug("讀取: " + entities);
        basicDao.insertAll(entities);
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
                    .basicEntity(new ChannelTagEntity())
                    .basicDao(new ChannelTagDao(DatabaseUtil.getConnection()))
                    .build();
        }
        if (fileName.contains("channel_info")) {
            return BasicDto.builder()
                    .basicEntity(new ChannelInfoEntity())
                    .basicDao(new ChannelInfo2Dao(DatabaseUtil.getConnection()))
                    .build();
        } else if (fileName.contains("p_type_2_list")) {
            return BasicDto.builder()
                    .basicEntity(new PType2Entity())
                    .basicDao(new PType2Dao(DatabaseUtil.getConnection()))
                    .build();
        } else if (fileName.contains("tag_info")) {
            return BasicDto.builder()
                    .basicEntity(new TagInfoEntity())
                    .basicDao(new TagInfoDao(DatabaseUtil.getConnection()))
                    .build();
        } else {
            return null;
        }
    }

    private static void handleFilePathConfiguration(Properties props) throws IOException {
        InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("config.properties");

        props.load(inputStream);
        EXPORT_PATH = props.getProperty("exportPath");
        IMPORT_PATH = props.getProperty("importPath");
    }

}

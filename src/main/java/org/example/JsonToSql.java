package org.example;

import com.sun.tools.javac.Main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonToSql {
    public static void main(String[] args) {
        try {
//            ClassLoader classLoader = JsonToSql.class.getClassLoader();
//            Path path = Paths.get(classLoader.getResource("channel_info.json").toURI());
//            System.out.println(path.toString());
//            String content1 = Files.readString(Path.of("../resources/channel_info.json"));
//            System.out.println(content1);
            // 使用相对路径读取文件

            Path path = Paths.get("../resources/channel_info.json");
            String content = Files.readString(path);
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Files findByName(String name){
        String fileRoot = "C:\\";
        Files.find(Paths.get(fileRoot),)

    }
}

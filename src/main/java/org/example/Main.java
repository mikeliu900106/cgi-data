package org.example;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();


        Option importOption = new Option("i", "import", true, "Path to the JSON file to import");
        Option exportOption = new Option("i", "export", true, "Path to the JSON file to export");
//        importOption.setRequired(true);
        options.addOption(importOption);
        options.addOption(exportOption);
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            // 解析命令行参数
            CommandLine cmd = parser.parse(options, args);

            // 获取--import参数值
            String importFilePath = cmd.getOptionValue("import");
            System.out.println(importFilePath);

            // 打印文件路径，或者你可以在这里执行其他操作
            System.out.println("Importing file: " + importFilePath);

            // 这里你可以添加处理导入逻辑的代码
            // importFile(importFilePath);

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar cli.jar", options);

            System.exit(1);
        }
    }

    // 示例导入方法
    private static void importFile(String filePath) {
        // 实现导入逻辑
        System.out.println("Importing data from: " + filePath);
    }
}


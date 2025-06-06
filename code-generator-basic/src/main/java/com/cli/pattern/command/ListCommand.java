package com.cli.pattern.command;

import lombok.SneakyThrows;
import picocli.CommandLine;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * 输出当前支持的模版列表
 */
@CommandLine.Command(name = "list", version = "ListCommand 1.0", mixinStandardHelpOptions = true, description = "输出当前支持的模版列表")
public class ListCommand implements Runnable{
    @SneakyThrows
    public void run() {
        String resourcePath = "templates";
        URL resourceUrl = this.getClass().getClassLoader().getResource(resourcePath);
        if (resourceUrl == null) {
            System.err.println("资源目录未找到: " + resourcePath);
            return;
        }

        List<String> fileNames = new ArrayList<>();

        if (resourceUrl.getProtocol().equals("file")) {
            // 开发环境：直接访问文件系统
            Path dirPath = Paths.get(resourceUrl.toURI());
            try(Stream<Path> list = Files.list(dirPath)) {
                list.forEach(path -> fileNames.add(path.getFileName().toString()));
            }
        } else if (resourceUrl.getProtocol().equals("jar")) {
            // 生产环境：解析JAR包
            String jarPath = resourceUrl.getPath().split("!")[0].substring(5);
            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith(resourcePath + "/") && !entry.isDirectory()) {
                    fileNames.add(entry.getName());
                }
            }
            jarFile.close();
        }
        System.out.println("当前支持的模版列表:");
        // 打印所有文件名
        for (String fileName : fileNames) {
            System.out.println(fileName);
        }
    }
}

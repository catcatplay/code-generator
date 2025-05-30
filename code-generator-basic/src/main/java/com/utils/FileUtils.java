package com.utils;

import cn.hutool.core.io.FileUtil;

import java.io.IOException;
import java.nio.file.*;

public class FileUtils {
    /**
     * 拷贝文件
     *
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     */
    public static void copyFiles(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }

    /**
     * 拷贝文件
     *
     * @param source 输入路径
     * @param target 输出路径
     */
    public static void copyFiles(Path source, Path target) {
        try {
            copyDirectoryRecursively(source, target);
        } catch (Exception e) {
            System.err.println("文件复制失败");
            e.printStackTrace();
        }
    }

    private static void copyDirectoryRecursively(Path source, Path target) throws IOException {
        if (Files.isDirectory(source)) {
            copyDirectory(source, target);
        } else {
            copySingleFile(source, target);
        }
    }

    private static void copyDirectory(Path sourceDir, Path targetDir) throws IOException {
        // 创建目标目录（自动创建父目录）
        Path targetPath = targetDir.resolve(sourceDir.getFileName());
        Files.createDirectories(targetPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir)) {
            for (Path entry : stream) {
                copyDirectoryRecursively(entry, targetPath);
            }
        }
    }

    private static void copySingleFile(Path sourceFile, Path targetDir) throws IOException {
        // 目标文件路径计算
        Path targetFile = targetDir.resolve(sourceFile.getFileName());
        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
    }
}

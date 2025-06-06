package com.cli.pattern.command;

import cn.hutool.core.util.ReflectUtil;
import com.dto.Loop;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * 输出参数信息
 */
@CommandLine.Command(name = "config", version = "ConfigCommand 1.0", mixinStandardHelpOptions = true, description = "输出参数信息")
public class ConfigCommand implements Runnable{
    @Override
    public void run() {
        System.out.println("模版的参数信息为：");
        Field[] fields = ReflectUtil.getFields(Loop.class);
        for (Field field : fields) {
            System.out.println(field.getName() + ": " + field.getType().getSimpleName());
        }
    }
}

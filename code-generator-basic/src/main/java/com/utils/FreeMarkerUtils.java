package com.utils;

import com.dto.DataModel;
import com.dto.Loop;
import com.dto.Users;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FreeMarkerUtils {
    public static void generateCode(Object model) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 设置模板渲染出错时的处理方式。
        //HTML_DEBUG_HANDLER：开发时使用，输出详细错误堆栈和 HTML 注释。
        //RETHROW_HANDLER：生产环境使用，直接抛出异常，由应用层捕获处理。
        //IGNORE_HANDLER：忽略错误（不推荐，可能导致静默失败）。
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 创建模版与对应信息的哈希表
        Map<Class<?>, String[]> templateMap = new HashMap<>();
        templateMap.put(Loop.class, new String[]{"loop.java.ftl", "src/main/loop.java"});
        templateMap.put(DataModel.class, new String[]{"myWeb.html.ftl", "src/main/myWeb.html"});
        templateMap.put(Users.class, new String[]{"login_dates.py.ftl", "src/main/loginDates.py"});

        // 根据对应的模版信息生成对应的文件
        Class<?> modelClass = model.getClass();
        String[] templateInfo = Optional.ofNullable(templateMap.get(modelClass)).orElseThrow(() -> new RuntimeException("未找到模板"));
        Template template = configuration.getTemplate(templateInfo[0]);
        try(Writer writer = new FileWriter(templateInfo[1])) {
            template.process(model, writer);
        }
    }
}

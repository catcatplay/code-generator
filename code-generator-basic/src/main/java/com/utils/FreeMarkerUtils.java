package com.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import com.Main;
import com.cli.pattern.enums.CommandEnums;
import com.dto.DataModel;
import com.dto.Loop;
import com.dto.Users;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import picocli.CommandLine;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.*;

public class FreeMarkerUtils {
    /**
     * 根据模版生成文件
     */
    public static void process(Object model, String outputPath) throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模板文件所在的路径
//        Path path = Paths.get(System.getProperty("user.dir")).getParent();
//        if (!path.endsWith("code-generator-basic")) path = path.resolve("code-generator/code-generator-basic");
//        path = path.resolve("src/main/resources/templates");
//        configuration.setDirectoryForTemplateLoading(path.toFile());

        // 指定模版加载器
//        支持多种 TemplateLoader 实现，例如：
//        ClassTemplateLoader：从类路径加载模板（推荐用于生产环境）。
//        FileTemplateLoader：从文件系统加载模板。
//        URLTemplateLoader：从远程 URL 加载模板。
//        MultiTemplateLoader：组合多个加载器（例如同时支持本地和远程模板）。
        ClassTemplateLoader templateLoader = new ClassTemplateLoader(Main.class, "/templates");
        configuration.setTemplateLoader(templateLoader);

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        // 设置模板渲染出错时的处理方式。
        //HTML_DEBUG_HANDLER：开发时使用，输出详细错误堆栈和 HTML 注释。
        //RETHROW_HANDLER：生产环境使用，直接抛出异常，由应用层捕获处理。
        //IGNORE_HANDLER：忽略错误（不推荐，可能导致静默失败）。
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 创建模版与对应信息的哈希表
        Map<Class<?>, String> templateMap = new HashMap<>();
        // 当从test 运行时：工作目录可能是模块根目录，需去除"code-generator-basic/"
        templateMap.put(Loop.class, "loop.java.ftl");
        templateMap.put(DataModel.class, "myWeb.html.ftl");
        templateMap.put(Users.class, "login_dates.py.ftl");

        // 根据对应的模版信息生成对应的文件
        Class<?> modelClass = model.getClass();
        String templateInfo = Optional.ofNullable(templateMap.get(modelClass)).orElseThrow(() -> new RuntimeException("未找到模板"));
        Template template = configuration.getTemplate(templateInfo);
        try(Writer writer = new FileWriter(outputPath)) {
            template.process(model, writer);
        }
    }

    /**
     * 将用户没有输入的必填参数拼接到args中
     */
    public static String[] fillRequiredOptions(String[] args) {
        if (isStandardHelpOption(args)) return args;

        CommandEnums commandEnums = CommandEnums.fromName(args[0]);
        if (Objects.isNull(commandEnums)) return args;

        List<String> missingOptions = new ArrayList<>();
        Field[] fields = ReflectUtil.getFields(commandEnums.getModelClass());

        for (Field field : fields) {
            if (!field.isAnnotationPresent(CommandLine.Option.class)) continue;
            CommandLine.Option option = field.getAnnotation(CommandLine.Option.class);
            if (option.required()) {
                String[] names = option.names();
                // 检查参数中是否已经提供了该选项
                boolean provided = Arrays.stream(args)
                        .anyMatch(arg -> Arrays.asList(names).contains(arg));
                if (!provided) missingOptions.add(names[0]);
            }
        }

        String[] result = Arrays.copyOf(args, args.length + missingOptions.size());
        int index = args.length;
        for (String optionName : missingOptions) {
            result[index++] = optionName;
        }
        return result;
    }

    private static boolean isStandardHelpOption(String[] args) {
        return ArrayUtil.isEmpty(args) || args[0].equals("-h") || args[0].equals("--help") || args[0].equals("-V") || args[0].equals("--version");
    }

}

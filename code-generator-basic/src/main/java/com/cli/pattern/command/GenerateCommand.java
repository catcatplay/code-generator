package com.cli.pattern.command;

import com.dto.Loop;
import com.utils.FreeMarkerUtils;
import freemarker.template.TemplateException;
import picocli.CommandLine;

import java.io.IOException;

/**
 * 生成代码
 */
@CommandLine.Command(name = "generate", version = "GenerateCommand 1.0", mixinStandardHelpOptions = true, description = "生成代码")
public class GenerateCommand implements Runnable{
    @CommandLine.Option(names = {"-a", "--author"}, arity = "0..1", description = "作者", required = true, interactive = true, echo = true)
    String author;
    @CommandLine.Option(names = {"-l", "--loop"}, arity = "0..1", description = "循环次数", required = true, interactive = true, echo = true)
    Integer loop;
    @CommandLine.Option(names = {"-p", "--path"}, arity = "0..1", description = "输出路径", required = true, interactive = true, echo = true)
    String path;
    @Override
    public void run() {
        Loop entity = new Loop().setAuthor(author).setLoop(loop);
        path = path + "\\" + "loop.java";
        try {
            FreeMarkerUtils.process(entity, path);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}

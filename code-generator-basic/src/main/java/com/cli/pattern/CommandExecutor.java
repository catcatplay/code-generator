package com.cli.pattern;

import com.cli.pattern.command.ConfigCommand;
import com.cli.pattern.command.GenerateCommand;
import com.cli.pattern.command.ListCommand;
import com.utils.FreeMarkerUtils;
import picocli.CommandLine;

@CommandLine.Command(name = "CommandExecutor", version = "CommandExecutor 1.0", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {
    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new ListCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new GenerateCommand());
    }

    @Override
    public void run() {
        // 不输入子命令时，给出提示
        System.out.println("请输入具体命令，或者输入 --help 查看命令提示");
    }

    /**
     * 执行命令
     */
    public void doExecute(String[] args) {
        commandLine.execute(FreeMarkerUtils.fillRequiredOptions(args));
    }
}

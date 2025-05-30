package com.cli;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "User name", interactive = true, required = true)
    String user;

    @Option(names = {"-p", "--password"}, description = "Passphrase", interactive = true, required = true)
    String password;

    public Integer call() throws Exception {
        // 判断密码是否填写
//        if (password == null) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.print("Enter value for --password: ");
//            password = scanner.nextLine();
//        }
        System.out.println("user = " + user);
        System.out.println("password = " + password);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new Login()).execute(getStrings(args));
    }

    /**
     * 将用户没有输入的必填参数拼接到args中
     */
    private static String[] getStrings(String[] args) {
        List<String> missingOptions = new ArrayList<>();
        Class<Login> loginClass = Login.class;
        Field[] fields = loginClass.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Option.class)) continue;
            Option option = field.getAnnotation(Option.class);
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
}

import com.utils.FileUtils;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTest {
    @Test
    public void test() {
        // 获取项目根路径
        Path outputPath = Paths.get(System.getProperty("user.dir"));
        Path path = outputPath.getParent();
        Path inputPath = path.resolve("code-generator-demo/test");
        FileUtils.copyFiles(inputPath, outputPath);
    }
}

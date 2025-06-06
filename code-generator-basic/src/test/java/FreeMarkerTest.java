import com.dto.*;
import com.utils.FreeMarkerUtils;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class FreeMarkerTest {
    @Test
    public void test() throws IOException, TemplateException {
        DataModel dataModel = new DataModel();
        dataModel.setCurrentYear("2025");
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem1 = new MenuItem().setUrl("http://freemarker.foofun.cn/dgui_quickstart_template.html").setLabel("模版在线手册");
        MenuItem menuItem2 = new MenuItem().setUrl("http://freemarker.foofun.cn/dgui_misc_userdefdir.html").setLabel("模版自定义指令");
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        dataModel.setMenuItems(menuItems);
        FreeMarkerUtils.process(dataModel, "code-generator-basic/src/main/myWeb.html");

        Loop loop = new Loop();
        loop.setAuthor("念");
        loop.setLoop(5);
        FreeMarkerUtils.process(loop, "code-generator-basic/src/main/java/com/loop.java");

        ArrayList<User> user = new ArrayList<>();
        user.add(new User().setName("Administrator").setPassword(""));
        user.add(new User().setName("Contriol").setPassword("Contriol"));
        user.add(new User().setName("'Normal").setPassword("Normal"));
        user.add(new User().setName("admin").setPassword("admin704"));
        user.add(new User().setName("Control").setPassword("Control704"));
        user.add(new User().setName("admin").setPassword(""));
        Users users = new Users();
        users.setUsers(user);
        FreeMarkerUtils.process(users, "code-generator-basic/src/main/loginDates.py");
    }
}

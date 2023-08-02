package cn.hamm.demo.data;

import cn.hamm.airpower.util.ReflectUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class PermissionTests {

    @Test
    void getApiList(){
        List<Map<String, Object>> appModuleList = ReflectUtil.getApiTreeList("cn.hamm");
        System.out.println(1);
    }
}

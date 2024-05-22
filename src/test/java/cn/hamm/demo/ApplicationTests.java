package cn.hamm.demo;

import cn.hamm.airpower.enums.Api;
import cn.hamm.airpower.util.DictionaryUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class ApplicationTests {
    @Test
    void testInit() {
        List<Map<String, Object>> dictionaryList = new DictionaryUtil().getDictionaryList(Api.class, Api::getKey, Api::getLabel, Api::getMethodName);
        System.out.println(dictionaryList);
    }

}

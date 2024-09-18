package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.DesensitizeExclude;
import cn.hamm.airpower.enums.ServiceError;
import cn.hamm.airpower.interfaces.IDictionary;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.open.OpenArithmeticType;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.util.DictionaryUtil;
import cn.hamm.airpower.util.ReflectUtil;
import cn.hamm.demo.common.exception.CustomError;
import cn.hamm.demo.module.webhook.enums.WebHookScene;
import cn.hamm.demo.module.webhook.enums.WebHookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("/system")
@Description("系统信息")
public class SystemController extends RootController {
    @Autowired
    private DictionaryUtil dictionaryUtil;

    @Autowired
    private ReflectUtil reflectUtil;

    @RequestMapping("")
    @DesensitizeExclude
    public Json index() {
        return Json.success("成功");
    }

    @RequestMapping("getErrorCodes")
    public Json getErrorCodes() {
        List<Map<String, Object>> serviceErrors = dictionaryUtil.getDictionaryList(ServiceError.class);
        List<Map<String, Object>> customErrors = dictionaryUtil.getDictionaryList(CustomError.class);
        // 合到一个List
        serviceErrors.addAll(customErrors);
        return Json.data(serviceErrors);
    }

    @RequestMapping("getDictionary")
    public Json getDictionary() {
        List<Class<? extends IDictionary>> classList = new ArrayList<>();
        classList.add(OpenArithmeticType.class);
        classList.add(WebHookScene.class);
        classList.add(WebHookType.class);
        List<Map<String, Object>> list = new ArrayList<>(classList.size());
        for (var clazz : classList) {
            list.add(Map.of(
                    "name", clazz.getSimpleName(),
                    "description", reflectUtil.getDescription(clazz),
                    "options", dictionaryUtil.getDictionaryList(clazz)
            ));
        }
        return Json.data(list);
    }
}

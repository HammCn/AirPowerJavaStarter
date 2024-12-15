package cn.hamm.demo.module.system;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.airpower.interfaces.IDictionary;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.open.OpenArithmeticType;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.util.DictionaryUtil;
import cn.hamm.airpower.util.ReflectUtil;
import cn.hamm.demo.common.exception.CustomError;
import cn.hamm.demo.module.notify.enums.NotifyScene;
import cn.hamm.demo.module.notify.enums.NotifyChannel;
import org.springframework.web.bind.annotation.PostMapping;

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
    @PostMapping("getErrorCodes")
    public Json getErrorCodes() {
        List<Map<String, Object>> serviceErrors = DictionaryUtil.getDictionaryList(ServiceError.class);
        List<Map<String, Object>> customErrors = DictionaryUtil.getDictionaryList(CustomError.class);
        // 合到一个List
        serviceErrors.addAll(customErrors);
        return Json.data(serviceErrors);
    }

    @PostMapping("getDictionary")
    public Json getDictionary() {
        List<Class<? extends IDictionary>> classList = new ArrayList<>();
        classList.add(OpenArithmeticType.class);
        classList.add(NotifyScene.class);
        classList.add(NotifyChannel.class);
        List<Map<String, Object>> list = new ArrayList<>(classList.size());
        for (var clazz : classList) {
            list.add(Map.of(
                    "name", clazz.getSimpleName(),
                    "description", ReflectUtil.getDescription(clazz),
                    "options", DictionaryUtil.getDictionaryList(clazz)
            ));
        }
        return Json.data(list);
    }
}

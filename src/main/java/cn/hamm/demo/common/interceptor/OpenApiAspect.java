package cn.hamm.demo.common.interceptor;

import cn.hamm.demo.common.Services;
import cn.hamm.demo.common.annotation.OpenApi;
import cn.hamm.demo.module.open.app.OpenAppEntity;
import cn.hamm.demo.module.open.base.OpenErrorCode;
import cn.hamm.demo.module.open.base.OpenRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * <h1>OpenApi切面</h1>
 *
 * @author Hamm.cn
 */
@Aspect
@Component
public class OpenApiAspect {
    @SuppressWarnings("EmptyMethod")
    @Pointcut("@annotation(cn.hamm.demo.common.annotation.OpenApi)")
    public void pointCut() {

    }

    /**
     * <h2>开放API</h2>
     */
    @Around("pointCut()")
    public Object openApi(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        OpenApi openApi = method.getAnnotation(OpenApi.class);
        OpenErrorCode.API_NOT_SUPPORT.whenNull(openApi);
        for (Object arg : args) {
            if (arg instanceof OpenRequest request) {
                OpenErrorCode.INVALID_APP_KEY.when(!StringUtils.hasText(request.getAppKey()));
                OpenAppEntity openApp = Services.getOpenAppService().getByAppKey(request.getAppKey());
                OpenErrorCode.INVALID_APP_KEY.whenNull(openApp);
                request.setOpenApp(openApp);
                request.checkSignature();
                break;
            }
        }
        return proceedingJoinPoint.proceed();
    }
}


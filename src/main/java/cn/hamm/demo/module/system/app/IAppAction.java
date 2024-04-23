package cn.hamm.demo.module.system.app;

/**
 * <h1>应用行为</h1>
 *
 * @author Hamm.cn
 */
public interface IAppAction {
    /**
     * <h2>Code换AccessToken</h2>
     */

    interface WhenCode2AccessToken {
    }

    /**
     * <h2>应用Key查询应用</h2>
     */
    interface WhenGetByAppKey {
    }

    /**
     * <h2>重置密钥</h2>
     */
    interface WhenResetSecret {
    }
}

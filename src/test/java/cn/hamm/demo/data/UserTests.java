package cn.hamm.demo.data;

import cn.hamm.demo.module.hr.user.UserEntity;
import cn.hamm.demo.module.hr.user.UserVo;
import org.junit.jupiter.api.Test;

/**
 * @author Hamm https://hamm.cn
 */
public class UserTests {
    @Test
    void userCopyDemo() {
        UserEntity userEntity = new UserEntity().setId(1L).setNickname("Hamm");
        UserVo userVo = userEntity.copyTo(UserVo.class);
        System.out.println("OK");
    }
}

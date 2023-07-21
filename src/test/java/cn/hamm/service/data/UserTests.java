package cn.hamm.service.data;

import cn.hamm.service.web.user.UserEntity;
import cn.hamm.service.web.user.UserVo;
import org.junit.jupiter.api.Test;

/**
 * @author Hamm https://hamm.cn
 */
public class UserTests {
    @Test
    void userCopyDemo(){
        UserEntity userEntity = new UserEntity().setId(1L).setNickname("Hamm");
        UserVo userVo = userEntity.copyTo(UserVo.class);
        System.out.println("OK");
    }
}

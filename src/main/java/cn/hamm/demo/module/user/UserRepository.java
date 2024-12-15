package cn.hamm.demo.module.user;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>Repository</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface UserRepository extends BaseRepository<UserEntity> {
    /**
     * <h3>根据邮箱查询一个用户</h3>
     *
     * @param email 邮箱
     * @return 用户
     */
    UserEntity getByEmail(String email);

    /**
     * <h3>根据手机号查询一个用户</h3>
     *
     * @param phone 手机号
     * @return 用户
     */
    UserEntity getByPhone(String phone);
}

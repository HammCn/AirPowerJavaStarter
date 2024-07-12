package cn.hamm.demo.module.webhook;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>数据库连接信息</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface WebHookRepository extends BaseRepository<WebHookEntity> {
}

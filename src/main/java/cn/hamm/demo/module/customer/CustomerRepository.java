package cn.hamm.demo.module.customer;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>数据库连接信息</h1>
 *
 * @author Hamm
 */
@Repository
public interface CustomerRepository extends BaseRepository<CustomerEntity> {
}

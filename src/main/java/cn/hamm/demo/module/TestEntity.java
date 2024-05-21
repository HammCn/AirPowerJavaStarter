package cn.hamm.demo.module;

import cn.hamm.airpower.annotation.Desensitize;
import cn.hamm.demo.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Hamm.cn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TestEntity extends BaseEntity<TestEntity> {
    @Desensitize(Desensitize.Type.CHINESE_NAME)
    private String name = "凌小云";

    @Desensitize(Desensitize.Type.ID_CARD)
    private String idCard = "500110199801010102";

    @Desensitize(Desensitize.Type.EMAIL)
    private String email = "admin@hamm.cn";

    @Desensitize(Desensitize.Type.TELEPHONE)
    private String tel = "02373333333";

    @Desensitize(Desensitize.Type.MOBILE)
    private String mobile = "17666666666";

    @Desensitize(Desensitize.Type.CAR_NUMBER)
    private String car = "渝D12345";

    @Desensitize(Desensitize.Type.IP_V4)
    private String ip = "127.182.123.423";

    @Desensitize(value = Desensitize.Type.ADDRESS, head = 3, tail = 2)
    private String address = "重庆市巴南区蓝光林肯公园";

    @Desensitize(Desensitize.Type.BANK_CARD)
    private String bank = "6222222222222222222";
}

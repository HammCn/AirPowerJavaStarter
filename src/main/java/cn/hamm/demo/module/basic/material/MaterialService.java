package cn.hamm.demo.module.basic.material;

import cn.hamm.airpower.root.RootService;
import cn.hamm.demo.module.basic.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <h1>Service</h1>
 *
 * @author Hamm
 */
@Service
public class MaterialService extends RootService<MaterialEntity, MaterialRepository> {
    @Autowired
    UnitService unitService;

    @Override
    protected MaterialEntity beforeSaveToDatabase(MaterialEntity entity) {
        entity.setUnitInfo(unitService.getById(entity.getUnitInfo().getId()));
        return entity;
    }
}

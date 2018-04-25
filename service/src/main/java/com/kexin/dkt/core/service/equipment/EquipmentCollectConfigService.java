package com.kexin.dkt.core.service.equipment;

import com.kexin.dkt.core.dao.equipment.EquipmentCollectConfigDao;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/10/27.
 */
@Service
public class EquipmentCollectConfigService {
    @Autowired
    private EquipmentCollectConfigDao collectconfigdao;


}

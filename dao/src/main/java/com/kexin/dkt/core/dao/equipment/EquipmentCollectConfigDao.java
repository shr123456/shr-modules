package com.kexin.dkt.core.dao.equipment;

import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by admin on 2017/10/27.
 */
@MapperScan
public interface EquipmentCollectConfigDao {
    /**
     *  获取某个拥有的dtu_no,meter_address
     * @param collectconfig
     * @return
     */
    public List<EquipmentCollectConfig> getDtoMeterAddress(EquipmentCollectConfig collectconfig);

}

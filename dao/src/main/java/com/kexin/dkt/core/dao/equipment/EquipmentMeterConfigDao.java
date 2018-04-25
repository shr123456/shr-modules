package com.kexin.dkt.core.dao.equipment;

import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import org.mybatis.spring.annotation.MapperScan;
import java.util.List;

/**
 * Created by admin on 2017/10/26.
 */
@MapperScan
public interface EquipmentMeterConfigDao {
    /**
     *  获取某个拥有的dtu_no,meter_address
     * @param meterconfig
     * @return
     */
    public List<EquipmentMeterConfig> getMeterConfig(EquipmentMeterConfig meterconfig);
    /**
     *  根据设备id获取设备的dtu_no,meter_address
     * @param meterconfig
     * @return
     */
    public List<EquipmentMeterConfig> getDtoMeterAddress(EquipmentMeterConfig meterconfig);
}

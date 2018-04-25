package com.kexin.dkt.core.service.monitoring;

import com.kexin.dkt.core.common.echarts.EchartsUtils;
import com.kexin.dkt.core.common.util.CalculateUtils;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.dao.customer.CustomerDao;
import com.kexin.dkt.core.dao.energy.EnergyDao;
import com.kexin.dkt.core.dao.equipment.EquipmentCollectConfigDao;
import com.kexin.dkt.core.dao.equipment.EquipmentDao;
import com.kexin.dkt.core.dao.monitoring.MonitoringDao;
import com.kexin.dkt.core.entity.collect.CollectBaseInfo;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.echarts.EchartsSeries;
import com.kexin.dkt.core.entity.equipment.EquipmentCollectConfig;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.service.energy.EnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by kexin on 2017/11/30.
 */
@Service
public class MonitoringService {

    @Autowired
    private MonitoringDao monitoringDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private EnergyDao energyDao;

    /*设备用电排名报表*/
    public List<Map<String, Object>> getEquipmentElectricRanking(ParamDate paramDate) {
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> mapList =  monitoringDao.getEquipmentElectricRanking(paramDate);
        ParamDate param = new ParamDate();
        param.setEndDate(paramDate.getEndDate());
        String suffixTable = param.getEndDate().replace("-","_");
        param.setTable("kxtimingdata.collect_base_info"+"_"+suffixTable);
        if(mapList !=null && mapList.size()>0){
            for(Map<String,Object> tempMap : mapList){
                param.setDtuNo(tempMap.get("dtuNo").toString());
                param.setMeterAddress(Long.parseLong(tempMap.get("meterAddress").toString()));
                Map<String,Object> map = monitoringDao.findEquipmentRuntime(param);
                if(map !=null) {
                    map.put("id", tempMap.get("id"));
                    map.put("dtuNo", param.getDtuNo());
                    map.put("meterAddress", param.getMeterAddress());
                    map.put("collectConfigName", tempMap.get("collectConfigName"));
                    map.put("electric", tempMap.get("electric"));
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 查询设备详情及用电量
     */
    public Map<String,Object> loadEquipmentDetail(EquipmentMeterConfig config) {
        Map<String,Object> map = equipmentDao.loadEquipmentDetail(config.getId());

        //获取前月的第一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String PreFirstDay = sdf.format(cal_1.getTime());
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        String preLastDay = sdf.format(cale.getTime());

        ParamDate paramDate = new ParamDate();
        paramDate.setFirstDate(PreFirstDay);
        paramDate.setEndDate(preLastDay);
        paramDate.setLpId((int) config.getId());
        paramDate.setCustomerId(map.get("customerId").toString());
        List<CustCustomer> custList = customerDao.findMethByCustomerId(config.getCustomerId());
        if(custList !=null && custList.size() >0){
            paramDate.setGenre(Integer.parseInt(custList.get(0).getMeasureMethods()));
        }

        //上月用电量
        double preMonthElectric = 0.0;
        ElecQuantity elecQuantityByPreMonth = energyDao.findAllEnergyByMonth(paramDate);
        if(elecQuantityByPreMonth !=null){
            preMonthElectric = elecQuantityByPreMonth.getSumElectric();
        }
        //本月用电量
        double toMonthElectric = 0.0;
        String firstTime = DateUtils.date2Str(new Date(),"yyyy-MM")+"-01";
        String endTime = DateUtils.date2Str(new Date(),"yyyy-MM-dd");
        paramDate.setFirstDate(firstTime);
        paramDate.setEndDate(endTime);
        ElecQuantity elecQuantity = energyDao.findAllEnergyByMonth(paramDate);
        if(elecQuantity !=null){
            toMonthElectric = elecQuantity.getSumElectric();
        }
        map.put("preMonthElectric",preMonthElectric);
        map.put("toMonthElectric",toMonthElectric);
        return map;
    }

    /**
     * 查询电流/电压/电量echarts图表
     * @param paramDate
     * @return
     */
    public String loadEquipmentEcharts(ParamDate paramDate) {
        /*根据设备id获取Dtu与地址*/
        Map<String,Object> map = equipmentDao.getDtoMeterAddressByLpId(paramDate.getLpId());
        paramDate.setDtuNo(map.get("dtuNo").toString());
        paramDate.setMeterAddress(Long.parseLong(map.get("meterAddress").toString()));
        String suffixTable = paramDate.getEndDate().replace("-","_");
        paramDate.setTable("kxtimingdata.collect_base_info"+"_"+suffixTable);
        String option = "";
        List<List<String>> data = new ArrayList<List<String>>();
        List<EchartsSeries> seriesList = new ArrayList<EchartsSeries>();
        List<CollectBaseInfo> collectList= new ArrayList<CollectBaseInfo>();
        String [] color = {"#5793f3", "#28ff28", "#d14a61"};
        if("energy".equals(paramDate.getTabName())){
            //电量
            List<ElecQuantity> quantityList = monitoringDao.findEnergyRunTime(paramDate);
            String[] dataTypeArray = {"电量"};
            List<String> dataList = new ArrayList<String>();
            List<String> dateList = new ArrayList<String>();
            if(quantityList !=null && quantityList.size() >0) {
                for (ElecQuantity elecQuantity : quantityList) {
                    dateList.add(elecQuantity.getDateStr().substring(10,elecQuantity.getDateStr().length()-5));
                    dataList.add(elecQuantity.getSumElectric().toString());
                }
                data.add(dataList);
            }
            if(data !=null && data.size()>0) {
                seriesList = getSeriesList(data, dataTypeArray);
                option = EchartsUtils.getOptionManyLine("电量/KW·h",color,dataTypeArray,dateList.toArray(new String[dateList.size()]),seriesList);
            }
        }else if("voltage".equals(paramDate.getTabName())){
            //三相电压
            collectList= monitoringDao.findVoltageRunTime(paramDate);
            String[] dataTypeArray = {"A相电压","B相电压","C相电压"};
            List<String> dateList = new ArrayList<String>();
            List<String> AvoltageList = new ArrayList<String>();
            List<String> BvoltageList = new ArrayList<String>();
            List<String> CvoltageList = new ArrayList<String>();
            if(collectList !=null && collectList.size() >0) {
                for(CollectBaseInfo info : collectList) {
                    dateList.add(info.getCollectTime().substring(10,info.getCollectTime().length()-5));
                    AvoltageList.add(CalculateUtils.get2PointValue(info.getAVoltage())+"");
                    BvoltageList.add(CalculateUtils.get2PointValue(info.getBVoltage())+"");
                    CvoltageList.add(CalculateUtils.get2PointValue(info.getCVoltage())+"");
                }
                data.add(AvoltageList);
                data.add(BvoltageList);
                data.add(CvoltageList);
            }
            if(data !=null && data.size()>0) {
                seriesList = getSeriesList(data, dataTypeArray);
                option = EchartsUtils.getOptionManyLine("电压/V",color,dataTypeArray,dateList.toArray(new String[dateList.size()]),seriesList);
            }
        }else if("electricity".equals(paramDate.getTabName())){
            //三相电流
            String[] dataTypeArray = {"A相电流","B相电流","C相电流"};
            List<String> dateList = new ArrayList<String>();
            List<String> AElectricityList = new ArrayList<String>();
            List<String> BElectricityList = new ArrayList<String>();
            List<String> CElectricityList = new ArrayList<String>();
            collectList= monitoringDao.findElectricityRunTime(paramDate);
            if(collectList !=null && collectList.size() >0) {
                for(CollectBaseInfo info : collectList) {
                    dateList.add(info.getCollectTime().substring(10,info.getCollectTime().length()-5));
                    AElectricityList.add(CalculateUtils.get2PointValue(info.getAElectricity())+"");
                    BElectricityList.add(CalculateUtils.get2PointValue(info.getBElectricity())+"");
                    CElectricityList.add(CalculateUtils.get2PointValue(info.getCElectricity())+"");
                }
                data.add(AElectricityList);
                data.add(BElectricityList);
                data.add(CElectricityList);
            }
            if(data !=null && data.size()>0) {
                seriesList = getSeriesList(data, dataTypeArray);
                option = EchartsUtils.getOptionManyLine("电流/A",color,dataTypeArray,dateList.toArray(new String[dateList.size()]),seriesList);
            }
        }else if("powerFactor".equals(paramDate.getTabName())){
            //总功率因素
            collectList= monitoringDao.findPowerFactorRunTime(paramDate);
            String[] dataTypeArray = {"功率因素"};
            List<String> dateList = new ArrayList<String>();
            List<String> powerFactorList = new ArrayList<String>();
            if(collectList !=null && collectList.size() >0) {
                for(CollectBaseInfo info : collectList) {
                    dateList.add(info.getCollectTime().substring(10,info.getCollectTime().length()-5));
                    powerFactorList.add(info.getTotalPowerFactor()+"");
                }
                data.add(powerFactorList);
            }
            if(data !=null && data.size()>0) {
                seriesList = getSeriesList(data, dataTypeArray);
                option = EchartsUtils.getOptionManyLine("功率/P",color, dataTypeArray, dateList.toArray(new String[dateList.size()]), seriesList);
            }
        }
        return option;
    }

    private List<EchartsSeries> getSeriesList( List<List<String>> data, String[] dataTypeArray) {
        List<EchartsSeries> seriesList = new ArrayList<EchartsSeries>();
        for(int i = 0 ;i<dataTypeArray.length;i++){
            EchartsSeries series = new EchartsSeries();
            series.setData(data.get(i).toArray(new String[data.get(i).size()]));
            series.setType("line");
            series.setName(dataTypeArray[i]);
            seriesList.add(series);
        }
        return seriesList;
    }

    /**
     * 显示近一周的报表
     * @param paramDate
     * @return
     */
    public List<Map<String, Object>> getWeekSheet(ParamDate paramDate) {
        /*根据设备id获取Dtu与地址*/
        Map<String,Object> map = equipmentDao.getDtoMeterAddressByLpId(paramDate.getLpId());
        ParamDate param = new ParamDate();
        param.setDtuNo(map.get("dtuNo").toString());
        param.setMeterAddress(Long.parseLong(map.get("meterAddress").toString()));
        //设置查询日期为近一周
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.str2Date(paramDate.getEndDate(),"yyyy-MM-dd"));
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        param.setCustomerId(paramDate.getCustomerId());
        param.setLpId(paramDate.getLpId());
        char[] array = {'日','一', '二', '三', '四', '五', '六'};
        for(int i = 0;i<=6;i++){
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
            param.setFirstDate(DateUtils.date2Str(calendar.getTime(),"yyyy-MM-dd"));
            param.setEndDate(param.getFirstDate());
            //用小时表查询每天的用电量
            List<ElecQuantity> elecQuantityList = energyDao.findElectricByDay(param);
            Map<String,Object> sheetMap = new HashMap<String, Object>();
            String suffixTable = param.getEndDate().replace("-", "_");
            param.setTable("kxtimingdata.collect_base_info" + "_" + suffixTable);
            Map<String,BigDecimal> tempMap = monitoringDao.findVoltageAndElec(param);
            double electric = 0.0;
            if(elecQuantityList !=null && elecQuantityList.size()>0){
                electric = elecQuantityList.get(0).getSumElectric();
            }
            double maxVoltage = 0.0;
            double minVoltage = 0.0;
            double maxElectricity = 0.0;
            double minPowerFactor = 0.0;
            if(tempMap != null){
                //最大电压
                maxVoltage = tempMap.get("maxAVoltage").doubleValue() > tempMap.get("maxBVoltage").doubleValue() ? tempMap.get("maxAVoltage").doubleValue():tempMap.get("maxBVoltage").doubleValue();
                maxVoltage = maxVoltage >tempMap.get("maxCVoltage").doubleValue() ? maxVoltage : tempMap.get("maxCVoltage").doubleValue();
                //最小电压
                minVoltage = tempMap.get("minAVoltage").doubleValue() < tempMap.get("minBVoltage").doubleValue() ? tempMap.get("minAVoltage").doubleValue():tempMap.get("minBVoltage").doubleValue();
                minVoltage = minVoltage < tempMap.get("minCVoltage").doubleValue() ? minVoltage : tempMap.get("minCVoltage").doubleValue();
                //最大电流
                maxElectricity = tempMap.get("maxAElectricity").doubleValue() > tempMap.get("maxBElectricity").doubleValue() ? tempMap.get("maxAElectricity").doubleValue():tempMap.get("maxBElectricity").doubleValue();
                maxElectricity = maxElectricity > tempMap.get("maxCElectricity").doubleValue() ? maxElectricity : tempMap.get("maxCElectricity").doubleValue();
                //最小功率因素
                minPowerFactor = tempMap.get("minAPowerFactor").doubleValue() < tempMap.get("minBPowerFactor").doubleValue() ? tempMap.get("minAPowerFactor").doubleValue():tempMap.get("minBPowerFactor").doubleValue();
                minPowerFactor = minPowerFactor < tempMap.get("minCPowerFactor").doubleValue() ? minPowerFactor : tempMap.get("minCPowerFactor").doubleValue();
            }
            sheetMap.put("maxVoltage",CalculateUtils.get2PointValue(maxVoltage));
            sheetMap.put("minVoltage",CalculateUtils.get2PointValue(minVoltage));
            sheetMap.put("maxElectricity",CalculateUtils.get2PointValue(maxElectricity));
            sheetMap.put("minPowerFactor",CalculateUtils.get2PointValue(minPowerFactor));
            sheetMap.put("collectTime",param.getFirstDate());
            sheetMap.put("electric",electric);
            sheetMap.put("weekDay","周"+array[calendar.get(Calendar.DAY_OF_WEEK)-1]);
            mapList.add(sheetMap);
        }
        return mapList;
    }
}

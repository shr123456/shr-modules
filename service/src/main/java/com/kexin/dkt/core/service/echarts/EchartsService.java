package com.kexin.dkt.core.service.echarts;

import com.kexin.dkt.core.dao.echarts.EchartsDao;
import com.kexin.dkt.core.entity.collect.CollectTotalPAtEeDay;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import com.kexin.dkt.core.entity.vo.EchartsData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/10/25.
 */
@Slf4j
@Service
public class EchartsService {

    @Autowired
    private EchartsDao echartsDao ;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 获取某个设备一段时间总、峰、平、谷等数据
     * @param collecttotalpateeday
     * @return
     */
    public List<EchartsData> findByEnergyCount(CollectTotalPAtEeDay collecttotalpateeday){
        List<CollectTotalPAtEeDay>  dataList = echartsDao.getOption(collecttotalpateeday);
        List<EchartsData> jsonDatas = new ArrayList<EchartsData>();
        if(dataList.size()>0) {
            EchartsData jsonDat1 = new EchartsData();
            EchartsData jsonDat2 = new EchartsData();
            EchartsData jsonDat3 = new EchartsData();
            EchartsData jsonDat4 = new EchartsData();
            List<String> data1 = new ArrayList<String>();
            List<String> data2 = new ArrayList<String>();
            List<String> data3 = new ArrayList<String>();
            List<String> data4 = new ArrayList<String>();
            List<String> xName = new ArrayList<String>();
            EchartsData jsonData = new EchartsData();
            for (int i = 0; i < dataList.size(); i++) {
                CollectTotalPAtEeDay List = dataList.get(i);
                SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
                String date = myFmt2.format(List.getCollectDate());
                xName.add(date);
                data1.add(String.valueOf(List.getDiffTotalPAtEe()));
                data2.add(String.valueOf(List.getPeakTotalPAtEe()));
                data3.add(String.valueOf(List.getValleyTotalPAtEe()));
                data4.add(String.valueOf(List.getFlatTotalPAtEe()));
            }
            //总 峰 平 谷
            jsonDat1.setName("总");
            jsonDat1.setData(data1);
            jsonDat1.setXcontent(xName);
            jsonDat2.setName("峰");
            jsonDat2.setData(data2);
            jsonDat2.setXcontent(xName);
            jsonDat3.setName("平");
            jsonDat3.setData(data3);
            jsonDat3.setXcontent(xName);
            jsonDat4.setName("谷");
            jsonDat4.setData(data4);
            jsonDat4.setXcontent(xName);
            jsonDatas.add(jsonDat1);
            jsonDatas.add(jsonDat2);
            jsonDatas.add(jsonDat3);
            jsonDatas.add(jsonDat4);
        }else {
            jsonDatas=null;
        }
        return jsonDatas;

    }

    /**
     * 获取某个客户一段时间总、峰、平、谷等数据
     * @param dtumeter
     * @return
     */
    public List<EchartsData> findByEnergyCounts(List<EquipmentMeterConfig>  dtumeter,CollectTotalPAtEeDay collecttotalpateeday) {
        StringBuffer sb = new StringBuffer();
        List<EchartsData> jsonDatas = new ArrayList<EchartsData>();
        EchartsData jsonDat1 = new EchartsData();
        EchartsData jsonDat2 = new EchartsData();
        EchartsData jsonDat3 = new EchartsData();
        EchartsData jsonDat4 = new EchartsData();
        List<String> data1 = new ArrayList<String>();
        List<String> data2 = new ArrayList<String>();
        List<String> data3 = new ArrayList<String>();
        List<String> data4 = new ArrayList<String>();
        List<String> xName = new ArrayList<String>();
        EchartsData jsonData = new EchartsData();
        DecimalFormat df = new DecimalFormat("#.00");
        if (dtumeter.size() > 1) {
            for (EquipmentMeterConfig config : dtumeter) {
                if (sb.length() > 1) {
                    sb.append(" or (dtu_no='" + config.getDtuNo() + "' and meter_address='" + config.getMeterAddress() + "')");
                } else {
                    sb.append("(dtu_no='" + config.getDtuNo() + "' and meter_address='" + config.getMeterAddress() + "')");
                }
            }
            String sql = " SELECT collect_date, sum(diff_total_p_at_ee) as diff_total_p_at_ee, SUM(peak_total_p_at_ee) as peak_total_p_at_ee," +
                    "SUM(valley_total_p_at_ee) as valley_total_p_at_ee,SUM(flat_total_p_at_ee)  as flat_total_p_at_ee  from kxtimingdata.collect_total_p_at_ee_day" +
                    " where (" + sb.toString() + ") and collect_date BETWEEN '"+collecttotalpateeday.getBeginTime()+"' AND '"+collecttotalpateeday.getEndTime()+"' GROUP BY collect_date";
            log.info("获取某个客户一段时间总、峰、平、谷等数据 : " + sql);
            List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql);
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> map = dataList.get(i);
                    SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
                    Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry<String, Object> entry = entries.next();
                        if (entry.getKey().equals("collect_date")) {
                            if (entry.getValue() == null) {
                                xName.add("0");
                            } else {
                               xName.add(entry.getValue().toString());
                            }
                        }
                        if (entry.getKey().equals("diff_total_p_at_ee")) {
                            if (entry.getValue() == null) {
                                data1.add("0");
                            } else {
                                data1.add(df.format(entry.getValue()).toString());
                            }
                        }
                        if (entry.getKey().equals("peak_total_p_at_ee")) {
                            if (entry.getValue() == null) {
                                data2.add("0");
                            } else {
                                data2.add(df.format(entry.getValue()).toString());
                            }
                        }
                        if (entry.getKey().equals("valley_total_p_at_ee")) {
                            if (entry.getValue() == null) {
                                data3.add("0");
                            } else {
                                data3.add(df.format(entry.getValue()).toString());
                            }
                        }
                        if (entry.getKey().equals("flat_total_p_at_ee")) {
                            if (entry.getValue() == null) {
                                data4.add("0");
                            } else {
                                data4.add(df.format(entry.getValue()).toString());
                            }
                        }


                    }
                }
            //总 峰 平 谷
                jsonDat1.setName("总");
                jsonDat1.setData(data1);
                jsonDat1.setXcontent(xName);
                jsonDat2.setName("峰");
                jsonDat2.setData(data2);
                jsonDat2.setXcontent(xName);
                jsonDat3.setName("平");
                jsonDat3.setData(data3);
                jsonDat3.setXcontent(xName);
                jsonDat4.setName("谷");
                jsonDat4.setData(data4);
                jsonDat4.setXcontent(xName);
                jsonDatas.add(jsonDat1);
                jsonDatas.add(jsonDat2);
                jsonDatas.add(jsonDat3);
                jsonDatas.add(jsonDat4);

            }else {
                jsonDatas=null;
            }

        }
        return jsonDatas;
    }

    /**
     * 获取某个用户当月总、峰、平、谷等数据
     * @param collecttotalpateeday
     * @return
     */
    public List<CollectTotalPAtEeDay> findByMonthEnergyCount(CollectTotalPAtEeDay collecttotalpateeday){


        return  echartsDao.getOptionbyMonth(collecttotalpateeday);

    }
}

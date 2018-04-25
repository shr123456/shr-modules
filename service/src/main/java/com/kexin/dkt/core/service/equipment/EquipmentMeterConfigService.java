package com.kexin.dkt.core.service.equipment;

import com.kexin.dkt.core.common.constant.ElecPrice;
import com.kexin.dkt.core.dao.equipment.EquipmentMeterConfigDao;
import com.kexin.dkt.core.entity.equipment.EquipmentMeterConfig;
import com.kexin.dkt.core.entity.vo.EchartsData;
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
 * Created by admin on 2017/10/26.
 */
@Service
public class EquipmentMeterConfigService {
    @Autowired
    private EquipmentMeterConfigDao equipmentMeterConfigDao ;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 获取某个用户所有的dtu_no,meter_address
     * @param meterconfig
     * @return
     */
    public List<EquipmentMeterConfig> getMeterConfigList(EquipmentMeterConfig meterconfig){
        List<EquipmentMeterConfig> meterconfigList =equipmentMeterConfigDao.getMeterConfig(meterconfig);
        return  meterconfigList;

    }
    /**
     * 获取某个用户当月总、峰、平、谷等数据
     * @param meterconfig
     * @return
     */
    public List<EchartsData> viewListByMonth(EquipmentMeterConfig meterconfig){
        List<EquipmentMeterConfig> meterconfigList =getMeterConfigList(meterconfig);
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
        if(meterconfigList.size()>0) {
            //查询三相电流
            for (EquipmentMeterConfig config : meterconfigList) {
                if (sb.length() > 1) {
                    sb.append(" or (dtu_no='" + config.getDtuNo() + "' and meter_address='" + config.getMeterAddress() + "')");
                } else {
                    sb.append("(dtu_no='" + config.getDtuNo() + "' and meter_address='" + config.getMeterAddress() + "')");
                }
            }
            String sql="SELECT date_format(tt.collect_date,'%y-%m-%d')  as collect_date,\n" +
                    "        SUM(tt.diff_total_p_at_ee) as diff_total_p_at_ee,\n" +
                    "        SUM(tt.peak_total_p_at_ee) as peak_total_p_at_ee,\n" +
                    "        SUM(tt.valley_total_p_at_ee) as valley_total_p_at_ee,\n" +
                    "        SUM(tt.flat_total_p_at_ee) as flat_total_p_at_ee\n" +
                    "        from\n" +
                    "         (SELECT * from kxtimingdata.collect_total_p_at_ee_day where (\n" +
                    "         "+sb.toString()+"\n" +
                    "         ) and collect_date BETWEEN DATE_ADD(curdate(),interval -day(curdate())+1 day) + interval 0 MONTH\n" +
                    "         AND DATE_ADD(curdate(),interval -day(curdate())+1 day) + interval 1 MONTH\n" +
                    "        ) tt  group by date_format(tt.collect_date,'%y-%m-%d')";

            List<Map<String, Object>> dataList= jdbcTemplate.queryForList(sql);
            if(dataList.size()>0){
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
                    if(entry.getKey().equals("diff_total_p_at_ee")) {
                        if (entry.getValue() == null) {
                            data1.add("0");
                        }else {
                            data1.add(df.format(entry.getValue()).toString());
                        }
                    }
                    if(entry.getKey().equals("peak_total_p_at_ee")) {
                        if (entry.getValue() == null) {
                            data2.add("0");
                        }else {
                            data2.add(df.format(entry.getValue()).toString());
                        }

                    }
                    if(entry.getKey().equals("valley_total_p_at_ee")) {
                        if (entry.getValue() == null) {
                            data3.add("0");
                        }else {
                            data3.add(df.format(entry.getValue()).toString());
                        }
                    }
                    if(entry.getKey().equals("flat_total_p_at_ee")) {
                        if (entry.getValue() == null) {
                            data4.add("0");
                        }else {
                            data4.add(df.format(entry.getValue()).toString());
                        }
                    }
                }
            }
            //总 峰 平 谷 平均电度电价
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
        }else {
            jsonDatas=null;
        }
        return jsonDatas;

    }
    /**
     * 获取某个用户当月总、峰、平、谷等数据
     * @param meterconfig
     * @return
     */
    public List<EchartsData> viewListByMonthMoney(EquipmentMeterConfig meterconfig){
        List<EquipmentMeterConfig> meterconfigList =getMeterConfigList(meterconfig);
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
        if(meterconfigList.size()>0) {
            //查询三相电流
            for (EquipmentMeterConfig config : meterconfigList) {
                if (sb.length() > 1) {
                    sb.append(" or (dtu_no='" + config.getDtuNo() + "' and meter_address='" + config.getMeterAddress() + "')");
                } else {
                    sb.append("(dtu_no='" + config.getDtuNo() + "' and meter_address='" + config.getMeterAddress() + "')");
                }
            }

        String sql="SELECT date_format(tt.collect_date,'%y-%m-%d')  as collect_date,\n" +
                "        SUM(tt.diff_total_p_at_ee) as diff_total_p_at_ee,\n" +
                "        SUM(tt.peak_total_p_at_ee) as peak_total_p_at_ee,\n" +
                "        SUM(tt.valley_total_p_at_ee) as valley_total_p_at_ee,\n" +
                "        SUM(tt.flat_total_p_at_ee) as flat_total_p_at_ee\n" +
                "        from\n" +
                "         (SELECT * from kxtimingdata.collect_total_p_at_ee_day where (\n" +
                "         "+sb.toString()+"\n" +
                "         ) and collect_date BETWEEN DATE_ADD(curdate(),interval -day(curdate())+1 day) + interval 0 MONTH\n" +
                "         AND DATE_ADD(curdate(),interval -day(curdate())+1 day) + interval 1 MONTH\n" +
                "        ) tt  group by date_format(tt.collect_date,'%y-%m-%d')";
        List<Map<String, Object>> dataList= jdbcTemplate.queryForList(sql);
        if(dataList.size()>0){
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> map = dataList.get(i);
            SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
            Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
            double sum=0;
            while (entries.hasNext()) {
                Map.Entry<String, Object> entry = entries.next();
                if (entry.getKey().equals("collect_date")) {
                    if (entry.getValue() == null) {
                        xName.add("0");
                    } else {
                        xName.add(entry.getValue().toString());
                    }
                }
                if(entry.getKey().equals("peak_total_p_at_ee")) {
                    if (entry.getValue() == null) {
                        sum=sum+0;
                        data2.add("0");
                    }else {

                       double  peak=Double.parseDouble(df.format((Double.parseDouble(entry.getValue().toString())) * ElecPrice.PEAK_PRICE));
                        data2.add(String.valueOf(peak));
                        sum=sum+peak;
                    }

                }
                if(entry.getKey().equals("valley_total_p_at_ee")) {
                    if (entry.getValue() == null) {
                        sum=sum+0;
                        data3.add("0");
                    }else {

                        double  flat=Double.parseDouble(df.format((Double.parseDouble(entry.getValue().toString())) * ElecPrice.VALLEY_PRICE));
                        sum=sum+flat;
                        data3.add(String.valueOf(flat));
                    }
                }
                if(entry.getKey().equals("flat_total_p_at_ee")) {
                    if (entry.getValue() == null) {
                        sum=sum+0;
                        data4.add("0");
                    }else {
                        double  thevlley=Double.parseDouble(df.format((Double.parseDouble(entry.getValue().toString())) * ElecPrice.FLAT_PRICE));
                        sum=sum+thevlley;
                        data4.add(String.valueOf(thevlley));
                    }
                }

            }
            data1.add(String.valueOf(df.format(sum)));
        }
        //总 峰 平 谷 平均电度电价
        jsonDat1.setName("总电费");
        jsonDat1.setData(data1);
        jsonDat1.setXcontent(xName);
        jsonDat2.setName("峰值电费");
        jsonDat2.setData(data2);
        jsonDat2.setXcontent(xName);
        jsonDat3.setName("平值电费");
        jsonDat3.setData(data3);
        jsonDat3.setXcontent(xName);
        jsonDat4.setName("谷值电费");
        jsonDat4.setData(data4);
        jsonDat4.setXcontent(xName);
        jsonDatas.add(jsonDat1);
        jsonDatas.add(jsonDat2);
        jsonDatas.add(jsonDat3);
        jsonDatas.add(jsonDat4);
        }else {
            jsonDatas=null;
        }
        }else {
            jsonDatas=null;
        }
        return jsonDatas;
    }
    public List<EquipmentMeterConfig> seldmByCollectConfigId(EquipmentMeterConfig meterconfig){
        List<EquipmentMeterConfig>  dtumeter=equipmentMeterConfigDao.getDtoMeterAddress(meterconfig);
        return dtumeter;
    }

    public String  SetOption(List<EchartsData> jsonDatas){
        StringBuffer option=new StringBuffer();
        StringBuffer option1=new StringBuffer();
        StringBuffer option2=new StringBuffer();
        StringBuffer option3=new StringBuffer();
        if(jsonDatas.size()>0){
            option.append("{tooltip : {trigger: 'axis',axisPointer : {type : 'shadow'}},legend: {data: [");
            for(int i=0;i<jsonDatas.size();i++){
                if (jsonDatas.size()==1){
                    option1.append("'"+jsonDatas.get(i).getName()+"'");
                }else {
                    if(i<jsonDatas.size()-1){
                        option1.append("'"+jsonDatas.get(i).getName()+ "',");
                    }else{
                        option1.append("'"+jsonDatas.get(i).getName()+"'");
                    }
                }
            }
            for(int i=0;i<jsonDatas.get(0).getXcontent().size();i++){
                if (jsonDatas.get(0).getXcontent().size()==1){
                    option2.append("'"+jsonDatas.get(0).getXcontent().get(i)+"'");
                }else {
                    String dayTime = jsonDatas.get(0).getXcontent().get(i);
                    if(i<jsonDatas.get(0).getXcontent().size()-1){
                        option2.append("'"+dayTime.substring(dayTime.lastIndexOf("-")+1)+ "',");

                    }else{
                        option2.append("'"+dayTime.substring(dayTime.lastIndexOf("-")+1)+"'");
                    }
                }
            }
            for(int i=0;i<jsonDatas.size();i++) {
                if(i==0){
                    option3.append("{name:'" + jsonDatas.get(i).getName() + "',type: 'line',stack:'', label: {normal: {position: 'insideRight'}},data: [");
                }else {
                    option3.append("{name:'" + jsonDatas.get(i).getName() + "',type: 'bar',stack:'总量', label: {normal: {position: 'insideRight'}},data: [");
                }

                for (int j = 0; j < jsonDatas.get(i).getData().size(); j++) {
                    if (jsonDatas.get(i).getData().size() == 1) {
                        option3.append("" + jsonDatas.get(i).getData().get(j) + "");
                    } else {
                        if (j < jsonDatas.get(i).getData().size() - 1) {
                            option3.append("" + jsonDatas.get(i).getData().get(j) + ",");
                        } else {
                            option3.append("" + jsonDatas.get(i).getData().get(j) + "");
                        }
                    }
                }

                option3.append("]},");
            }
            String subOption3 = option3.substring(0, option3.length()-1);
            option.append(option1).append("]},grid: { left: '3%',right: '4%',bottom: '3%',containLabel: true},yAxis:  {type: 'value'}," +
                    " xAxis: {type: 'category',data: [").append(option2).append("] },series: [");
            option.append(subOption3).append("]}");

        }else{
            option.append("{tooltip: {trigger: 'axis',axisPointer: {type: 'shadow'}},legend: {data: ['总','峰','平','谷']},grid: {left: '3%',right: '4%',bottom: '3%',containLabel: true},yAxis: {type: 'value'},xAxis: {type: 'category',data: []},series: []}");
        }
        String  optionList = option.toString().replaceAll(" +","");
        return optionList.trim();
    }

}


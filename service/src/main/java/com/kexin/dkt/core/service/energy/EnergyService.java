package com.kexin.dkt.core.service.energy;

import com.kexin.dkt.core.common.constant.ElecPrice;
import com.kexin.dkt.core.common.echarts.EchartsUtils;
import com.kexin.dkt.core.common.util.*;
import com.kexin.dkt.core.dao.customer.CustomerDao;
import com.kexin.dkt.core.dao.energy.EnergyDao;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.echarts.EchartsSeries;
import com.kexin.dkt.core.entity.echarts.sale.SaleElectricityDo;
import com.kexin.dkt.core.entity.vo.EchartsData;
import com.kexin.dkt.core.entity.vo.ParamDate;
import com.kexin.dkt.core.entity.vo.ElecQuantity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by kexin on 2017/10/24.
 */
@Slf4j
@Service
public class EnergyService {


    @Autowired
    private EnergyDao energyDao;

    @Autowired
    private CustomerDao customerDao;

    /**
     * 获取当前监控设备数量
     * @param userId
     * @return
     */
    public int findEquipmentCount(String userId) {
        List<CustCustomer> custlist= customerDao.findCustomerIdAndMeth(userId);
        Integer equipmentCount = 0;
        if(!ValidationUtil.isEmpty(custlist)) {
            CustCustomer customer = custlist.get(0);
            equipmentCount = energyDao.findEquipmentCount(customer.getCustomerId());
            if(equipmentCount == null){
                equipmentCount = 0;
            }
        }
        return equipmentCount;
    }

    /**
     * 获取公司名称
     * @param userId
     * @return
     */
    public String findCustomerName(String userId ) {
        List<CustCustomer> custlist= customerDao.findCustomerIdAndMeth(userId);
        if(!ValidationUtil.isEmpty(custlist)) {
            CustCustomer customer = custlist.get(0);
            return energyDao.findCustomerName(customer.getCustomerId());
        }
        return null;
    }


    /**
     * 获取当天电量
     * @param paramDate
     * @return
     */
    public ElecQuantity findEnergyByDay(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        return energyDao.findEnergyByDay(paramDate);
    }


    /**
     * 获取当天电费
     * @param paramDate
     * @return
     */
    public double findElecPriceByDay(ParamDate paramDate) {
        double sumElecPriceByDay = 0.0;
        ElecQuantity elecQuantity = energyDao.findEnergyByDay(paramDate);
        if (elecQuantity != null) {
            //天峰值用电
            double dayPeakcost = elecQuantity.getPeakElectric();
            //天谷值用电
            double dayValleycost = elecQuantity.getValleyElectric();
            //天平值用电
            double dayFlatcost = elecQuantity.getFlatElectric();
            sumElecPriceByDay = dayPeakcost+dayValleycost+dayFlatcost;
        }
        return CalculateUtils.roundNumber(sumElecPriceByDay);
    }




    /**
     * 获取当月用电的峰谷平
     * @param paramDate
     * @return
     */
    public ElecQuantity findAllEnergyByMonth(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        return energyDao.findAllEnergyByMonth(paramDate);
    }

    /**
     * 获取上月电费
     * @param paramDate
     * @return
     */
    public double findElecPriceByPreMonth(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
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

        //上月电费
        double sumElecPriceByPreMonth = 0.0;

        ParamDate preDate = new ParamDate();
        preDate.setFirstDate(PreFirstDay);
        preDate.setEndDate(preLastDay);
        preDate.setLpId(paramDate.getLpId());
        preDate.setGenre(paramDate.getGenre());
        preDate.setCustomerId(paramDate.getCustomerId());
        ElecQuantity elecQuantityByPreMonth = energyDao.findAllEnergyByMonth(preDate);
        if (elecQuantityByPreMonth != null) {
            //月峰值用电
            double monthPeakcost = elecQuantityByPreMonth.getPeakElectric();
            //月谷值用电
            double monthValleycost = elecQuantityByPreMonth.getValleyElectric();
            //月平值用电
            double monthFlatcost = elecQuantityByPreMonth.getFlatElectric();
            sumElecPriceByPreMonth = monthPeakcost * ElecPrice.PEAK_PRICE + monthValleycost * ElecPrice.VALLEY_PRICE + monthFlatcost * ElecPrice.FLAT_PRICE;
        }
        return CalculateUtils.roundNumber(sumElecPriceByPreMonth);
    }

    /**
     * 根据时间段获取当月电费
     * @param paramDate
     * @return
     */
    public double findAllElecPriceByTime(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        //当月电费findAllElecPriceByTime
        double sumElecPriceByMonth = 0.0;
        ElecQuantity elecQuantityByMonth = energyDao.findAllEnergyByMonth(paramDate);
        if (elecQuantityByMonth != null) {
            double energyByMonth = elecQuantityByMonth.getSumElectric();
            //月峰值用电
            double monthPeakcost = elecQuantityByMonth.getPeakElectric();
            //月谷值用电
            double monthValleycost = elecQuantityByMonth.getValleyElectric();
            //月平值用电
            double monthFlatcost = elecQuantityByMonth.getFlatElectric();
            sumElecPriceByMonth = monthPeakcost * ElecPrice.PEAK_PRICE + monthValleycost * ElecPrice.VALLEY_PRICE + monthFlatcost * ElecPrice.FLAT_PRICE;
        }

        return sumElecPriceByMonth;
    }


    /*获取一个客户一月中每天的峰谷平*/
    public List<ElecQuantity> findEnergyByDayList(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        return energyDao.findEnergyByDayList(paramDate);
    }


    /*综合平均电价*/
    public double getAvgElecPrice(ParamDate paramDate){
        paramDate = this.convertParamDate(paramDate);
        double avgElecPrice = 0.0;
        //获取当月用电量
        ElecQuantity elecQuantityByTime = energyDao.findAllEnergyByMonth(paramDate);
        //当月电费
        double sumElecPriceByTime = findAllElecPriceByTime(paramDate);
        double energyByMonth = 0.0;
        if(elecQuantityByTime !=null){
            //当月用电量
            energyByMonth = elecQuantityByTime.getSumElectric();
        }
        if (energyByMonth != 0.0) {
            avgElecPrice = sumElecPriceByTime / energyByMonth;
        }
        return avgElecPrice;
    }

    /*获取时间和电费类型分类饼状图*/
    public String getTimeTypeOption(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        List<Object> dataList = new ArrayList<Object>();
        List<String> nameList = new ArrayList<String>();
        Map<String, Object> peakMap = new HashMap<String, Object>();
        Map<String, Object> valleyMap = new HashMap<String, Object>();
        Map<String, Object> flatMap = new HashMap<String, Object>();
        ElecQuantity elecQuantityByTime = this.findAllEnergyByMonth(paramDate);
        String titleName = "";
        if("typeCost".equals(paramDate.getTabName())) {
            //电费构成-电度电费
            double sumElecPriceByTime = this.findAllElecPriceByTime(paramDate);
            peakMap.put("value", CalculateUtils.roundNumber(sumElecPriceByTime));
            peakMap.put("name", "电度电费");
            nameList.add("电度电费");
            dataList.add(peakMap);
            //电费构成-基本电费
            //变压器容量
            int transformerCapacity = energyDao.getTransCapacity(paramDate);
            //基本电费
            double basicElecCost = BasicElecPrice.getBasicElecPrice(transformerCapacity);

            valleyMap.put("value", CalculateUtils.roundNumber(basicElecCost));
            valleyMap.put("name", "基本电费");
            nameList.add("基本电费");
            dataList.add(valleyMap);
            //电费构成-附加电费
            flatMap.put("value", CalculateUtils.roundNumber(2000));
            flatMap.put("name", "附加电费");
            titleName = "电费构成";
            nameList.add("附加电费");
            dataList.add(flatMap);
        }else {
            double peakElec = 0.0;
            double valleyElec = 0.0;
            double flatElec = 0.0;
            if(elecQuantityByTime != null){
                //月峰值用电
                peakElec = elecQuantityByTime.getPeakElectric();
                //月谷值用电
                valleyElec = elecQuantityByTime.getValleyElectric();
                //月平值用电
                flatElec = elecQuantityByTime.getFlatElectric();
            }
            if ("elecQuantity".equals(paramDate.getTabName())) {
                peakMap.put("name", "峰时电量");
                valleyMap.put("name", "谷时电量");
                flatMap.put("name", "平时电量");
                nameList.add("峰时电量");
                nameList.add("谷时电量");
                nameList.add("平时电量");
                titleName = "分时电量";
                if (elecQuantityByTime != null) {
                    //分时电量-峰时电量
                    peakMap.put("value", CalculateUtils.roundNumber(peakElec));
                    //分时电量-谷时电量
                    valleyMap.put("value", CalculateUtils.roundNumber(valleyElec));
                    //分时电量-平时电量
                    flatMap.put("value", CalculateUtils.roundNumber(flatElec));
                }else{
                    //分时电量-峰时电量
                    peakMap.put("value", 0.00);
                    //分时电量-谷时电量
                    valleyMap.put("value", 0.00);
                    //分时电量-平时电量
                    flatMap.put("value", 0.00);
                }
                dataList.add(peakMap);
                dataList.add(flatMap);
                dataList.add(valleyMap);
            } else if ("elecCost".equals(paramDate.getTabName())) {
                peakMap.put("name", "峰时电费");
                nameList.add("峰时电费");
                valleyMap.put("name", "谷时电费");
                nameList.add("谷时电费");
                flatMap.put("name", "平时电费");
                nameList.add("平时电费");
                titleName = "分时电费";
                if (elecQuantityByTime != null) {
                    //分时电费-峰时电费
                    peakMap.put("value", CalculateUtils.roundNumber(peakElec * ElecPrice.PEAK_PRICE));
                    //分时电费-谷时电费
                    valleyMap.put("value", CalculateUtils.roundNumber(valleyElec * ElecPrice.VALLEY_PRICE));
                    //分时电费-平时电费
                    flatMap.put("value", CalculateUtils.roundNumber(flatElec * ElecPrice.FLAT_PRICE));
                }else{
                    //分时电费-峰时电费
                    peakMap.put("value", 0.00);
                    //分时电费-谷时电费
                    valleyMap.put("value", 0.00);
                    //分时电费-平时电费
                    flatMap.put("value", 0.00);
                }
                dataList.add(peakMap);
                dataList.add(valleyMap);
                dataList.add(flatMap);
            }
        }
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0;i<nameList.size();i++){
            String temp = "'"+ nameList.get(i) +"',";
            if(i == nameList.size()-1){
                temp = "'"+nameList.get(i)+"'";
            }
            sb1.append(temp);
        }

        for (int i = 0;i<dataList.size();i++){
            Map<String,Object> tempMap = (Map)dataList.get(i);
            String temp = "{value:"+ tempMap.get("value") +","+"name:'"+tempMap.get("name")+"'},";
            if(i == nameList.size()-1){
                temp = "{value:'"+ tempMap.get("value") +"',"+"name:'"+tempMap.get("name")+"'}";
            }
            sb2.append(temp);
        }
        String option = "{" +
                "    title : {" +
                "        x:'center'" +
                "    }," +
                "    tooltip : {" +
                "        trigger: 'item'" +
                "    }," +
                "    legend: {" +
                "        orient: 'horizontal'," +
                "        left: 'center'," +
                "        data: ["+sb1.toString()+"]" +
                "    }," +
                "    yAxis:  {" +
                "        show: false," +
                "    }," +
                "    xAxis: {" +
                "        show: false," +
                "    }," +
                "    series : [" +
                "        {" +
                "            name: '"+titleName+"'," +
                "            type: 'pie'," +
                "            radius : '55%'," +
                "            center: ['50%', '60%']," +
                "            data:["+sb2.toString()+"]," +
                "            itemStyle: {" +
                "                emphasis: {" +
                "                    shadowBlur: 10," +
                "                    shadowOffsetX: 0" +
                "                }" +
                "            }" +
                "        }" +
                "    ]" +
                "}";
        return option;
    }


    /*获取能效指数折线图*/
    public String getEnergyIndexOption(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        List<Object> dataList = new ArrayList<Object>();
        List<Object> dateList = new ArrayList<Object>();
        //获取一个客户一月中每天的峰谷平
        List<ElecQuantity> elecQuantityList = this.findEnergyByDayList(paramDate);
        if(elecQuantityList !=null && elecQuantityList.size()!=0) {
            for (ElecQuantity elecQuantity : elecQuantityList) {
                //时间段的用电量
                double sumDayElectric = 0.0;
                double sumDayElecCost = 0.0;
                if (elecQuantity != null) {
                    //月峰值用电
                    double dayPeakcost = elecQuantity.getPeakElectric();
                    //月谷值用电
                    double dayValleycost = elecQuantity.getValleyElectric();
                    //月平值用电
                    double dayFlatcost = elecQuantity.getFlatElectric();
                    //时间段的总电费
                    sumDayElecCost = dayPeakcost * ElecPrice.PEAK_PRICE + dayValleycost * ElecPrice.VALLEY_PRICE + dayFlatcost * ElecPrice.FLAT_PRICE;
                }
                //每天的综合平均电价

                double avgElecPrice = 0.0;
                if (sumDayElectric != 0.0)
                    avgElecPrice = sumDayElecCost/sumDayElectric;

                double totalElecPrice = ElecPrice.TOTAL_PRICE;
                //能效指数
                long energyIndex = Math.round(100 - (avgElecPrice - (totalElecPrice / 3)) / 0.02);
                energyIndex = energyIndex>100?100:energyIndex;
                //日期
                String dateTime = DateUtils.date2Str(elecQuantity.getCollectDate(), "dd");
                dateList.add(dateTime);
                dataList.add(energyIndex);
            }
        }
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        for (int i = 0;i<dateList.size();i++){
            String temp = "'"+ dateList.get(i) +"',";
            if(i == dateList.size()-1){
                temp = "'"+dateList.get(i)+"'";
            }
            sb3.append(temp);
        }
        for (int i = 0;i<dataList.size();i++){
            String temp = dataList.get(i) +",";
            if(i == dataList.size()-1){
                temp = dataList.get(i)+"";
            }
            sb4.append(temp);
        }
        String option = "{" +
                "    tooltip : {" +
                "        trigger: 'axis'," +
                "        axisPointer : {" +
                "            type : 'line'" +
                "        }" +
                "    }," +
                "    legend: {" +
                "        data: ['能效指数']" +
                "    }," +
                "    grid: {" +
                "        left: '3%'," +
                "        right: '4%'," +
                "        bottom: '3%'," +
                "        containLabel: true" +
                "    }," +
                "    yAxis:  {" +
                "        show: true," +
                "        type: 'value'," +
                "        name:'能效指数'" +
                "    }," +
                "    xAxis: {" +
                "        show: true," +
                "        type: 'category'," +
                "        data: ["+sb3.toString()+"]," +
                "        name:'日期'" +
                "    }," +
                "    series: [" +
                "        {" +
                "            name: '能效指数'," +
                "            type: 'line'," +
                "            stack: '总量'," +
                "            label: {" +
                "                normal: {" +
                "                    show: true," +
                "                    position: 'insideRight'" +
                "                }" +
                "            }," +
                "            data: ["+sb4.toString()+"]" +
                "        }" +
                "    ]" +
                "}";
        return option;
    }

    /*获取k指数*/
    public double getKIndex(ParamDate paramDate){

        //能效指数
        long energyIndex = this.getEnergyIndex(paramDate);

        //用电成本k指数
        double kIndex = GetK.getKByScore(energyIndex);
        return CalculateUtils.get2PointValue(kIndex);
    }

    /*获取能效指数*/
    public long getEnergyIndex(ParamDate paramDate){

        //综合平均电价
        double avgElecPrice = this.getAvgElecPrice(paramDate);

        //峰谷平电价总和
        double totalElecPrice = ElecPrice.PEAK_PRICE+ElecPrice.VALLEY_PRICE+ElecPrice.FLAT_PRICE;
        //能效指数
        long energyIndex = Math.round(100-(avgElecPrice-(totalElecPrice/3))/0.02);
        if(energyIndex >100){
            energyIndex = 100;
        }
        return energyIndex;
    }

    /*获取月度负荷折线图*/
    public String getLoadOption(ParamDate paramDate,String tabName){
        paramDate = this.convertParamDate(paramDate);
        List<Object> dataList = new ArrayList<Object>();
        List<Object> dateList = new ArrayList<Object>();
        //获取一个客户一月中每天小时最大电量
        List<ElecQuantity> elecQuantityList = this.findMaxEnergyByDayList(paramDate);
        if(elecQuantityList !=null && elecQuantityList.size()!=0) {
            for (ElecQuantity elecQuantity : elecQuantityList) {
                double maxElectric = 0.0;
                String dateTime = "";
                if(elecQuantity !=null){
                    //一天小时的用电量最大值
                    maxElectric = elecQuantity.getMaxTotal();
                    //获取每天时间值
                    dateTime = elecQuantity.getDateStr().substring(elecQuantity.getDateStr().lastIndexOf("-")+1);
                }
                //获取变压器容量
                Integer transformerCapacity = energyDao.getTransCapacity(paramDate);
                if(transformerCapacity ==null){
                    transformerCapacity = 0;
                }

                //负荷率
                double loadProb = 0;
                if(transformerCapacity != 0){
                    loadProb = CalculateUtils.get2PointValue(maxElectric / transformerCapacity * 100);
                }
                dateList.add(dateTime);
                dataList.add(loadProb);
            }
        }
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        for (int i = 0;i<dateList.size();i++){
            String temp = "'"+ dateList.get(i) +"',";
            if(i == dateList.size()-1){
                temp = "'"+dateList.get(i)+"'";
            }
            sb3.append(temp);
        }
        for (int i = 0;i<dataList.size();i++){
            String temp = dataList.get(i) +",";
            if(i == dataList.size()-1){
                temp = dataList.get(i)+"";
            }
            sb4.append(temp);
        }
        String option = "{" +
                "    tooltip : {" +
                "        trigger: 'axis'," +
                "        axisPointer : {" +
                "            type : 'line'" +
                "        }" +
                "    }," +
                "    legend: {" +
                "        data: ['负荷率']" +
                "    }," +
                "    grid: {" +
                "        left: '3%'," +
                "        right: '4%'," +
                "        bottom: '3%'," +
                "        containLabel: true" +
                "    }," +
                "    yAxis:  {" +
                "        show: true," +
                "        type: 'value'," +
                "        name:'负荷率/%'" +
                "    }," +
                "    xAxis: {" +
                "        show: true," +
                "        type: 'category'," +
                "        data: ["+sb3.toString()+"]," +
                "        name:'日期'" +
                "    }," +
                "    series: [" +
                "        {" +
                "            name: '负荷率'," +
                "            type: 'line'," +
                "            stack: '总量'," +
                "            label: {" +
                "                normal: {" +
                "                    show: false," +
                "                    position: 'insideRight'" +
                "                }" +
                "            }," +
                "            data: ["+sb4.toString()+"]" +
                "        }" +
                "    ]" +
                "}";
        return option;
    }

    /*获取每月中每天最大小时用电量的集合*/
    private List<ElecQuantity> findMaxEnergyByDayList(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        return energyDao.findMaxEnergyByDayList(paramDate);
    }

    /*获取月最大负载值*/
    public double getMaxLoad(ParamDate paramDate){
        paramDate = this.convertParamDate(paramDate);
        Double maxLoadValue = energyDao.getMaxLoad(paramDate);
        return maxLoadValue !=null ? maxLoadValue :0.0;
    }

    /*获取月最小负载值*/
    public double getMinLoad(ParamDate paramDate){
        paramDate = this.convertParamDate(paramDate);
        Double minLoadValue = energyDao.getMinLoad(paramDate);
        return minLoadValue !=null ? minLoadValue :0.0;
    }

    /*获取变压器容量*/
    public int getTransCapacity(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        Integer transCapacity = energyDao.getTransCapacity(paramDate);
        return transCapacity !=null ? transCapacity : 0;
    }

    /*获取月平均负载值*/
    public double getAvgLoad(ParamDate paramDate) {
        paramDate = this.convertParamDate(paramDate);
        Double avgLoadValue = energyDao.getAvgLoad(paramDate);
        return avgLoadValue !=null ? avgLoadValue :0.0;
    }

    /*购电指数柱状图*/
    public String getSellIndexOption(ParamDate paramDate, double buyEnergy) {
        paramDate = this.convertParamDate(paramDate);
        //定义map集合用于初始化时间值
        Map<String,Double> map = new TreeMap<String, Double>();
        //图表标题
        String title = "";
        //统计数据类型标题数组
        String[] dataTypeArray = {"合理量","购电量","历史参考","用电量"};
        String [] color = {"#FCDBC0", "#FF0000","#D9D9D9","#00B0F0"};
        //每月时间数组
        List<ElecQuantity> dateList = null;
        if(paramDate.getEndDate().equals(DateUtils.date2Str(new Date(),"yyyy-MM-dd"))){
            ParamDate newParamDate = new ParamDate();
            //获取当月的最后一天日期
            Calendar calendar = Calendar.getInstance();
            Date date = DateUtils.converStrTimeToDate(paramDate.getEndDate());
            calendar.setTime(date);
            calendar.set(calendar.MONTH, (calendar.get(calendar.MONTH) + 1));
            calendar.set(calendar.DAY_OF_MONTH, 0);
            Date lastDayOfMonth = calendar.getTime();
            newParamDate.setFirstDate(paramDate.getFirstDate());
            newParamDate.setLpId(paramDate.getLpId());
            newParamDate.setTabName(paramDate.getTabName());
            newParamDate.setGenre(paramDate.getGenre());
            newParamDate.setCustomerId(paramDate.getCustomerId());
            newParamDate.setEndDate(DateUtils.date2Str(lastDayOfMonth,"yyyy-MM-dd"));
            dateList = energyDao.getMonthTimes(newParamDate);
        }else {
            dateList = energyDao.getMonthTimes(paramDate);
        }
        String[] monthTimeArray = new String[dateList.size()];
        for(int i = 0;i<monthTimeArray.length;i++){
            monthTimeArray[i] = DateUtils.date2Str(dateList.get(i).getCollectDate(),"dd");
        }
        for(String time : monthTimeArray){
            map.put(time,0.0);
        }
        //获取series中数据集合
        List<EchartsSeries> list = this.getSeriesList(paramDate, dataTypeArray, map, buyEnergy);


        String option = EchartsUtils.getOptionManyLine(title,color,dataTypeArray,monthTimeArray,list);

        return option;
    }

    /*获取series中数据集合*/
    private List<EchartsSeries> getSeriesList(ParamDate paramDate, String[] dataTypeArray, Map<String,Double> map, double buyEnergy) {


        Map<String,Double> userEnergyMap = new TreeMap<String, Double>();
        Map<String,Double> rationalEnergyMap =  new TreeMap<String, Double>();
        Map<String,Double> buyEnergyMap =  new TreeMap<String, Double>();
        userEnergyMap.putAll(map);
        rationalEnergyMap.putAll(map);
        buyEnergyMap.putAll(map);
        //半年的平均数据
        Map<String,Double> referenceEnergyMap =  new TreeMap<String, Double>();
        Map<String,Double> tempReferenceEnergyMap =   new TreeMap<String, Double>();
        tempReferenceEnergyMap.putAll(map);
        List<Object> userEnergyList = new ArrayList<Object>();
        List<Object> rationalEnergyList = new ArrayList<Object>();
        List<Object>  referenceEnergyList = new ArrayList<Object>();
        List<EchartsSeries> list = new ArrayList<EchartsSeries>();
        EchartsSeries useEnergySeries = new EchartsSeries();
        EchartsSeries rationalEnergySeries = new EchartsSeries();
        EchartsSeries referenceEnergySeries = new EchartsSeries();
        EchartsSeries buyEnergySeries = new EchartsSeries();

        ParamDate paramDate1 = new ParamDate();
        //开始时间
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(DateUtils.str2Date(paramDate.getFirstDate(),"yyyy-MM-dd"));
        calendar1.set(Calendar.MONTH,calendar1.get(Calendar.MONTH)-6);
        paramDate1.setFirstDate(DateUtils.date2Str(calendar1.getTime(),"yyyy-MM-dd"));
        paramDate1.setLpId(paramDate.getLpId());
        paramDate1.setTabName(paramDate.getTabName());
        paramDate1.setGenre(paramDate.getGenre());
        paramDate1.setCustomerId(paramDate.getCustomerId());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(DateUtils.str2Date(paramDate.getFirstDate(),"yyyy-MM-dd"));
        calendar2.set(Calendar.DAY_OF_MONTH,0);
        paramDate1.setEndDate(DateUtils.date2Str(calendar2.getTime(),"yyyy-MM-dd"));
        Map<String,Integer> countMap = new HashMap<String, Integer>();

        //获取每天用电量递增值
        List<ElecQuantity> elecQuantityList = energyDao.findEnergyByDayList(paramDate);
        if(elecQuantityList !=null && elecQuantityList.size()>0){
            double sum = 0.0;
            int count = 0;
            for(ElecQuantity elecQuantity : elecQuantityList){
                count ++;
                sum += CalculateUtils.get2PointValue(elecQuantity.getSumElectric());
                userEnergyMap.put(DateUtils.date2Str(elecQuantity.getCollectDate(),"dd"), CalculateUtils.roundNumber(sum));
                String tempStr = DateUtils.date2Str(new Date(),"dd");
                if(paramDate.getEndDate().equals(DateUtils.date2Str(new Date(),"dd"))){
                    if(count == Integer.parseInt(tempStr)){
                        break;
                    }
                }
            }
        }
        //使用temp临时变量存储不为0的值，如果map集合中有值为0，那么就覆盖它
        double temp = 0.0;
        int count = 0;
        for (Map.Entry<String,Double> entry :userEnergyMap.entrySet()){
            count ++;
            if(entry.getValue() !=0.0){
                temp = entry.getValue();
                userEnergyList.add(CalculateUtils.roundNumber(entry.getValue())+"");
            }else{
                String tempStr = DateUtils.date2Str(new Date(),"dd");
                    if(count == Integer.parseInt(tempStr)){
                        break;
                    }
                userEnergyList.add(CalculateUtils.roundNumber(temp) + "");
            }
        }

        String[] useEnergyArray = new String[userEnergyList.size()];
        useEnergySeries.setName(dataTypeArray[3]);
        useEnergySeries.setData(userEnergyList.toArray(useEnergyArray));
        useEnergySeries.setType("line");

        //当月最后一天
        Calendar calendar = Calendar.getInstance();
        Date date = DateUtils.converStrTimeToDate(paramDate.getEndDate());
        calendar.setTime(date);
        calendar.set(calendar.MONTH, (calendar.get(calendar.MONTH) + 1));
        calendar.set(calendar.DAY_OF_MONTH, 0);
        int lastDayOfMonth = calendar.get(calendar.DAY_OF_MONTH);


        List<ElecQuantity> halfYearElecList = energyDao.findEnergyByDayList(paramDate1);

        if(halfYearElecList !=null && halfYearElecList.size()>0){
            out:for(ElecQuantity elecQuantity : halfYearElecList){
                //不包含2月份的数据统计
                if(!DateUtils.date2Str(elecQuantity.getCollectDate(), "MM").equals("02")) {
                    inner:for(Map.Entry<String, Double> entry :map.entrySet()) {
                        if (entry.getKey().equals(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd"))) {
                            if (countMap.get(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd")) == null) {
                                countMap.put(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd"), 1);
                            } else {
                                int temp1 = countMap.get(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd"));
                                countMap.put(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd"), temp1 + 1);
                            }
                            if (tempReferenceEnergyMap.get(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd")) == null) {
                                tempReferenceEnergyMap.put(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd"), CalculateUtils.roundNumber(CalculateUtils.get2PointValue(elecQuantity.getSumElectric())));
                            } else {
                                tempReferenceEnergyMap.put(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd"), CalculateUtils.roundNumber(CalculateUtils.get2PointValue(elecQuantity.getSumElectric() + tempReferenceEnergyMap.get(DateUtils.date2Str(elecQuantity.getCollectDate(), "dd")))));
                            }
                            break inner;
                        }
                    }
                }
            }

            double sum = 0.0;
            int count1 = 0;
            for(Map.Entry<String,Double> entry :buyEnergyMap.entrySet()){
                count1 ++;
                double temp3 = 0;
                if(countMap.get(entry.getKey()) != 0) {
                    temp3 = tempReferenceEnergyMap.get(entry.getKey()) / countMap.get(entry.getKey());
                }
                sum += temp3;
                if(countMap.get(entry.getKey()) == null) {
                    referenceEnergyMap.put(paramDate.getEndDate().substring(0, paramDate.getEndDate().lastIndexOf("-")) + "-" + entry.getKey(), sum );
                }else{
                    referenceEnergyMap.put(paramDate.getEndDate().substring(0, paramDate.getEndDate().lastIndexOf("-")) + "-" + entry.getKey(), sum );
                }
                String tempStr = DateUtils.date2Str(new Date(),"dd");
                if(paramDate.getEndDate().equals(DateUtils.date2Str(new Date(),"yyyy-MM-dd"))){
                    if(count1 == Integer.parseInt(tempStr)){
                        break;
                    }
                }
            }
            //使用temp临时变量存储不为0的值，如果map集合中有值为0，那么就覆盖它
            double temp2 = 0.0;
            int count2 = 0;
            for (Map.Entry<String,Double> entry :referenceEnergyMap.entrySet()){
                count2 ++;
                if(entry.getValue() !=0.0){
                    temp2 = entry.getValue();
                    referenceEnergyList.add(CalculateUtils.roundNumber(entry.getValue())+"");
                }else{
                    String tempStr = DateUtils.date2Str(new Date(),"dd");
                    if(DateUtils.date2Str(new Date(),"yyyy-MM-dd").equals(DateUtils.date2Str(new Date(),"yyyy-MM-dd"))){
                        if(count2 == Integer.parseInt(tempStr)){
                            break;
                        }
                    }
                    referenceEnergyList.add(CalculateUtils.roundNumber(temp2) + "");
                }
            }
        }

        //获取每天合理用电递增值
        double avgEnergy = buyEnergy/lastDayOfMonth;

        double sum = 0.0;
        for (Map.Entry<String,Double> entry :rationalEnergyMap.entrySet()){
            sum +=avgEnergy;
            rationalEnergyList.add(CalculateUtils.roundNumber(sum) + "");
        }
        String[] rationalEnergyArray = new String[rationalEnergyList.size()];
        rationalEnergySeries.setName(dataTypeArray[0]);
        rationalEnergySeries.setData(rationalEnergyList.toArray(rationalEnergyArray));
        rationalEnergySeries.setType("line");

        //购电量
        String[] buyEnergyArray = new String[buyEnergyMap.size()];
        for(int i = 0; i<buyEnergyArray.length; i++){
            buyEnergyArray[i] = CalculateUtils.roundNumber(buyEnergy) + "";
        }
        buyEnergySeries.setName(dataTypeArray[1]);
        buyEnergySeries.setData(buyEnergyArray);
        buyEnergySeries.setType("line");

        String[] referenceEnergyArray = new String[referenceEnergyList.size()];
        referenceEnergySeries.setName(dataTypeArray[2]);
        referenceEnergySeries.setData(referenceEnergyList.toArray(referenceEnergyArray));
        referenceEnergySeries.setType("line");


        list.add(rationalEnergySeries);
        list.add(buyEnergySeries);
        list.add(referenceEnergySeries);
        list.add(useEnergySeries);
        return list;
    }

    /*转换paramDate类*/
    public ParamDate convertParamDate(ParamDate paramDate){
        List<CustCustomer> custlist= customerDao.findCustomerIdAndMeth(paramDate.getUserId());
        if(!ValidationUtil.isEmpty(custlist)) {
            CustCustomer customer = custlist.get(0);
            paramDate.setCustomerId(customer.getCustomerId());
            if (Integer.parseInt(customer.getMeasureMethods())==ElecPrice.METERING_MODE_LOW) {
                paramDate.setGenre(1);
            } else if (Integer.parseInt(customer.getMeasureMethods())==ElecPrice.METERING_MODE_HIGH) {
                paramDate.setGenre(0);
            } else {
                paramDate.setGenre(2);
            }
        }
        return paramDate;
    }

    /*查询往前月的总电量*/
    public ElecQuantity findEnergyByBeforeMonth(ParamDate paramDate) {
       return energyDao.findEnergyByBeforeMonth(paramDate);
    }

    /*获取年峰谷平用电量*/
    public List<ElecQuantity> findEnergyByYear(SaleElectricityDo saleElectricityDo){
        return energyDao.findEnergyByYear(saleElectricityDo);
    }
}

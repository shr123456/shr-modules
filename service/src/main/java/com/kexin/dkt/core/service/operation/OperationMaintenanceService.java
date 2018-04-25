package com.kexin.dkt.core.service.operation;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kexin.dkt.core.common.echarts.EchartsUtils;
import com.kexin.dkt.core.common.util.*;
import com.kexin.dkt.core.common.util.StringUtils;
import com.kexin.dkt.core.dao.operation.OperationMaintenanceDao;
import com.kexin.dkt.core.dao.system.UserDao;
import com.kexin.dkt.core.entity.custcustomer.CustCustomer;
import com.kexin.dkt.core.entity.echarts.EchartsSeries;
import com.kexin.dkt.core.entity.file.SysAccessory;
import com.kexin.dkt.core.entity.operation.*;
import com.kexin.dkt.core.entity.security.SecUser;
import com.kexin.dkt.core.entity.vo.OrderVo;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.service.customer.CustomerService;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.codec.binary.Base64;



/**
 * Created by kexin on 2017/11/7.
 */
@Service
@Slf4j
public class OperationMaintenanceService {

    @Autowired
    private OperationMaintenanceDao operationMaintenanceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CustomerService customerService;


    String [] typeArray = {"维修","试验","保养","巡检"};

    int size = typeArray.length;

    @Value("${img_mkdir}")
    //图片路径
    private String imgPath;

    @Value("${pdf_mkdir}")
    //pdf图片路径
    private String pdfImgPath;

    @Value("${word_mkdir}")
    //pdf图片路径
    private String wordPath;

    @Value("${upload_hosts}")
    //访问路径前缀
    private String uploadHosts;

    @Value("${upload_path}")
    //绝对路径前缀
    private String uploadPath;

    @Value("${corporateSeal_path}")
    private String corporateSealPath;

    /*查询合同任务的状态(执行进度)*/
    public String getOperationMaintenanceOption(OrderVo orderVo) {
        int[] doneCount = this.findDoneCountArray(orderVo);
        long [] sumCount = this.findSumCountArray(orderVo);
        long max = 0;
        List<Object> list = new ArrayList<Object>();
        for(int i = 0;i<doneCount.length; i++){
            if(sumCount[i]>max){
                max = sumCount[i];
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name",doneCount[i]);
            map.put("value",sumCount[i]);
            list.add(map);
        }
        String option = "{" +
                "    color:['#ccc','#feac3d']," +
                "    tooltip: {" +
                "        show: false," +
                "        trigger: 'axis'," +
                "        axisPointer: {" +
                "            type: 'cross'," +
                "            crossStyle: {" +
                "                color: '#999'" +
                "            }" +
                "        }" +
                "    }," +
                "    legend: {" +
                "        selectedMode:false," +
                "        data:['剩余','已实施']," +
                "        z: 1," +
                "        bottom: 5" +
                "    }," +
                "    grid: [" +
                "        {x: '40', y: '7%', width: '80%', height: '80%'}," +
                "        {x: '40', y: '7%', width: '80%', height: '80%'}" +
                "    ]," +
                "    yAxis: [" +
                "        {" +
                "            gridIndex: 0," +
                "            type: 'category'," +
                "            data:"  + JSON.toJSONString(typeArray)+","+
                "            axisPointer: {" +
                "                type: 'shadow'" +
                "            }" +
                "        }," +
                "        {" +
                "            gridIndex: 1," +
                "            type: 'category'," +
                "            data: []," +
                "            axisPointer: {" +
                "                type: 'shadow'" +
                "            }" +
                "        }" +
                "    ]," +
                "    xAxis: [" +
                "        {" +
                "            show: false," +
                "            gridIndex: 0," +
                "            type: 'value'," +
                "            name: ''," +
                "            min: 0," +
                "            max: "+max+"," +
                "            interval: 50," +
                "            axisLabel: {" +
                "                formatter: '{value}'" +
                "            }" +
                "        }," +
                "        {" +
                "            show: false," +
                "            gridIndex: 1," +
                "            type: 'value'," +
                "            name: ''," +
                "            min: 0," +
                "            max: "+max+"," +
                "            interval: 50," +
                "            axisLabel: {" +
                "                formatter: '{value}'" +
                "            }" +
                "        }" +
                "    ]," +
                "    series: [" +
                "        {" +
                "            name:'剩余'," +
                "            xAxisIndex: 0," +
                "            yAxisIndex: 0," +
                "            type:'bar'," +
                "            showSymbol: false," +
                "            legendHoverLink: false," +
                "            data:"  + JSON.toJSONString(list)+","+
                "            label: {" +
                "                normal: {" +
                "                    show: true," +
                "                    position: 'right'," +
                "                    color: '#000'," +
                "                    fontSize: '14'," +
                "                    formatter: '{c}'" +
                "                }" +
                "            }," +
                "            z: 1" +
                "        }," +
                "        {" +
                "            name:'已实施'," +
                "            xAxisIndex: 1," +
                "            yAxisIndex: 1," +
                "            type:'bar'," +
                "            showSymbol: false," +
                "            legendHoverLink: false," +
                "            data:"  + JSON.toJSONString(doneCount)+","+
                "            z: 2" +
                "        }" +
                "    ]" +
                "}";

        return option;
    }

    /*查询客户项目中执行某种任务的执行次数*/
    public Integer findTaskCount(OrderVo orderVo){
        Integer taskCount =  operationMaintenanceDao.findTaskCount(orderVo);
        if(taskCount == null){
            taskCount = 0;
        }
    /*查询客户中服务类型的订单*/
    return taskCount;
}

    /*查询某客户中的所有项目*/
    public List<Map<String,Object>> findAllProject(Map<String,Object> paramMap){

        List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
        OrderVo orderVo = new OrderVo();
        orderVo.setCustomerId(paramMap.get("customerId").toString());
        List<OperationMaintenanceProject> projectList = operationMaintenanceDao.findAllProject(orderVo);
        if(projectList != null && projectList.size()>0) {
            for (OperationMaintenanceProject project : projectList) {
                Map<String,Object> resultMap = new HashMap<String,Object>();
                resultMap.put("project_name",project.getProjectName());
                resultMap.put("project_id",project.getProjectId());

                resultList.add(paramMap);
            }
        }
        return resultList;
    }

    /*根据项目id查询某项目*/
    public OperationMaintenanceProject findProjectById(int projectId){
        return operationMaintenanceDao.findProjectById(projectId);
    }

    public List<OperationMaintenanceOrder> findOrderByServiceType(OrderVo orderVo){
        return operationMaintenanceDao.findOrderByServiceType(orderVo);
    }

    public String [] findNextTimeArray(OrderVo orderVo){
        String[] nextTime = new String[size];
        nextTime[0] = "--";
        nextTime[1] = "--";
        nextTime[2] = "--";
        nextTime[3] = "--";

        //查询下一次巡检时间
        orderVo.setServiceType(1);
        orderVo.setOrderType(2);
        List<OperationMaintenanceOrder> inspectionOrderList = findOrderByServiceType(orderVo);
        if(inspectionOrderList !=null && inspectionOrderList.size()>0){
            Date inspectionTime = inspectionOrderList.get(0).getPlannedTime();
            if(inspectionTime.getTime() > new Date().getTime()){
                nextTime[0] = DateUtils.date2Str(inspectionTime,"yyyy-MM-dd");
            }
        }

        //查询下一次试验时间
        orderVo.setServiceType(2);
        List<OperationMaintenanceOrder> testOrderList = findOrderByServiceType(orderVo);
        if(testOrderList !=null && testOrderList.size()>0){
             Date testTime = testOrderList.get(0).getPlannedTime();
            if(testTime.getTime() > new Date().getTime()){
                nextTime[1] = DateUtils.date2Str(testTime,"yyyy-MM-dd");
            }
        }

        //查询下一次保养时间
        orderVo.setServiceType(3);
        List<OperationMaintenanceOrder> maintainOrderList = findOrderByServiceType(orderVo);
        if(maintainOrderList !=null && maintainOrderList.size()>0){
             Date maintainTime = maintainOrderList.get(0).getPlannedTime();
            if(maintainTime.getTime() > new Date().getTime()){
                nextTime[2] = DateUtils.date2Str(maintainTime,"yyyy-MM-dd");
            }
        }

        //查询下一次维修时间
        orderVo.setServiceType(4);
        List<OperationMaintenanceOrder> serviceOrderList = findOrderByServiceType(orderVo);
        if(serviceOrderList !=null && serviceOrderList.size()>0){
            Date serviceTime = serviceOrderList.get(0).getPlannedTime();
            if(serviceTime.getTime() > new Date().getTime()){
                nextTime[3] = DateUtils.date2Str(serviceTime,"yyyy-MM-dd");
            }
        }
        return nextTime;
    }

    /*查询各总任务数*/
    public long[] findSumCountArray(OrderVo orderVo){

        List<OperationMaintenanceProject> projectList = operationMaintenanceDao.findAllProject(orderVo);
        long[] sumCount = new long[size];
        //维修总次数
        orderVo.setServiceType(4);
        sumCount[0] = operationMaintenanceDao.findAllServiceCount(orderVo);
        if(projectList !=null && projectList.size() >0){
            for(OperationMaintenanceProject operationMaintenanceProject :projectList){
                if(operationMaintenanceProject !=null){
                    //试验总次数
                    sumCount[1] += operationMaintenanceProject.getTestCount()*operationMaintenanceProject.getLife();
                    //保养总次数
                    sumCount[2] += operationMaintenanceProject.getMaintainCount()*operationMaintenanceProject.getLife();
                    //巡检总次数
                    sumCount[3] += operationMaintenanceProject.getInspectionCount()*operationMaintenanceProject.getLife();
                }
            }
        }

        return sumCount;
    }


    /*查询已完成任务数*/
    public int[] findDoneCountArray(OrderVo orderVo){
        int[] doneCount = new int[size];
        //维修次数
        orderVo.setServiceType(4);
        doneCount[0] = operationMaintenanceDao.findTaskCount(orderVo);
        //试验次数
        orderVo.setServiceType(2);
        doneCount[1] = operationMaintenanceDao.findTaskCount(orderVo);
        //保养次数
        orderVo.setServiceType(3);
        doneCount[2] = operationMaintenanceDao.findTaskCount(orderVo);
        //巡检次数
        orderVo.setServiceType(1);
        doneCount[3] = operationMaintenanceDao.findTaskCount(orderVo);
        return doneCount;
    }

    /*返回封装数据的map集合*/
    public Map<String,Object> getMapData(OrderVo orderVo){
        Map<String,Object> map = new HashMap<String, Object>();
        long[] sumCountArray = findSumCountArray(orderVo);
        int[] doneCountArray = findDoneCountArray(orderVo);
        String[] nextTimeArray = findNextTimeArray(orderVo);
        ArrayUtils.reverse(sumCountArray);
        ArrayUtils.reverse(doneCountArray);
        map.put("sumCountArray", sumCountArray);
        map.put("doneCountArray", doneCountArray);
        map.put("nextTimeArray",nextTimeArray);
        return map;
    }

    /*根据类型查询订单列表*/
    public List<Object> queryOrderByType(OrderVo orderVo) {
        //用于封装[{title:'',data:''}]类型的数据
        List<Object> list = new ArrayList<Object>();

        //查询订单列表
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.queryOrderByType(orderVo);
        if(orderList !=null && orderList.size()>0){
            String timeType = "";
            //定义map集合，存储(title:'' data:'')
            Map<String,Object> map = null;
            List<OperationMaintenanceOrder> dataList = null;
            for(OperationMaintenanceOrder order : orderList){
                if(order !=null){
                    //设置工单类型与服务类型的名称
                    this.setOperationMaintenanceOrderType(order);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(order.getPlannedTime());
                    String planTime = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月";
                    if(!timeType.equals(planTime)){
                        timeType = planTime;
                        if(dataList !=null){
                            map.put("data",dataList);
                        }
                        if(map !=null){
                            list.add(map);
                        }
                        map = new HashMap<String, Object>();
                        dataList = new ArrayList<OperationMaintenanceOrder>();
                        dataList.add(order);
                        map.put("title",timeType);
                    }else {
                        dataList.add(order);
                    }
                }
            }
            map.put("data",dataList);
            list.add(map);
        }
        return list;
    }

    /*查询订单详情*/
    public OperationMaintenanceOrder queryOrderDetail(OrderVo orderVo) {
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        if(order !=null) {
            //设置工单类型与服务类型的名称
            this.setOperationMaintenanceOrderType(order);
        }
        return order;
    }

    /*保存工单评论*/
    public void saveOrderComment(Map<String,Object> map) {
        if(map.get("userId") !=null){
            SecUser secUser = userDao.getUserById(map.get("userId").toString() );
             /*添加工单信息*/
            Map<String,Object> tempMap = new HashMap<String,Object>();
            OrderVo orderVo2 = new OrderVo();
            orderVo2.setOrderId((Integer)map.get("orderId"));
            OperationMaintenanceOrder order1 = operationMaintenanceDao.queryOrderDetail(orderVo2);
            tempMap.put("orderCode", order1.getOrderCode());
            tempMap.put("operationPeople", secUser.getUserName());
            tempMap.put("operationTime", new Date());
            tempMap.put("status",8);
            tempMap.put("operationContent","客户评价工单！");
            operationMaintenanceDao.addOrderRecords(tempMap);
        }
        OperationMaintenanceOrder order = new OperationMaintenanceOrder();
        order.setOrderId((Integer)map.get("orderId"));
        order.setCEvaluateLevel(map.get("cEvaluateLevel").toString()+"");
        order.setCEvaluateContent(map.get("cEvaluateContent").toString());

        operationMaintenanceDao.saveOrderComment(order);
    }

    /*获取图片集合*/
    public Map<String,Object> getPictureList(OperationMaintenanceOrder order) {
        Map<String,Object> picMap = new HashMap<String, Object>();
        if(order.getPhotosBeforeWork() !=null){
            //工作前图片数组
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosBeforeWork());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0) {
                SysAccessory sysAccessory = sysAccessoryList.get(0);
//                File file = new File(imgPath + sysAccessory.getFilePath());
                String[] photoBeforePic = new String[sysAccessoryList.size()];
                if(photoBeforePic.length >0) {
                    for (int i = 0; i < photoBeforePic.length; i++) {
                        String path = sysAccessory.getFilePath().replace("\\","/");
                        photoBeforePic[i] = uploadHosts + path;
                    }
                }
                picMap.put("photoBeforePic", photoBeforePic);
            }
        }
        if(order.getPhotosAtWork() !=null){
            //工作中图片
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosAtWork());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0) {
                SysAccessory sysAccessory = sysAccessoryList.get(0);
//                File file = new File(imgPath + sysAccessory.getFilePath());
                String[] photoDoingPic = new String[sysAccessoryList.size()];
                if(photoDoingPic.length >0) {
                    for (int i = 0; i < sysAccessoryList.size(); i++) {
                        String path = sysAccessory.getFilePath().replace("\\","/");
                        photoDoingPic[i] = uploadHosts + path;
                    }
                }
                picMap.put("photoDoingPic", photoDoingPic);
            }
        }
        if(order.getPhotosAfterWork() !=null){
            //工作后图片
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosAfterWork());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0) {
                SysAccessory sysAccessory = sysAccessoryList.get(0);
//                File file = new File(imgPath + sysAccessory.getFilePath());
                String[] photoAfterPic = new String[sysAccessoryList.size()];
                if(photoAfterPic.length >0) {
                    for (int i = 0; i < sysAccessoryList.size(); i++) {
                        String path = sysAccessory.getFilePath().replace("\\","/");
                        photoAfterPic[i] = uploadHosts + path;
                    }
                }
                picMap.put("photoAfterPic", photoAfterPic);
            }
        }
        if(order.getPhotosProblem() !=null){
            //问题点图片
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosProblem());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0) {
                SysAccessory sysAccessory = sysAccessoryList.get(0);
//                File file = new File(imgPath + sysAccessory.getFilePath());
                String[] photoProblemPic = new String[sysAccessoryList.size()];
                if(photoProblemPic.length >0){
                    for (int i = 0; i < sysAccessoryList.size(); i++) {
                        String path = sysAccessory.getFilePath().replace("\\","/");
                        photoProblemPic[i] = uploadHosts + path;
                    }
                }
                picMap.put("photoProblemPic", photoProblemPic);
            }
        }

        return picMap;
    }

    /*获取pdf文件集合*/
    public List<Object> getpdfList(OperationMaintenanceOrder order) {
        List<Object> pdfList = new ArrayList<Object>();
        Map<String,Object> pdfMap = null;
        pdfMap = new HashMap<String, Object>();
        if(order.getWorkReportOne() !=null){
            //文档1
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getWorkReportOne());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0){
                SysAccessory sysAccessory = sysAccessoryList.get(0);
                File file = new File(uploadPath + sysAccessory.getFilePath());
                String[] reportOnePdfArray = file.list();
                Set<String> reportOneSet = new TreeSet<String>();
                String reportOnePdfName = sysAccessory.getFileName();
                if(reportOnePdfArray != null && reportOnePdfArray.length >0){
                    for (int i = 0; i < reportOnePdfArray.length; i++) {
                        if(!reportOnePdfArray[i].contains(".pdf")) {
                            String path = sysAccessory.getFilePath().replace("\\", "/");
                            String reportImg =  uploadHosts + path +"/" + reportOnePdfArray[i];
                            reportOneSet.add(reportImg);
                        }
                    }
                }
                if (reportOnePdfName == null) {
                    reportOnePdfName = "";
                }
                pdfMap.put("name",reportOnePdfName);
                pdfMap.put("data", reportOneSet);
            }
            pdfList.add(pdfMap);
        }else {
            pdfMap.put("data", null);
            pdfMap.put("name","");
            pdfList.add(pdfMap);
        }
        pdfMap = new HashMap<String, Object>();
        if(order.getWorkReportTwo() !=null){
            //文档2
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getWorkReportTwo());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0) {
                SysAccessory sysAccessory = sysAccessoryList.get(0);
                File file = new File(uploadPath + sysAccessory.getFilePath());
                String[] reportTwoPdfArray = file.list();
                Set<String> reportTwoSet = new TreeSet<String>();
                String reportTwoPdfName = sysAccessory.getFileName();
                if(reportTwoPdfArray != null && reportTwoPdfArray.length >0) {
                    for (int i = 0; i < reportTwoPdfArray.length; i++) {
                        if(!reportTwoPdfArray[i].contains(".pdf")) {
                            String path = sysAccessory.getFilePath().replace("\\", "/");
                            String reportImg =  uploadHosts + path +"/" + reportTwoPdfArray[i];
                            reportTwoSet.add(reportImg);
                        }
                    }
                }
                if (reportTwoPdfName == null) {
                    reportTwoPdfName = "";
                }
                pdfMap.put("data", reportTwoSet);
                pdfMap.put("name", reportTwoPdfName);
            }
            pdfList.add(pdfMap);
        }else {
            pdfMap.put("data", null);
            pdfMap.put("name","");
            pdfList.add(pdfMap);
        }
        return pdfList;
    }

    /*设置工单类型与服务类型的名称*/
    public OperationMaintenanceOrder setOperationMaintenanceOrderType(OperationMaintenanceOrder order){
        String[] orderNameArr = {"","设备巡检","设备试验","设备保养","设备维修","设备抢修","技术支持","工器具监测"};
        String[] orderStatusNameArr = {"未开始","待派工","待实施","待评价","已完成"};
        //设置该订单的服务类型名称
        order.setOrderName(orderNameArr[(int)order.getServiceType()]);
        //设置该订单的状态名称
        order.setOrderStatusName(orderStatusNameArr[(int)order.getOrderType()]);
        return order;
    }

    /**
     * 修改阅读状态
     * @param order
     */
    public void updateReadStatus(OperationMaintenanceOrder order) {
        operationMaintenanceDao.updateReadStatus(order);
    }

    /**
     * 查询阅读状态
     * @return
     */
    public boolean findAllReadStatus(OrderVo orderVo) {
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.queryOrderByType(orderVo);
        if(orderList !=null && orderList.size()>0){
            for(OperationMaintenanceOrder order : orderList){
                if(order.getReadStatus() == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Map<String,Object>> findTaskSchedule(OrderVo orderVo) {
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        /*当天执行计划*/
        Map<String,Object> todayMap = new HashMap<String, Object>();
        List<OperationMaintenanceOrder> todayList = this.findTaskByToday(orderVo);
        todayMap.put("tabName","todayTask");
        todayMap.put("title","今天任务");
        todayMap.put("doneCount",0);
        todayMap.put("sumCount",0);
        todayMap.put("undoneCount",0);
        if(todayList !=null && todayList.size()>0) {
            todayMap.put("sumCount",todayList.size());
            int doneCount = 0;
            for (OperationMaintenanceOrder order : todayList) {
                if (order.getOrderType() == 3 || order.getOrderType() == 4) {
                    doneCount++;
                }
            }
            todayMap.put("doneCount",doneCount);
            todayMap.put("undoneCount",todayList.size()-doneCount);
        }
        mapList.add(todayMap);

        /*明天执行计划*/
        Map<String,Object> nextDayMap = new HashMap<String, Object>();
        List<OperationMaintenanceOrder> nextDayList = this.findTaskByNextday(orderVo);
        nextDayMap.put("tabName","nextdayTask");
        nextDayMap.put("title","明天任务");
        nextDayMap.put("doneCount",0);
        nextDayMap.put("sumCount",0);
        nextDayMap.put("undoneCount",0);
        if(nextDayList !=null && nextDayList.size()>0) {
            nextDayMap.put("sumCount",nextDayList.size());
            int doneCount = 0;
            for (OperationMaintenanceOrder order : todayList) {
                if (order.getOrderType() == 3 || order.getOrderType() == 4) {
                    doneCount++;
                }
            }
            nextDayMap.put("doneCount",doneCount);
            nextDayMap.put("undoneCount",nextDayList.size()-doneCount);
        }

        mapList.add(nextDayMap);

        /*本周执行计划*/
        Map<String,Object> weekMap = new HashMap<String, Object>();
        List<OperationMaintenanceOrder> weekList = this.findTaskByWeek(orderVo);
        weekMap.put("tabName","weekTask");
        weekMap.put("title","本周任务");
        weekMap.put("doneCount",0);
        weekMap.put("sumCount",0);
        weekMap.put("undoneCount",0);
        if(weekList !=null && weekList.size()>0) {
            weekMap.put("sumCount",weekList.size());
            int doneCount = 0;
            for (OperationMaintenanceOrder order : weekList) {
                if (order.getOrderType() == 3 || order.getOrderType() == 4) {
                    doneCount++;
                }
            }
            weekMap.put("doneCount",doneCount);
            weekMap.put("undoneCount",weekList.size()-doneCount);
        }

        mapList.add(weekMap);

        /*本月执行计划*/
        Map<String,Object> monthMap = new HashMap<String, Object>();
        List<OperationMaintenanceOrder> monthList = this.findTaskByMonth(orderVo);
        monthMap.put("tabName","monthTask");
        monthMap.put("title","本月任务");
        monthMap.put("doneCount",0);
        monthMap.put("sumCount",0);
        monthMap.put("undoneCount",0);
        if(monthList !=null && monthList.size()>0) {
            monthMap.put("sumCount",monthList.size());
            int doneCount = 0;
            for (OperationMaintenanceOrder order : monthList) {
                if (order.getOrderType() == 3 || order.getOrderType() == 4) {
                    doneCount++;
                }
            }
            monthMap.put("doneCount",doneCount);
            monthMap.put("undoneCount",monthList.size()-doneCount);
        }
        mapList.add(monthMap);

        return mapList;
    }

    /**
     * 查询今天的任务完成详情
     * @param orderVo
     * @return
     */
    public List<OperationMaintenanceOrder> findTaskByToday(OrderVo orderVo) {
        /*查询用户所在组*/
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());

        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM-dd"));
        paramMap.put("queryValue",orderVo.getQueryValue());
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.findTaskByOneDay(paramMap);
        return orderList;
    }

    /**
     * 查询明天任务实施详情
     * @param orderVo
     * @return
     */
    public List<OperationMaintenanceOrder> findTaskByNextday(OrderVo orderVo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
        Date nextDayDate = calendar.getTime();

        /*查询用户所在组*/
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());
        paramMap.put("endTime",DateUtils.date2Str(nextDayDate,"yyyy-MM-dd"));
        paramMap.put("queryValue",orderVo.getQueryValue());
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.findTaskByOneDay(paramMap);
        return orderList;
    }

    /**
     * 查询本周任务实施详情
     * @param orderVo
     * @return
     */
    public List<OperationMaintenanceOrder> findTaskByWeek(OrderVo orderVo) {
        /*查询用户所在组*/
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-1);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        Date weekFirstDate = calendar.getTime();
        paramMap.put("firstTime",DateUtils.date2Str(weekFirstDate,"yyyy-MM-dd"));
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
        Date weekEndtDate = calendar.getTime();
        paramMap.put("endTime",DateUtils.date2Str(weekEndtDate,"yyyy-MM-dd"));

        paramMap.put("queryValue",orderVo.getQueryValue());
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.findTaskByWeek(paramMap);
        return orderList;
    }

    /**
     * 查询本月任务实施详情
     * @param orderVo
     * @return
     */
    public List<OperationMaintenanceOrder> findTaskByMonth(OrderVo orderVo) {
        /*查询用户所在组*/
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());
        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));
        paramMap.put("queryValue",orderVo.getQueryValue());
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.findTaskByMonth(paramMap);
        return orderList;
    }

    public PageBean findTaskList(Map<String,Object> pageMap){
        String tabName = pageMap.get("tabName").toString();
        int pageNum = (Integer) pageMap.get("pageNum");
        int pageSize = (Integer)pageMap.get("pageSize");

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<OperationMaintenanceOrder> orderList = null;
        OrderVo orderVo = new OrderVo();
        if(pageMap.get("queryValue") !=null ){
            String queryValue = pageMap.get("queryValue").toString();
            orderVo.setQueryValue(queryValue);
        }

        if(pageMap.get("userId") !=null) {
            orderVo.setUserId(pageMap.get("userId").toString());
        }
        if("todayTask".equals(tabName)) {
                /*查询今天任务实施详情*/
            orderList = this.findTaskByToday(orderVo);
        }else if("nextdayTask".equals(tabName)) {
                /*查询明天任务实施详情*/
            orderList = this.findTaskByNextday(orderVo);
        }else if("weekTask".equals(tabName)) {
             /*查询本周任务实施详情*/
            orderList = this.findTaskByWeek(orderVo);
        } else if ("monthTask".equals(tabName)) {
            /*查询本月任务实施详情*/
            orderList = this.findTaskByMonth(orderVo);
        }else if("overTime".equals(tabName)){
            /*查询本月超时任务详情*/
            orderList = this.findOverTimeTask(orderVo);
        }else if("allTask".equals(tabName)){
            /*查询本月所有任务详情*/
            orderList = this.findAllTask(orderVo);
        }

        if(orderList !=null && orderList.size()>0){
            String timeType = "";
            //定义map集合，存储(title:'' data:'')
            Map<String,Object> map = null;
            List<Map<String,Object>> dataList = null;
//            List<OperationMaintenanceOrder> dataList = null;
            for(OperationMaintenanceOrder order : orderList){
                if(order !=null){
                    //设置工单类型与服务类型的名称
                    this.setOperationMaintenanceOrderType(order);

                    /*封装工单数据*/
                    Map<String,Object> paramMap = new HashMap<String, Object>();
                    OperationMaintenanceProject project = operationMaintenanceDao.findProjectById((int)order.getProjectId());
                    String workStatus = "unfinish";
                    if(order.getOrderType() == 3 || order.getOrderType() ==4){
                        workStatus = "finish";
                    }
                    paramMap.put("orderId",order.getOrderId());
                    paramMap.put("customerId",order.getCustomerId());
                    paramMap.put("orderName",order.getOrderName());
                    paramMap.put("orderCode",order.getOrderCode());
                    paramMap.put("plannedTime",order.getPlannedTime());
                    paramMap.put("orderName",order.getOrderName());
                    paramMap.put("operator",order.getOperator());
                    paramMap.put("projectId",order.getProjectId());
                    paramMap.put("projectName",project.getProjectName());
                    paramMap.put("workStatus",workStatus);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(order.getPlannedTime());
                    String planTime = calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+(calendar.get(Calendar.DAY_OF_MONTH))+"日";
                    if(!timeType.equals(planTime)){
                        timeType = planTime;
                        if(dataList !=null){
                            map.put("data",dataList);
                        }
                        if(map !=null){
                            list.add(map);
                        }
                        map = new HashMap<String, Object>();
                        dataList = new ArrayList<Map<String, Object>>();
                        dataList.add(paramMap);
                        map.put("title",timeType);
                    }else {
                        dataList.add(paramMap);
                    }
                }
            }
            map.put("data",dataList);
            list.add(map);
        }

        if(list == null && list.size()== 0){
            list = new ArrayList<Map<String, Object>>();
        }
        int totalSize = list.size();
        int totalPage = totalSize % pageSize > 0 ? totalSize/pageSize +1 : totalSize/pageSize;

        PageBean pageBean = new PageBean();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        int firstIndex = (pageNum -1) * pageSize;
        int endIndex = (pageNum ) * pageSize;
        if(endIndex > totalSize){
            endIndex = totalSize;
        }
        for(int i = firstIndex ; i<endIndex ; i++){
            resultList.add(list.get(i));
        }
        pageBean.setList(resultList);
        pageBean.setTotalCount(totalSize);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    /**
     * 查询全部订单
     * @param orderVo
     * @return
     */
    private List<OperationMaintenanceOrder> findAllTask(OrderVo orderVo) {
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());
        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));
        paramMap.put("queryValue",orderVo.getQueryValue());
        return operationMaintenanceDao.findAllTask(paramMap);
    }

    /**
     * 查询本月超时任务列表
     * @param orderVo
     * @return
     */
    private List<OperationMaintenanceOrder> findOverTimeTask(OrderVo orderVo) {
        /*查询用户所在组*/
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());
        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));
        paramMap.put("queryValue",orderVo.getQueryValue());
        return operationMaintenanceDao.findOverTimeTask(paramMap);
    }

    /**
     * 查询本月超时任务数量
     * @param orderVo
     * @return
     */
    public int findOverTimeTaskCount(OrderVo orderVo) {
        /*查询用户所在组*/
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());

        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));
        return operationMaintenanceDao.findOverTimeTaskCount(paramMap);
    }

    /**
     * 查询本月任务数量以及完成数量折线图
     * @param orderVo
     * @return
     */
    public String loadTaskOption(OrderVo orderVo) {
        Map<String,String> paramMap = this.findGroupName(orderVo.getUserId());
        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));
        List<Map<String,Object>> mapList = operationMaintenanceDao.findDoneTaskByMonth(paramMap);
        List<Map<String,Object>> timeList = this.getTimeList(orderVo);
        String[] dataTypeArray = {"任务数","完成数"};
        String option = "";
        List<EchartsSeries> list = new ArrayList<EchartsSeries>();
        EchartsSeries doneSeries = new EchartsSeries();
        EchartsSeries sumSeries = new EchartsSeries();
        String[] monthTimeArray = new String[timeList.size()];
        String[] doneDataArray = new String[timeList.size()];
        String[] sumDataArray = new String[timeList.size()];
        String[] color = {"#28ff28", "#5793f3", "#d14a61"};
        if(mapList !=null && mapList.size()>0){
            if(timeList !=null && timeList.size()>0){
               outer:for(Map<String,Object> map1 :mapList){
                   inner:for(Map<String,Object> map2 :timeList){
                        if(map1.get("PLANNED_TIME").equals(map2.get("PLANNED_TIME"))){
                            map2.putAll(map1);
                            break inner;
                        }
                   }
               }
            }
            for(int i = 0; i<timeList.size();i++){
                monthTimeArray[i] = timeList.get(i).get("PLANNED_TIME").toString();
                doneDataArray[i] = (timeList.get(i).get("DONE").toString());
                sumDataArray[i] = (timeList.get(i).get("SUM").toString());

            }
        }else{
            for(int i = 0; i<timeList.size();i++){
                monthTimeArray[i] = timeList.get(i).get("PLANNED_TIME").toString();
                doneDataArray[i] = (timeList.get(i).get("DONE").toString());
                sumDataArray[i] = (timeList.get(i).get("SUM").toString());

            }
        }
        sumSeries.setName(dataTypeArray[0]);
        sumSeries.setData(sumDataArray);
        sumSeries.setType("line");
        list.add(sumSeries);

        doneSeries.setName(dataTypeArray[1]);
        doneSeries.setData(doneDataArray);
        doneSeries.setType("line");
        list.add(doneSeries);


        option =  EchartsUtils.getOptionManyLine("",color,dataTypeArray,monthTimeArray,list);
        return option;
    }

    List<Map<String,Object>> getTimeList(OrderVo orderVo){
        orderVo.setFirstTime(DateUtils.date2Str(new Date(),"yyyy-MM")+"-01");
        /*获取当月最后一天日期*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(calendar.MONTH, (calendar.get(calendar.MONTH) + 1));
        calendar.set(calendar.DAY_OF_MONTH, 0);
        Date lastDayOfMonth = calendar.getTime();
        orderVo.setEndTime(DateUtils.date2Str(lastDayOfMonth,"yyyy-MM-dd"));
        return operationMaintenanceDao.getTimeList(orderVo);
    }

    /**
     * 查询某项目的执行情况
     * @param order
     * @return
     */
    public Map<String,Object> findProjectDetail(OperationMaintenanceOrder order) {
        Map<String,Object> map = new HashMap<String, Object>();
        //在operation_maintenance_project表中没有技术支持与工期
        List<OperationMaintenanceOrder> orderList =  operationMaintenanceDao.findOrderListByProjectId(order);
        //巡检
        int doneXJCount = 0;
        int sumXJCount = 0;
        //试验
        int doneSYCount = 0;
        int sumSYCount = 0;
        //保养
        int doneBYCount = 0;
        int sumBYCount = 0;
        //技术支持
        int doneJSZCCount = 0;
        int sumJSZCCount = 0;
        //工器具检测
        int doneGQJJCCount = 0;
        int sumGQJJCCount = 0;

        if(orderList !=null && orderList.size()>0){
            for(OperationMaintenanceOrder tempOrder : orderList){
                if(tempOrder.getServiceType() == 1){
                    //巡检
                    sumXJCount ++;
                    if(tempOrder.getOrderType() == 3 || tempOrder.getOrderType() == 4){
                        doneXJCount ++;
                    }
                    continue;
                }else if(tempOrder.getServiceType() == 2){
                    //试验
                    sumSYCount ++;
                    if(tempOrder.getOrderType() == 3 || tempOrder.getOrderType() == 4){
                        doneSYCount ++;
                    }
                    continue;
                }else if(tempOrder.getServiceType() == 3){
                    //保养
                    sumBYCount ++;
                    if(tempOrder.getOrderType() == 3 || tempOrder.getOrderType() == 4){
                        doneBYCount ++;
                    }
                    continue;
                }else if(tempOrder.getServiceType() == 6){
                    //技术支持
                    sumJSZCCount ++;
                    if(tempOrder.getOrderType() == 3 || tempOrder.getOrderType() == 4){
                        doneJSZCCount ++;
                    }
                    continue;
                }else if(tempOrder.getServiceType() == 7){
                    //工器具检测
                    sumGQJJCCount ++;
                    if(tempOrder.getOrderType() == 3 || tempOrder.getOrderType() == 4){
                        doneGQJJCCount ++;
                    }
                    continue;
                }
            }
        }
        map.put("doneXJCount",doneXJCount);
        map.put("sumXJCount",sumXJCount);
        map.put("XJName","巡检");
        map.put("doneSYCount",doneSYCount);
        map.put("sumSYCount",sumSYCount);
        map.put("SYName","试验");
        map.put("doneBYCount",doneBYCount);
        map.put("sumBYCount",sumBYCount);
        map.put("BYName","维护保养");
        map.put("doneJSZCCount",doneJSZCCount);
        map.put("sumJSZCCount",sumJSZCCount);
        map.put("JSZCName","技术支持");
        map.put("doneGQJJCCount",doneGQJJCCount);
        map.put("sumGQJJCCount",sumGQJJCCount);
        map.put("GQJJCName","工器具检测");

        return map;
    }

    /**
     *查询班组成员带标识
     * @param receiveMap
     * @return
     */
    public List<Map<String, String>> findConstructionGroupMember(Map<String,String> receiveMap) {
        List<Map<String,String>> engineerList = operationMaintenanceDao.findConstructionGroupMember(receiveMap);
        return engineerList;
    }

    /**
     * 新增班组成员
     * @param map
     */
    @Transactional
    public void addConstructionGroupMember(Map<String,Object> map) {
        operationMaintenanceDao.deleteConstructionGroup(map.get("groupName").toString());
        List<String> list = (List<String>)map.get("list");
        if(list !=null && list.size()>0) {
            operationMaintenanceDao.addConstructionGroupMember(map);
        }
    }

    /**
     * 添加工作照片
     * @param map
     */
    @Transactional
    public void addWorkPicture(Map<String, Object> map) {
        try {
            int orderId = Integer.parseInt(map.get("orderId").toString());
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(orderId);
            OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);

            Base64 base64 = new Base64();
            SysAccessory sysAccessory = null;
            String fileUrl = imgPath + "/" + DateUtils.getCurrentDay();
            File newFile = new File(uploadPath + fileUrl);
            if(map.get("photoBeforeWork") !=null ) {
                /*工作前图片*/
                List<String> photoBeforeList = (List<String>) map.get("photoBeforeWork");
                if(photoBeforeList !=null && photoBeforeList.size()>0  ) {
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                    }
                    String workBeforeRelationId = "app" + System.currentTimeMillis();
                    order.setPhotosBeforeWork(workBeforeRelationId);

//                operationMaintenanceDao.deleteSysAccessory(order.getPhotosBeforeWork());
                    for (int i = 0; i < photoBeforeList.size(); i++) {
                        byte[] buff = base64.decode(photoBeforeList.get(i));
                        String fileName = System.currentTimeMillis() + ".jpg";
                        sysAccessory = new SysAccessory();
                        sysAccessory.setRelationId(workBeforeRelationId);
                        sysAccessory.setFileSuffix("jpg");
                        sysAccessory.setFileType(3);
                        sysAccessory.setDeleted(0);
                        sysAccessory.setUploadTime(new Date());
                        sysAccessory.setFilePath(fileUrl + "/" + fileName);
                        sysAccessory.setFileName(fileName);
                        FileOutputStream fos = new FileOutputStream(uploadPath + sysAccessory.getFilePath());
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bos.write(buff);
                        bos.close();
                        operationMaintenanceDao.addSysAccessory(sysAccessory);
                    }
                }
            }

            if(map.get("photoAtWork") !=null ) {
            /*工作中图片*/
                List<String> photoDoingList = (List<String>) map.get("photoAtWork");
                if(photoDoingList !=null && photoDoingList.size()>0) {
                    fileUrl = imgPath + "/" + DateUtils.getCurrentDay();
                    newFile = new File(uploadPath + fileUrl);
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                    }
                    String workingRelationId = "app" + System.currentTimeMillis();
                    order.setPhotosAtWork(workingRelationId);
//                operationMaintenanceDao.deleteSysAccessory(order.getPhotosAtWork());
                    for (int i = 0; i < photoDoingList.size(); i++) {
                        byte[] buff = base64.decode(photoDoingList.get(i));
                        String fileName = System.currentTimeMillis() + ".jpg";
                        sysAccessory = new SysAccessory();
                        sysAccessory.setRelationId(workingRelationId);
                        sysAccessory.setFileSuffix("jpg");
                        sysAccessory.setFileType(3);
                        sysAccessory.setDeleted(0);
                        sysAccessory.setFileName(fileName);
                        sysAccessory.setUploadTime(new Date());
                        sysAccessory.setFilePath(fileUrl + "/" + fileName);
                        FileOutputStream fos = new FileOutputStream(uploadPath + sysAccessory.getFilePath());
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bos.write(buff);
                        bos.close();
                        operationMaintenanceDao.addSysAccessory(sysAccessory);
                    }
                }
            }

            if(map.get("photoAfterWork") !=null ) {
            /*工作后图片*/
                List<String> photosAfterWorkList = (List<String>) map.get("photoAfterWork");
                if(photosAfterWorkList !=null && photosAfterWorkList.size()>0) {
                    fileUrl = imgPath + "/" + DateUtils.getCurrentDay();
                    newFile = new File(uploadPath + fileUrl);
                    if (!newFile.exists()) {
                        newFile.mkdirs();
                    }
                    String workAfterRelationId = "app" + System.currentTimeMillis();
                    order.setPhotosAfterWork(workAfterRelationId);

//                operationMaintenanceDao.deleteSysAccessory(order.getPhotosAfterWork());
                    for (int i = 0; i < photosAfterWorkList.size(); i++) {
                        byte[] buff = base64.decode(photosAfterWorkList.get(i));
                        String fileName = System.currentTimeMillis() + ".jpg";
                        sysAccessory = new SysAccessory();
                        sysAccessory.setRelationId(workAfterRelationId);
                        sysAccessory.setFileSuffix("jpg");
                        sysAccessory.setFileType(3);
                        sysAccessory.setDeleted(0);
                        sysAccessory.setFileName(fileName);
                        sysAccessory.setUploadTime(new Date());
                        sysAccessory.setFilePath(fileUrl + "/" + fileName);
                        FileOutputStream fos = new FileOutputStream(uploadPath + sysAccessory.getFilePath());
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bos.write(buff);
                        bos.close();
                        operationMaintenanceDao.addSysAccessory(sysAccessory);
                    }
                }
            }
            operationMaintenanceDao.updateOrderPhoto(order);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询图片信息
     * @param orderVo
     * @return
     */
    public Map<String, Object> queryPhotosList(OrderVo orderVo) {
        OperationMaintenanceOrder order = this.queryOrderDetail(orderVo);
        Map<String,Object> picMap = this.getPictureList(order);
        if(picMap ==null || picMap.size() == 0){
            picMap = new HashMap<String,Object>();
            List<String> list = new ArrayList<String>();
            picMap.put("photoBeforePic", list);
            picMap.put("photoDoingPic", list);
            picMap.put("photoAfterPic", list);
        }
        return picMap;
    }

    /**
     * 查询工单中所服务的电房集合
     * @param orderVo
     * @return
     */
    public List<Map<String, Object>> queryElectricRoomList(OrderVo orderVo) {
        List<Map<String,Object>> roomList = operationMaintenanceDao.queryElectricRoomList(orderVo);
        ElectricRoomStatus roomStatus = new ElectricRoomStatus();
        roomStatus.setOrderId(orderVo.getOrderId());
        if(roomList !=null && roomList.size()>0){
            for(Map<String,Object> map : roomList){
                roomStatus.setRoomId((Integer)map.get("ID"));
                /*查询电房是否完成*/
                Integer status = operationMaintenanceDao.findElectricRoomStatus(roomStatus);
                if(status == null){
                    status = 0;
                }
                map.put("worked",status);
            }
        }else{
            roomList = new ArrayList<Map<String, Object>>();
        }
        return roomList;
    }

    /**
     * 新增电房
     * @param map
     */
    @Transactional
    public ElectricRoomInfo addElectricRoom(Map<String,String> map) {
        ElectricRoomInfo roomInfo = new ElectricRoomInfo();
        try {

            SecUser secUser = userDao.getUserById(map.get("userId"));
            String fileName = "";
            boolean flag = false;
            if(StringUtils.isNotBlank(map.get("dataUrl"))){
                Base64 base64 = new Base64();
                /*添加电房图片*/
                byte[] buff = base64.decode(map.get("dataUrl"));
                fileName = System.currentTimeMillis()+".jpg";
                FileOutputStream fos = new FileOutputStream(uploadPath+imgPath+"/"+fileName);
                BufferedOutputStream bos  = new BufferedOutputStream(fos);
                bos.write(buff);
                bos.close();
                flag = true;
            }

            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(Integer.parseInt(map.get("orderId")));
            OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
            roomInfo.setCustomerId(order.getCustomerId());
            roomInfo.setElectricalRoomName(map.get("roomName"));
            if(secUser !=null) {
                roomInfo.setCreator(secUser.getUserName());
            }
            roomInfo.setCreateTime(new Date());
            roomInfo.setCharger(order.getContacts());
            roomInfo.setChargeTel(order.getPhone());
            roomInfo.setAddress(order.getProjectDetailAddress());
            roomInfo.setWorked(0);
            if(flag) {
                roomInfo.setPhotoUrl(uploadHosts + imgPath + "/" + fileName);
            }
            roomInfo.setDeleted(0);
            operationMaintenanceDao.addElectricRoom(roomInfo);

            //封装设备类型数据
            RoomEquipmentType equipmentType = new RoomEquipmentType();
            equipmentType.setCreateTime(new Date());
            equipmentType.setDeleted(0);
            equipmentType.setElectricalRoomId((int)roomInfo.getId());
            equipmentType.setEquipmentNumber(0);
            equipmentType.setEquipmentTypeName("配电房");
            if(secUser !=null) {
                equipmentType.setCreator(secUser.getUserName());
            }
            /*通过电房id与设备类型名称查询设备类型数量*/
            int count = operationMaintenanceDao.findEquipmentTypeCount(equipmentType);
            if(count == 0){
                operationMaintenanceDao.addEquipmentType(equipmentType);
                Map<String,Object> equipmentMap = new HashMap<String,Object>();
                equipmentMap.put("id",equipmentType.getId());
                equipmentMap.put("operProductsId",order.getOperProductsId());
                List<String> equipmentList = new ArrayList<String>();
                equipmentList.add("配电房");
                equipmentMap.put("equipmentNameList",equipmentList);
                this.addEquipment(equipmentMap);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return roomInfo;
    }

    /**
     * 查询班组分组列表
     * @param
     * @return
     */
    public List<String> findConstructionGroup() {
        List<String> groupList = operationMaintenanceDao.findConstructionGroup();
        return groupList;
    }

    /**
     * 查询设备类型列表
     * @param
     * @param roomStatus
     * @return
     */
    public List<Map<String,Object>> findEquipmentTypeList(ElectricRoomStatus roomStatus) {
        ElectricRoomInfo roomInfo = new ElectricRoomInfo();
        roomInfo.setId(roomStatus.getId());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id",roomStatus.getId());
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId((int)roomStatus.getOrderId());
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        paramMap.put("operProductsId",order.getOperProductsId());
        List<Map<String,Object>> equipmentTypeList = operationMaintenanceDao.findEquipmentTypeList(paramMap);
        /*对应状态 0：未完成 1：有问题 2：已完成*/
        String[] equipmentStautsArr = {"unfinished","error","success"};
        /*创建E*/
        EquipmentErrorMessage errorMessage = new EquipmentErrorMessage();
        errorMessage.setOrderId(roomStatus.getOrderId());
        if(equipmentTypeList !=null && equipmentTypeList.size()>0) {
            for (Map<String, Object> map : equipmentTypeList) {
                int status = 0;
            /*查询设备类型状态*/
                Integer paramStatus = operationMaintenanceDao.findEquipmentTypeStatus(roomStatus.getOrderId(), (Integer) map.get("id"));
                errorMessage.setEquipmentTypeId((Integer) map.get("id"));
                if (paramStatus != null) {
                    if (paramStatus == 1) {
                        status = 2;
                    }
                }
            /*查询异常数量*/
                int count = operationMaintenanceDao.findEquipmentErrorCount(errorMessage);
                if (count > 0) {
                    status = 1;
                }
                map.put("status", equipmentStautsArr[status]);
            }
        }else{
            equipmentTypeList = new ArrayList<Map<String,Object>>();
        }
        return equipmentTypeList;
    }

    /**
     * 查询设备类型列表是否选择
     * @param map
     * @return
     */
    public List<Map<String, Object>> findEquipmentTypeSelect(Map<String,Integer> map) {
        List<Map<String,Object>> equipmentTypeList = operationMaintenanceDao.findEquipmentTypeSelect(map);
        if(equipmentTypeList == null){
            equipmentTypeList = new ArrayList<Map<String,Object>>();
        }
        return equipmentTypeList;
    }

    /**
     * 新增设备类型
     * @param map
     */
    @Transactional
    public void addEquipmentType(Map<String, Object> map) {
        int roomId = (Integer)map.get("roomId");
        List<String> equipmentNameList = (List<String>)map.get("equipmentNameArr");
        //封装设备类型数据
        RoomEquipmentType equipmentType = new RoomEquipmentType();
        equipmentType.setCreateTime(new Date());
        equipmentType.setDeleted(0);
        equipmentType.setElectricalRoomId(roomId);
        equipmentType.setEquipmentNumber(0);
        if(equipmentNameList !=null && equipmentNameList.size() >0){
            SecUser secUser = userDao.getUserById(map.get("userId").toString());
            if(secUser !=null) {
                equipmentType.setCreator(secUser.getUserName());
            }
            for(String equipmentName : equipmentNameList) {
                equipmentType.setEquipmentTypeName(equipmentName);
                /*通过电房id与设备类型名称查询设备类型数量*/
                int count = operationMaintenanceDao.findEquipmentTypeCount(equipmentType);
                if(count == 0){
                    operationMaintenanceDao.addEquipmentType(equipmentType);
                 }
            }
        }
    }


    /**
     * 新增设备
     * @param map
     */
    @Transactional
    public void addEquipment(Map<String, Object> map) {
        long id = (Long)map.get("id");
        int operProductsId = (Integer)map.get("operProductsId");
        /*通过设备类型id查询设备类型名称*/
        RoomEquipmentType equipmentType = operationMaintenanceDao.findEquipmentTypeById((int) id);
        List<String> list = (List<String>)map.get("equipmentNameList");
        RoomEquipmentInfo equipmentInfo = new RoomEquipmentInfo();
        equipmentInfo.setEquipmentTypeId((int) id);
        equipmentInfo.setDeleted(0);

        if(list !=null){
            for(String equipmentName : list) {
                equipmentInfo.setEquipmentName(equipmentName);
                /*添加设备信息*/
                operationMaintenanceDao.addEquipment(equipmentInfo);
            }
        }
        /*修改设备数量*/
        equipmentType.setEquipmentNumber(equipmentType.getEquipmentNumber()+list.size());
        /*修改设备类型数量*/
        operationMaintenanceDao.updateEquipmentType(equipmentType);
    }

    /**
     * 查询设备列表
     * @param typeStatus
     * @return
     */
    public List<Map<String, Object>> queryEquipmentList(EquipmentTypeStatus typeStatus) {
        RoomEquipmentType equipmentType = new RoomEquipmentType();
        equipmentType.setId(typeStatus.getEquipmentTypeId());
        List<Map<String,Object>> mapList = operationMaintenanceDao.queryEquipmentList(equipmentType);
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId((int)typeStatus.getOrderId());
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        EquipmentErrorMessage errorMessage = new EquipmentErrorMessage();
        EquipmentErrorMessage errorMessageParam = new EquipmentErrorMessage();
        errorMessage.setOrderId(typeStatus.getOrderId());
        if(mapList !=null && mapList.size()>0){
            for(Map<String,Object> map :mapList){
                errorMessage.setEquipmentId((Integer)map.get("id"));
                int count = operationMaintenanceDao.findEquipmentErrorCount(errorMessage);
                map.put("errorCount",count);
                OperationMaintenanceOrder newOrder = operationMaintenanceDao.findNewOrder(order);
                map.put("lastError","noProblem");//没问题
                if(newOrder !=null){
                    errorMessageParam.setOrderId(newOrder.getOrderId());
                    errorMessageParam.setEquipmentId((Integer)map.get("id"));
                    int newCount = operationMaintenanceDao.findEquipmentErrorCount(errorMessageParam);
                    if(newCount == 0){
                        map.put("lastError","noProblem");//没问题
                    }else{
                        map.put("lastError","problem");//有问题
                    }
                }
            }
        }else{
            mapList = new ArrayList<Map<String, Object>>();
        }
        return mapList;
    }

    /**
     *删除设备
     * @param errorMessage
     */
    @Transactional
    public void deleteEquipment(EquipmentErrorMessage errorMessage) {
        /*查询设备*/
        RoomEquipmentInfo equipmentInfo = operationMaintenanceDao.findEquipmentById(errorMessage.getEquipmentId());
        /*查询设备类型*/
        RoomEquipmentType type = operationMaintenanceDao.findEquipmentTypeById(equipmentInfo.getEquipmentTypeId());
        /*修改设备数量*/
        type.setEquipmentNumber(type.getEquipmentNumber()-1);
        /*修改设备类型*/
        operationMaintenanceDao.updateEquipmentType(type);
        /*删除异常信息表*/
        operationMaintenanceDao.deleteErrorMessage(errorMessage);
        /*删除设备*/
        operationMaintenanceDao.deleteEquipment(errorMessage.getEquipmentId());
    }

    /**
     * 修改设备名称
     * @param equipmentInfo
     */
    public void updateEquipment(RoomEquipmentInfo equipmentInfo) {
        operationMaintenanceDao.updateEquipment(equipmentInfo);
    }

    /**
     * 查询工作项列表
     * @param errorMessage
     * @return
     */
    public List<EquipmentWorkContent> queryWorkProjectList(EquipmentErrorMessage errorMessage) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId((int)errorMessage.getOrderId());
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        RoomEquipmentInfo equipmentInfo = operationMaintenanceDao.findEquipmentById(errorMessage.getEquipmentId());
        RoomEquipmentType equipmentType = operationMaintenanceDao.findEquipmentTypeById(equipmentInfo.getEquipmentTypeId());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("operProductsId",order.getOperProductsId());
        map.put("equipmentTypeName",equipmentType.getEquipmentTypeName());
        List<EquipmentWorkContent> workContentList = operationMaintenanceDao.queryWorkProjectList(map);
        if(workContentList !=null && workContentList.size()>0) {
            for (EquipmentWorkContent workContent : workContentList) {
                errorMessage.setWorkProjectId(workContent.getId());
                int count = operationMaintenanceDao.findEquipmentErrorCount(errorMessage);
                if(count == 0){
                    workContent.setWorkProjectStauts(1);//没问题
                }else{
                    workContent.setWorkProjectStauts(0);//有问题
                }
                /*查询最新一次完成的服务类型的工单*/
                OperationMaintenanceOrder newOrder = operationMaintenanceDao.findNewOrder(order);
                workContent.setLastErrorStatus(1);//没问题
                if(newOrder !=null) {
                    EquipmentErrorMessage errorMessage1 = new EquipmentErrorMessage();
                    errorMessage1.setOrderId(newOrder.getOrderId());
                    errorMessage1.setEquipmentId(errorMessage.getEquipmentId());
                    errorMessage1.setWorkProjectId(errorMessage.getWorkProjectId());
                    int newCount = operationMaintenanceDao.findEquipmentErrorCount(errorMessage1);
                    if (newCount == 0) {
                        workContent.setLastErrorStatus(1);//没问题
                    } else {
                        workContent.setLastErrorStatus(0);//有问题
                    }
                }

            }
        }else{
            workContentList = new ArrayList<EquipmentWorkContent>();
        }
        return workContentList;
    }

    /**
     * 添加异常信息
     * @param map
     */
    @Transactional
    public void addErrorMessage(Map<String,Object> map) throws Exception {
        String photoUrl = "";
        if(StringUtils.isNotBlank(map.get("dataUrl").toString())) {
            Base64 base64 = new Base64();

            /*定义一个目录*/
            long currentTime = System.currentTimeMillis();
            String fileUrl= DateUtils.getCurrentDay();
            File newFile = new File(uploadPath+imgPath+"/"+fileUrl);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            /*添加异常图片*/
            byte[] buff = base64.decode(map.get("dataUrl").toString());
            String fileName = System.currentTimeMillis() + ".jpg";
            FileOutputStream fos = new FileOutputStream( uploadPath+imgPath+"/"+ fileUrl + "/" + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(buff);
            bos.close();
         /*图片上传路径*/
            photoUrl = imgPath +"/"+ fileUrl + "/" + fileName;
        }
        EquipmentErrorMessage errorMessage = new EquipmentErrorMessage();
        if((Integer)map.get("id") == 0) {
            int workProjectId = (Integer) map.get("workProjectId");
            int equipmentId = (Integer)map.get("equipmentId");
            /*查询设备信息*/
            RoomEquipmentInfo equipmentInfo = operationMaintenanceDao.findEquipmentById(equipmentId);
            /*查询设备类型信息*/
            RoomEquipmentType equipmentType = operationMaintenanceDao.findEquipmentTypeById(equipmentInfo.getEquipmentTypeId());

            errorMessage.setOrderId((Integer)map.get("orderId"));
            errorMessage.setWorkStatus(0);
            errorMessage.setRoomId(equipmentType.getElectricalRoomId());
            errorMessage.setEquipmentTypeId(equipmentType.getId());
            errorMessage.setEquipmentId(equipmentInfo.getId());
            errorMessage.setWorkProjectId(workProjectId);
            errorMessage.setAdviceMessage(map.get("adviceMessage").toString());
            errorMessage.setErrorMessage(map.get("errorMessage").toString());
            errorMessage.setErrorPhoto(photoUrl);
            errorMessage.setCreateTime(new Date());
            errorMessage.setDeleted(0);
            List<EquipmentErrorMessage> errorList = operationMaintenanceDao.queryHistoryProblemList(errorMessage);
            if(errorList ==null || errorList.size() == 0){
                operationMaintenanceDao.addErrorMessage(errorMessage);
            }
        }else{
            errorMessage.setId((Integer)map.get("id"));
            errorMessage = operationMaintenanceDao.findErrorMessageById(errorMessage);
            errorMessage.setAdviceMessage(map.get("adviceMessage").toString());
            errorMessage.setErrorMessage(map.get("errorMessage").toString());
            if(photoUrl !="") {
                errorMessage.setErrorPhoto(photoUrl);
            }
            errorMessage.setCreateTime(new Date());
            operationMaintenanceDao.updateErrorMessage(errorMessage);
        }
    }

    /**
     * 删除设备类型
     * @param equipmentType
     */
    @Transactional
    public void deleteEquipmentType(RoomEquipmentType equipmentType) {
        operationMaintenanceDao.deleteEquipmentType(equipmentType);
        List<Map<String,Object>>  equipmentList = operationMaintenanceDao.queryEquipmentList(equipmentType);
        if(equipmentList !=null && equipmentList.size()>0){
            for(Map<String,Object> map : equipmentList){
                operationMaintenanceDao.deleteEquipment((Integer)map.get("id"));
            }
        }
        operationMaintenanceDao.deleteEquipmentTypeStatus(equipmentType.getId());
    }

    /**
     * 修改电房名称
     * @param
     */
    public void updateElectricRoom( ElectricRoomInfo roomInfo) {
        if(StringUtils.isNotBlank(roomInfo.getElectricalRoomName())) {
            operationMaintenanceDao.updateElectricRoom(roomInfo);
        }
    }

    /**
     * 删除电房
     * @param roomStatus
     */
    @Transactional
    public void deleteElectricRoom(ElectricRoomStatus roomStatus) {
        ElectricRoomInfo roomInfo = new ElectricRoomInfo();
        roomInfo.setId(roomStatus.getRoomId());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("id",roomStatus.getRoomId());
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId((int)roomStatus.getOrderId());
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        paramMap.put("operProductsId",order.getOperProductsId());

        List<Map<String,Object>> typeList = operationMaintenanceDao.findEquipmentTypeList(paramMap);
        RoomEquipmentType equipmentType = new RoomEquipmentType();
        if(typeList != null && typeList.size()>0){
            for(Map<String,Object> map : typeList){
                equipmentType.setId((Integer)map.get("id"));
                List<Map<String,Object>> infoList = operationMaintenanceDao.queryEquipmentList(equipmentType);
                if(infoList != null && infoList.size()>0){
                    for(Map<String,Object> infoMap :infoList){

                        operationMaintenanceDao.deleteEquipment((Integer)infoMap.get("id"));
                    }
                }
                operationMaintenanceDao.deleteEquipmentType(equipmentType);
            }
        }
        operationMaintenanceDao.deleteElectricRoom(roomInfo);
        operationMaintenanceDao.deleteElectricRoomStatus(roomStatus);
    }

    /**
     * 查询历史问题
     * @param errorMessage
     * @return
     */
    public List<EquipmentErrorMessage> queryHistoryProblemList(EquipmentErrorMessage errorMessage) {
        List<EquipmentErrorMessage> errorMessageList = operationMaintenanceDao.queryHistoryProblemList(errorMessage);
        if(errorMessageList == null){
            errorMessageList = new ArrayList<EquipmentErrorMessage>();
        }
        return errorMessageList;
    }

    /**
     * 完成设备类型
     * @param typeStatus
     */
    public void finishEquipmentType(EquipmentTypeStatus typeStatus) {
        /*查询设备类型状态数量*/
        int count = operationMaintenanceDao.findEquipmentTypeStatusCount(typeStatus);
        /*添加设备类型的状态*/
        if(count == 0) {
            typeStatus.setFinishStatus(1);
            operationMaintenanceDao.finishEquipmentType(typeStatus);
        }
    }

    /**
     * 完成此电房
     * @param roomStatus
     */
    public void finishElectricRoom(ElectricRoomStatus roomStatus) {
         /*查询设备类型状态数量*/
        int count = operationMaintenanceDao.findElectricRoomStatusCount(roomStatus);
        /*完成此电房*/
        if(count == 0) {
            roomStatus.setFinishStatus(1);
            operationMaintenanceDao.finishElectricRoom(roomStatus);
        }
    }

    /**
     * 完成任务
     * @param map
     */
    public void customerSignature(Map<String,Object> map) throws IOException {
        int orderId = (Integer)map.get("orderId");
        /*保存图片信息*/
         /*定义一个目录*/
        String fileUrl1= DateUtils.getCurrentDay()+"/"+"dateImage";
        File newFile = new File(uploadPath + imgPath +"/"+ fileUrl1);
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        Base64 base64 = new Base64();
         /*添加日期图片*/
        byte[] buff = base64.decode(map.get("dateImage").toString());
        String fileName = System.currentTimeMillis() + ".jpg";
        FileOutputStream fos = new FileOutputStream( uploadPath + imgPath + "/"+ fileUrl1 + "/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(buff);
        bos.close();

        String fileUrl2= DateUtils.getCurrentDay()+"/"+"signatureImage";
        File newFile1 = new File(uploadPath + imgPath +"/"+ fileUrl2);
        if(!newFile1.exists()){
            newFile1.mkdirs();
        }

         /*添加签名图片*/

        byte[] buff1 = base64.decode(map.get("signatureImage").toString());
        String fileName1 = System.currentTimeMillis() + ".jpg";
        FileOutputStream fos1 = new FileOutputStream( uploadPath + imgPath +"/"+ fileUrl2 + "/" + fileName1);
        BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
        bos1.write(buff1);
        bos1.close();

        /*定义图片地址*/
        String outPath = pdfImgPath + "/"+ DateUtils.getCurrentDay()+ "/" + System.currentTimeMillis() ;
        String targetPath = outPath+"/"+System.currentTimeMillis()+".pdf";
        File file4 = new File(uploadPath + outPath);
        if(!file4.exists()){
            file4.mkdirs();
        }

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        /*查询工单信息*/
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        SysAccessory sysAccessory = null;
        String relationId = "";
        if(StringUtils.isNotBlank(order.getWorkReportOne()) && order.getWorkReportOne() != "0") {
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getWorkReportOne());
            sysAccessory = sysAccessoryList.get(0);
            relationId = sysAccessory.getRelationId();
        }else{
            sysAccessory = new SysAccessory();
            relationId = "BG" + System.currentTimeMillis();
            sysAccessory.setRelationId(relationId);
        }
        sysAccessory.setFileName(targetPath.substring(targetPath.lastIndexOf("/") + 1));
        sysAccessory.setDeleted(0);
        sysAccessory.setFileType(3);
        sysAccessory.setFilePath(outPath);
        sysAccessory.setFileSuffix(targetPath.substring(targetPath.lastIndexOf(".") + 1));
        sysAccessory.setUploadTime(new Date());
        if(sysAccessory.getId() !=0){
            operationMaintenanceDao.updateSysAccessory(sysAccessory);
        }else {
            operationMaintenanceDao.addSysAccessory(sysAccessory);
        }

        order.setOrderId(orderId);
        order.setOrderType(3);
        order.setWorkReportOne(relationId);
        order.setDatePhoto(imgPath + "/"+ fileUrl1 + "/" + fileName);
        order.setSignaturePhoto(imgPath +"/"+ fileUrl2 + "/" + fileName1);
        operationMaintenanceDao.customerSignature(order);

        /*添加工单信息*/
        if(map.get("userId") !=null) {
            String userId = map.get("userId").toString();
            Map<String, Object> tempMap = new HashMap<String, Object>();
            OrderVo orderVo2 = new OrderVo();
            orderVo2.setOrderId(orderId);
            SecUser secUser = userDao.getUserById(userId);
            tempMap.put("status",7);
            tempMap.put("orderCode", order.getOrderCode());
            tempMap.put("operationPeople", secUser.getUserName());
            tempMap.put("operationTime", new Date());
            tempMap.put("operationContent", order.getEngineer() + "完成施工!");
            operationMaintenanceDao.addOrderRecords(tempMap);
        }

         /*生成word文件*/
        String newWordPath = produceWord(orderId,imgPath + "/"+ fileUrl1 + "/" + fileName,imgPath +"/"+ fileUrl2 + "/" + fileName1);
        /*将word转换成pdf*/

        /*多线程生成图片*/
        toPicThread(newWordPath,targetPath,outPath);
    }

    public void toPicThread(final String newWordPath,final String targetPath,final String outPath){
        Thread thread =  new Thread() {
            public void run() {
                try {
                    Word2PdfUtil.wordToPdf(newWordPath, uploadPath, targetPath);
                    Pdf2PicUtils.pdf2Png( uploadPath + targetPath,uploadPath + outPath);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /**
     * 查询异常信息
     * @param errorMessageParam
     * @return
     */
    public EquipmentErrorMessage findErrorMessage(EquipmentErrorMessage errorMessageParam) {
        EquipmentErrorMessage errorMessage = operationMaintenanceDao.findErrorMessage(errorMessageParam);
        if(errorMessage !=null) {
            errorMessage.setErrorPhoto(uploadHosts + errorMessage.getErrorPhoto());
        }
        return errorMessage;
    }

    /*根据设备类型名称查询工作项集合*/
    public List<EquipmentWorkContent> findWorkProjectList(Map<String,Object> map){
        return operationMaintenanceDao.queryWorkProjectList(map);
    }

    /*查询服务类型最新的执行情况*/
    public OperationMaintenanceOrder findServiceTypeNewTime(OperationMaintenanceOrder order) {
         /*查询服务类型最新的执行情况*/
        OperationMaintenanceOrder order1 =  operationMaintenanceDao.findServiceTypeNewTime(order);
        return order1;
    }

    /**
     * 查询分组成员名称
     * @param memberMap
     * @return
     */
    public List<Map<String, String>> findGroupMemberName(Map<String, String> memberMap) {
        return operationMaintenanceDao.findGroupMemberName(memberMap);
    }

    /**
     * 查询工单中电房完成列表
     * @param order
     * @return
     */
    public List<ElectricRoomStatus> findFinishRoomList(OperationMaintenanceOrder order) {
        return operationMaintenanceDao.findFinishRoomList(order);
    }



    public String produceWord(int orderId ,String dateImg ,String signatureImg) {
        String  directoryPath = wordPath+"/"+DateUtils.getCurrentDay();
        File newFile = new File(uploadPath + directoryPath);
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        String suffixWordPath = directoryPath+"/"+System.currentTimeMillis()+".doc";
        Map<String, Object> dataModel = new HashMap<String, Object>();
        try {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(orderId);
            /*查询工单详情*/
            OperationMaintenanceOrder order = this.queryOrderDetail(orderVo);
            /*查询项目信息*/
            OperationMaintenanceProject project = this.findProjectById((int)order.getProjectId());


            Map<String, Object> projectMessageMap = new HashMap<String, Object>();
            Map<String, Object> orderMessageMap = new HashMap<String, Object>();
            /*项目名称*/
            projectMessageMap.put("projectName", project.getProjectName());
            /*公司名称*/
            projectMessageMap.put("companyName", "广东可信电力股份有限公司");
            /*报告时间*/
            projectMessageMap.put("time", DateUtils.date2Str(new Date(),"yyyy年MM月dd日"));
            /*项目编号*/
            projectMessageMap.put("projectCode", project.getProjectNo());
            /*项目地址*/
            projectMessageMap.put("projectAddress", project.getProjectDetailAddress());
            /*开始时间*/
            projectMessageMap.put("startTime", DateUtils.date2Str(project.getStartTime(),"yyyy/MM/dd"));
            /*结束时间*/
            projectMessageMap.put("endTime", DateUtils.date2Str(project.getEndTime(),"yyyy/MM/dd"));
            /*项目执行情况*/
            /*查询执行次数*/
            Map<String,Object> projectMap = this.findProjectDetail(order);
        /*查询服务类型最新一次执行时间*/

            //1.巡检 2.试验 3.保养 4.维修 5.抢修 6.技术支持 7.工器具监测'
            OperationMaintenanceOrder paramOrder = new OperationMaintenanceOrder();
            paramOrder.setCustomerId(order.getCustomerId());
            paramOrder.setServiceType(1);
            OperationMaintenanceOrder xjOrder =  this.findServiceTypeNewTime(paramOrder);
            //巡检总次数
            projectMessageMap.put("XJSumCount", projectMap.get("sumXJCount"));
            //巡检已完成次数
            projectMessageMap.put("XJDoneCount", projectMap.get("doneXJCount"));

            String xjTime = "-";
            if(xjOrder !=null){
                xjTime = DateUtils.date2Str(xjOrder.getPlannedTime(),"yyyy/MM/dd");
            }
            //巡检执行时间
            projectMessageMap.put("XJLastTime", xjTime);

            paramOrder.setServiceType(2);
            OperationMaintenanceOrder syOrder =  this.findServiceTypeNewTime(paramOrder);
            //试验总次数
            projectMessageMap.put("SYSumCount", projectMap.get("sumSYCount"));
            //试验已完成次数
            projectMessageMap.put("SYDoneCount", projectMap.get("doneSYCount"));

            String syTime = "-";
            if(syOrder !=null){
                syTime = DateUtils.date2Str(syOrder.getPlannedTime(),"yyyy/MM/dd");
            }
            //试验执行时间
            projectMessageMap.put("SYLastTime", syTime);

            paramOrder.setServiceType(3);
            OperationMaintenanceOrder byOrder =  this.findServiceTypeNewTime(paramOrder);
            //保养总次数
            projectMessageMap.put("BYSumCount", projectMap.get("sumBYCount"));
            //保养已完成次数
            projectMessageMap.put("BYDoneCount", projectMap.get("doneBYCount"));

            String byTime = "-";
            if(byOrder !=null){
                byTime = DateUtils.date2Str(byOrder.getPlannedTime(),"yyyy/MM/dd");
            }
            //保养执行时间
            projectMessageMap.put("BYLastTime", byTime);

            paramOrder.setServiceType(6);
            OperationMaintenanceOrder zcOrder =  this.findServiceTypeNewTime(paramOrder);
            //技术支持总次数
            projectMessageMap.put("JSZCSumCount", projectMap.get("sumJSZCCount"));
            //技术支持已完成次数
            projectMessageMap.put("JSZCDoneCount", projectMap.get("doneJSZCCount"));

            String zcTime = "-";
            if(zcOrder !=null){
                zcTime = DateUtils.date2Str(zcOrder.getPlannedTime(),"yyyy/MM/dd");
            }
            //技术支持执行时间
            projectMessageMap.put("JSZCLastTime", zcTime);

            paramOrder.setServiceType(7);
            OperationMaintenanceOrder jcOrder =  this.findServiceTypeNewTime(paramOrder);
            //工器具检测总次数
            projectMessageMap.put("JCSumCount", projectMap.get("sumGQJJCCount"));
            //工器具检测已完成次数
            projectMessageMap.put("JCDoneCount", projectMap.get("doneGQJJCCount"));
            String jcTime = "-";
            if(jcOrder !=null){
                jcTime = DateUtils.date2Str(jcOrder.getPlannedTime(),"yyyy/MM/dd");
            }
            //工器具检测执行时间
            projectMessageMap.put("JCLastTime", jcTime);

            String[] reportNameArr = {"","维保巡检","预防性试验","维护保养","维护维修","维护抢修","技术支持","工器具监测"};

            /*设置数据*/
            dataModel.put("projectMessage", projectMessageMap);

            /*需要定义一个数组，存放报告名称，在根据服务类型分类过滤出对应的名称*/
            /*报告名称*/
            orderMessageMap.put("reportName", "【"+reportNameArr[(int)order.getServiceType()]+"】");
            /*设备信息*/
            String equipmentMessageStr = order.getEquipmentInfo();
            String[] equipmentMessageArr = equipmentMessageStr.split("\n");
            orderMessageMap.put("equipmentMessage", equipmentMessageArr);

            /*巡检时间*/
            orderMessageMap.put("workTime", new Date());

            Map<String,String> memberMap = new HashMap<String,String>();
            memberMap.put("groupName",order.getEngineer());
            /*需要查询分组人员，并用分号隔开*/
            List<Map<String, String>> memberList = this.findGroupMemberName(memberMap);
            String meberStr = "";
            if(memberList !=null && memberList.size()>0) {
                for (int i= 0;i<memberList.size();i++) {
                    if(i == 0) {
                        meberStr += memberList.get(i);
                    }else{
                        meberStr += ("、"+ memberList.get(i));
                    }
                }
            }

            /*工作人员*/
            orderMessageMap.put("worker", meberStr);
            /*联系电话*/
            orderMessageMap.put("phoneNum", order.getPhone());
            /*服务类型名称*/
            orderMessageMap.put("serviceTypeName",reportNameArr[(int)order.getServiceType()]);

            Map<String,Object> workProjectMap = new HashMap<String,Object>();
            RoomEquipmentType type = new RoomEquipmentType();
            /*需查询电房完成数量，设备数量，工作项目，异常数量*/
            List<ElectricRoomStatus> electricRoomStatusList = this.findFinishRoomList(order);
            if(electricRoomStatusList !=null && electricRoomStatusList.size()>0){
                for(ElectricRoomStatus status : electricRoomStatusList){
                    List<Map<String,Object>> typeList =  this.findEquipmentTypeList(status);
                    if(typeList !=null && typeList.size()>0) {
                        for (Map<String,Object> typeMap : typeList) {
                            type.setId((Integer)typeMap.get("id"));
                            List<Map<String,Object>> equipmentList = operationMaintenanceDao.queryEquipmentList(type);
                            for(Map<String,Object> equipmentMap : equipmentList) {
                                for (Map<String, Object> workmap : typeList) {
                                    workProjectMap.put("equipmentTypeName", workmap.get("serviceTypeName"));
                                    workProjectMap.put("operProductsId", order.getOperProductsId());
                                    List<EquipmentWorkContent> contentList = this.findWorkProjectList(workProjectMap);
                                }
                            }
                        }
                    }
                }
            }
            EquipmentErrorMessage errorMessage = new EquipmentErrorMessage();
            errorMessage.setOrderId(order.getOrderId());

            List<EquipmentErrorMessage> errorMessageList = this.queryHistoryProblemList(errorMessage);
            List<Map<String,Object>> adviceList = new ArrayList<Map<String,Object>>();
            if(errorMessageList !=null && errorMessageList.size()>0) {
                for (EquipmentErrorMessage errorMessage1 : errorMessageList) {
                    Map<String,Object> adviceMap = new HashMap<String,Object>();
                    Map<String,Object> roomInfoMap = operationMaintenanceDao.findElectricRoomById(errorMessage1.getRoomId());
                    RoomEquipmentType type1 = operationMaintenanceDao.findEquipmentTypeById((int)errorMessage1.getEquipmentTypeId());
                    RoomEquipmentInfo equipmentInfo = operationMaintenanceDao.findEquipmentById(errorMessage1.getEquipmentId());
                    Map<String,Object> workProjectMap1 =  operationMaintenanceDao.queryWorkProjectById(errorMessage1.getWorkProjectId());
                    adviceMap.put("roomName","");
                    if(roomInfoMap !=null) {
                        adviceMap.put("roomName", roomInfoMap.get("roomName").toString());
                    }
                    adviceMap.put("typeName","");
                    if(type1 !=null) {
                        adviceMap.put("typeName", type1.getEquipmentTypeName());
                    }
                    adviceMap.put("equipmentName","");
                    if(equipmentInfo !=null) {
                        adviceMap.put("equipmentName", equipmentInfo.getEquipmentName());
                    }
                    adviceMap.put("workProjectName","");
                    if(workProjectMap1 !=null) {
                        adviceMap.put("workProjectName", workProjectMap1.get("projectName").toString());
                    }
                    adviceMap.put("adviceMessage", "无");
                    if(org.apache.commons.lang3.StringUtils.isNotBlank(errorMessage1.getAdviceMessage())) {
                        adviceMap.put("adviceMessage", errorMessage1.getAdviceMessage());
                    }
                    adviceList.add(adviceMap);
                }
            }

            dataModel.put("adviceList",adviceList);
            int errorNum = errorMessageList.size();

            if(order !=null) {
                orderVo.setCustomerId(order.getCustomerId());
            }
            /*查询电房集合*/

            List<Map<String, Object>> roomInfoMapList = this.queryElectricRoomList(orderVo);
            List<List<Map<String, Object>>> typeList = new ArrayList<List<Map<String, Object>>>();
            List<List<List<Map<String, Object>>>> contentList = new ArrayList<List<List<Map<String, Object>>>>();
            ElectricRoomStatus roomStatus = new ElectricRoomStatus();
            roomStatus.setOrderId(order.getOrderId());
            EquipmentErrorMessage contentErrorMessage = new EquipmentErrorMessage();
            Map<String, Object> workContentParamMap = new HashMap<String, Object>();
            /*定义图片集合*/
            List<Map<String, Object>> imgList = new ArrayList<Map<String, Object>>();
            int roomNum = 0;
            int typeNum = 0;
            int equipmentNum = 0;
            int workNum = 0;
            int imgNum = 0;
            if (roomInfoMapList != null  && roomInfoMapList.size()>0) {
                for (Map<String, Object> roomInfoMap : roomInfoMapList) {
                    roomNum ++;
                    roomStatus.setId((Integer) roomInfoMap.get("ID"));
                    List<Map<String, Object>> equipmentTypeMapList = this.findEquipmentTypeList(roomStatus);
                    List<List<Map<String, Object>>> list2 = new ArrayList<List<Map<String, Object>>>();
                    if (equipmentTypeMapList != null && equipmentTypeMapList.size()>0) {
                        typeList.add(equipmentTypeMapList);
                        for (Map<String, Object> equipmentTypeMap : equipmentTypeMapList) {
                            typeNum++;
                            type.setId((Integer) equipmentTypeMap.get("id"));
                            List<Map<String, Object>> equipmentList = operationMaintenanceDao.queryEquipmentList(type);
                            equipmentNum += equipmentList.size();
                            /*定义一个集合包含工作项，结果，照片*/
                            workContentParamMap.put("equipmentTypeName", equipmentTypeMap.get("serviceTypeName").toString());
                            workContentParamMap.put("operProductsId", order.getOperProductsId());
                            List<EquipmentWorkContent> workContentList = this.findWorkProjectList(workContentParamMap);
                            workNum += workContentList.size();
                            /*将工作项数据重新封装*/
                            List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
                            if(workContentList !=null && workContentList.size()>0) {
                                for (EquipmentWorkContent workContent : workContentList) {
                                    Map<String, Object> paramMap = new HashMap<String, Object>();
                                    paramMap.put("id", workContent.getId());
                                    paramMap.put("workProjectName", workContent.getWorkProjectName());
                                /*并且封装工作项异常信息*/
                                /*查询设备类型中的对应的设备项目问题集合*/
                                    contentErrorMessage.setOrderId(orderId);
                                    contentErrorMessage.setRoomId((Integer) roomInfoMap.get("ID"));
                                    contentErrorMessage.setEquipmentTypeId((Integer) equipmentTypeMap.get("id"));
                                    contentErrorMessage.setWorkProjectId(workContent.getId());//此处修改
                                    List<Map<String, Object>> errorParamMapList = new ArrayList<Map<String, Object>>();
                                    List<EquipmentErrorMessage> errorParamList = this.queryHistoryProblemList(contentErrorMessage);
                                    if (errorParamList != null && errorParamList.size() > 0) {
                                        for (EquipmentErrorMessage equipmentErrorMessage : errorParamList) {
                                            imgNum += 1;
                                            Map<String, Object> tempMap = new HashMap<String, Object>();
                                            tempMap.put("imgName", "img" + imgNum + ".png");
                                            tempMap.put("id", "imgId" + imgNum);
                                        /*转成base64字符串*/
                                            String binaryData = "";
                                            if (org.apache.commons.lang3.StringUtils.isNotBlank(equipmentErrorMessage.getErrorPhoto())) {
                                                File file = new File(uploadPath + equipmentErrorMessage.getErrorPhoto());
                                                FileInputStream inputStream = new FileInputStream(file);
                                                byte[] buff = new byte[inputStream.available()];
                                                int length = inputStream.read(buff);
                                                Base64 base64 = new Base64();
                                                binaryData = base64.encodeAsString(buff);
                                            }
                                            tempMap.put("binaryData", binaryData);
                                            RoomEquipmentInfo equipmentInfo = operationMaintenanceDao.findEquipmentById(equipmentErrorMessage.getEquipmentId());
                                            tempMap.put("errorMessage", equipmentInfo.getEquipmentName()+","+equipmentErrorMessage.getErrorMessage());
                                            imgList.add(tempMap);
                                            errorParamMapList.add(tempMap);
                                        }
                                    }
                                    paramMap.put("errorMessageList", errorParamMapList);
                                    if (paramList != null) {
                                        paramList.add(paramMap);
                                    }
                                }
                            }
                            list2.add(paramList);
                        }
                        contentList.add(list2);
                    }
                }
            }

            orderMessageMap.put("roomNum",roomNum);
            orderMessageMap.put("equipmentNum",equipmentNum);
            orderMessageMap.put("workNum",workNum);
            orderMessageMap.put("errorNum",errorNum);
            /*工作概要*/
//            orderMessageMap.put("workDesctricption", "本次"+reportNameArr[(int)order.getServiceType()]+"共检查了"+roomNum+"个电房、"+equipmentNum+"个设备、"+workNum+"个工作项目，共发现"+errorNum+"项异常，巡检工作如下：");
            /*电房数据集合*/
            dataModel.put("roomInfoList", roomInfoMapList);
            /*设备类型集合*/
            dataModel.put("equipmentTypeList", typeList);
            /*工作项集合*/
            dataModel.put("workContentList", contentList);

            /*工单数据*/
            dataModel.put("orderMessage", orderMessageMap);

            //客户签名
            String signatureImgBs = "";
            if(org.apache.commons.lang3.StringUtils.isNotBlank(signatureImg)) {
                File file1 = new File(uploadPath + signatureImg);
                FileInputStream inputStream1 = new FileInputStream(file1);
                byte[] buff1 = new byte[inputStream1.available()];
                int length1 = inputStream1.read(buff1);
                Base64 base64 = new Base64();
                signatureImgBs = base64.encodeAsString(buff1);
            }
            //签名日期
            String dateImgBs = "";
            if(org.apache.commons.lang3.StringUtils.isNotBlank(dateImg)) {
                File file2 = new File(uploadPath + dateImg);
                FileInputStream inputStream2 = new FileInputStream(file2);
                byte[] buff2 = new byte[inputStream2.available()];
                int length2 = inputStream2.read(buff2);
                Base64 base64 = new Base64();
                dateImgBs = base64.encodeAsString(buff2);
            }

            /*获取工作前图片*/
            String photoBeforeImg = "";
            if(order.getPhotosBeforeWork() !=null) {
                List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosBeforeWork());
                if (sysAccessoryList != null && sysAccessoryList.size() > 0) {
                    SysAccessory sysAccessory = sysAccessoryList.get(0);
                    String[] photoBeforePic = new String[sysAccessoryList.size()];
                    if (photoBeforePic.length > 0) {
                        for (int i = 0; i < photoBeforePic.length; i++) {
                            String path = sysAccessory.getFilePath().replace("\\", "/");
                            photoBeforePic[i] = path;
                        }

                        if (org.apache.commons.lang3.StringUtils.isNotBlank(photoBeforePic[0])) {
                            File file2 = new File(uploadPath + photoBeforePic[0]);
                            FileInputStream inputStream2 = new FileInputStream(file2);
                            byte[] buff2 = new byte[inputStream2.available()];
                            int length2 = inputStream2.read(buff2);
                            Base64 base64 = new Base64();
                            photoBeforeImg = base64.encodeAsString(buff2);
                        }
                    }
                }
            }
            dataModel.put("photoBeforeImg",photoBeforeImg);

            /*获取工作中图片*/
            String photoDoingImg = "";
            if(order.getPhotosAtWork() !=null) {
                List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosAtWork());
                if (sysAccessoryList != null && sysAccessoryList.size() > 0) {
                    SysAccessory sysAccessory = sysAccessoryList.get(0);
                    String[] photoDoingPic = new String[sysAccessoryList.size()];
                    if (photoDoingPic.length > 0) {
                        for (int i = 0; i < photoDoingPic.length; i++) {
                            String path = sysAccessory.getFilePath().replace("\\", "/");
                            photoDoingPic[i] = path;
                        }

                        if (org.apache.commons.lang3.StringUtils.isNotBlank(photoDoingPic[0])) {
                            File file2 = new File(uploadPath + photoDoingPic[0]);
                            FileInputStream inputStream2 = new FileInputStream(file2);
                            byte[] buff2 = new byte[inputStream2.available()];
                            int length2 = inputStream2.read(buff2);
                            Base64 base64 = new Base64();
                            photoDoingImg = base64.encodeAsString(buff2);
                        }
                    }
                }
            }
            dataModel.put("photoDoingImg",photoDoingImg);


            /*获取工作后图片*/
            String photosAfterWorkImg = "";
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getPhotosAfterWork());
            if(sysAccessoryList !=null && sysAccessoryList.size() >0) {
                SysAccessory sysAccessory = sysAccessoryList.get(0);
                String[] photoAfterPic = new String[sysAccessoryList.size()];
                if(photoAfterPic.length >0) {
                    for (int i = 0; i < photoAfterPic.length; i++) {
                        String path = sysAccessory.getFilePath().replace("\\","/");
                        photoAfterPic[i] = path;
                    }

                    if(org.apache.commons.lang3.StringUtils.isNotBlank(photoAfterPic[0])) {
                        File file2 = new File(uploadPath + photoAfterPic[0]);
                        FileInputStream inputStream2 = new FileInputStream(file2);
                        byte[] buff2 = new byte[inputStream2.available()];
                        int length2 = inputStream2.read(buff2);
                        Base64 base64 = new Base64();
                        photosAfterWorkImg = base64.encodeAsString(buff2);
                    }
                }
            }
            dataModel.put("photosAfterWorkImg",photosAfterWorkImg);

            /*客户签名图片*/
            dataModel.put("signatureImgBs", signatureImgBs);
            /*客户签名日期图片*/
            dataModel.put("dateImgBs", dateImgBs);

            File file2 = new File(uploadPath+"/"+corporateSealPath);
            FileInputStream inputStream2 = new FileInputStream(file2);
            byte[] buff2 = new byte[inputStream2.available()];
            int length2 = inputStream2.read(buff2);
            Base64 base64 = new Base64();
            String corporateSealImg = base64.encodeAsString(buff2);
            /*公司印章图片*/
            dataModel.put("corporateSealImg", corporateSealImg);
            /*封装图片数据*/
            dataModel.put("imgList",imgList);
           return  FreeMarkerUtils.toWord(dataModel,uploadPath + suffixWordPath,"word.ftl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 本月任务概要
     * @param
     * @return
     */
    public Map<String, Integer> findTaskCountByTime(Map<String,String> paramMap) {
        List<OperationMaintenanceOrder> orderList =  operationMaintenanceDao.findOrderByTime(paramMap);
        Map<String,Integer> map = new HashMap<String, Integer>();
        int XJSumCount = 0;
        int XJDoneCount = 0;
        int SYSumCount = 0;
        int SYDoneCount = 0;
        int BYSumCount = 0;
        int BYDoneCount = 0;
        int JSZCSumCount = 0;
        int JSZCDoneCount = 0;
        int JCSumCount = 0;
        int JCDoneCount = 0;
        if(orderList !=null){
            for(OperationMaintenanceOrder order : orderList){
                /*运维巡检数量*/
                if(order.getServiceType() == 1) {
                    XJSumCount ++;
                    if(order.getOrderType() == 3 || order.getOrderType() == 4){
                        XJDoneCount ++;
                    }
                }
               /*预防试验数量*/
                if(order.getServiceType() == 2) {
                    SYSumCount++;
                    if(order.getOrderType() == 3 || order.getOrderType() == 4){
                        SYDoneCount++;
                    }
                }

               /*维护保养数量*/
                if(order.getServiceType() == 3) {
                    BYSumCount ++;
                    if(order.getOrderType() == 3 || order.getOrderType() == 4){
                        BYDoneCount++;
                    }
                }

               /*技术支持数量*/
                if(order.getServiceType() == 6) {
                    JSZCSumCount ++;
                    if(order.getOrderType() == 3 || order.getOrderType() == 4){
                        JSZCDoneCount ++;
                    }
                }

               /*工器具检测数量*/
                if(order.getServiceType() == 7) {
                    JCSumCount ++;
                    if(order.getOrderType() == 3 || order.getOrderType() == 4){
                        JCDoneCount ++;
                    }
                }

            }
        }
        map.put("XJSumCount",XJSumCount);
        map.put("XJDoneCount",XJDoneCount);
        map.put("SYSumCount",SYSumCount);
        map.put("SYDoneCount",SYDoneCount);
        map.put("BYSumCount",BYSumCount);
        map.put("BYDoneCount",BYDoneCount);
        map.put("JSZCSumCount",JSZCSumCount);
        map.put("JSZCDoneCount",JSZCDoneCount);
        map.put("JCSumCount",JCSumCount);
        map.put("JCDoneCount",JCDoneCount);
        return map;
    }

    /**
     * 获取每月的记录
     * @param map
     * @return
     */
    public List<Map<String, Object>> findOrderByMonth(Map<String,Object> map) {
        String monthTime = map.get("monthTime").toString();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<String> timeList = operationMaintenanceDao.findMonthTime(monthTime);
        /*查询分组名称*/
        Map<String,String> paramMap = new HashMap<String, String>();
        if (timeList != null && timeList.size() > 0) {
            String workStatus = "completed";
            for (String dayTime : timeList) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("timestamp", DateUtils.converStrTimeToDate(dayTime).getTime());
                paramMap.put("endTime",dayTime);
                /*根据时间条件查询工单*/
                List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.findOrderByTime(paramMap);
                int doneCount = 0;
                int sumCount = 0;
                if (orderList != null && orderList.size() > 0) {
                    sumCount = orderList.size();
                    for (OperationMaintenanceOrder order : orderList) {
                        if (order.getOrderType() == 3 || order.getOrderType() == 4) {
                            doneCount++;
                        }
                    }
                }
                if(doneCount < sumCount){
                    workStatus = "unfinished";
                }
                String workSize = doneCount + "/" + sumCount;
                resultMap.put("workSize",workSize);
                resultMap.put("workStatus",workStatus);
                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    /**
     * 本月任务概要与今日任务概要
     * @param
     * @return
     */
    public Map<String, Object> loadTaskDescription() {

        Map<String,Object> map = new HashMap<String,Object>();
        /*本月任务概要*/
        Map<String,String> paramMap1 = new HashMap<String, String>();
        paramMap1.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));

        Map<String,Integer> monthTaskMap = this.findTaskCountByTime(paramMap1);
        map.put("monthTaskMap",monthTaskMap);
        /*今日任务概要*/

        OrderVo orderVo2 = new OrderVo();
        Map<String,String> paramMap2 = new HashMap<String, String>();
        orderVo2.setEndTime(DateUtils.date2Str(new Date(),"yyyy-MM-dd"));
        paramMap2.put("endTime",orderVo2.getEndTime());
        Map<String,Integer> todayTaskMap = this.findTaskCountByTime(paramMap2);
        map.put("todayTaskMap",todayTaskMap);
        /*查询超时未完成数量*/
        int count = this.findOverTimeTaskCount(orderVo2);
        map.put("overTimeCount",count);

        return map;
    }

    /**
     * 查询工单事件数量
     * @param paramMap
     * @return
     */
    public Map<String, Object> loadOrderEvents(Map<String, Object> paramMap) {
        /*查询工单事件数量*/
        return null;
    }


    /**
     * 新增工单
     * @param paramMap
     */
    @Transactional
    public void addOrder(Map<String, Object> paramMap) {
        //1.巡检 2.试验 3.保养 4.维修 5.抢修 6.技术支持 7.工器具监测
        String[] orderTypeArr = {"","sjType","syType","byType","","","jszcType","jcType"};
        for(int i = 0; i<orderTypeArr.length ; i++){
            if(paramMap.get("serviceType") !=null &&orderTypeArr[i].equals(paramMap.get("serviceType").toString()) && paramMap.get("serviceType").toString() !=""){
                paramMap.put("serviceType",i);
            }
        }
        SecUser secUser = userDao.getUserById(paramMap.get("userId").toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderCode = "GD"+format.format(new Date());
        Map<String,Object> tempMap = new HashMap<String,Object>();
        tempMap.put("orderCode", orderCode);
        tempMap.put("operationPeople", secUser.getUserName());
        tempMap.put("operationTime", new Date());
        tempMap.put("status",2);
        if(org.apache.commons.lang3.StringUtils.isNotBlank(paramMap.get("groupName").toString())) {
            paramMap.put("order_type",2);
            tempMap.put("operationContent", "新建工单,及新建时派工" + paramMap.get("groupName").toString() + "！");
        }else{
            paramMap.put("order_type",1);
            tempMap.put("operationContent", "新建工单,未派工！");
        }
        operationMaintenanceDao.addOrderRecords(tempMap);

        paramMap.put("executeTime",DateUtils.convertTimeToDate((Long) paramMap.get("executeTime")));
        OperationMaintenanceProject project = operationMaintenanceDao.findProjectById((Integer) paramMap.get("projectId"));
        paramMap.put("project_detail_address",project.getProjectDetailAddress());
        paramMap.put("equipment_info",project.getEquipmentInfo());
        paramMap.put("contacts",project.getContacts());
        paramMap.put("phone",project.getPhone());
        paramMap.put("salesman",project.getSalesmanId());
        paramMap.put("salesman_phone",project.getSalesmanPhone() == null );
        paramMap.put("update_time",new Date());
        paramMap.put("area_id",project.getAreaId());
        paramMap.put("orderCode",orderCode);
        paramMap.put("project_name",project.getProjectName());
        paramMap.put("operator",project.getCustomerName());
        paramMap.put("oper_products_id",project.getOperProductsInspectionId());
        operationMaintenanceDao.addOrder(paramMap);
    }

    /**
     * 查询工单报告列表
     * @param paramMap
     * @return
     */
    @Transactional
    public List<Map<String, Object>> findReportList(Map<String, Object> paramMap) {
        Map<String,String> reportMap = new HashMap<String, String>();
        reportMap.put("groupName","");
        if(paramMap.get("userId").toString() != ""){
            Map<String,String> param = new HashMap<String, String>();
            param.put("userId",paramMap.get("userId").toString());
            Map<String,Object> groupMap = operationMaintenanceDao.findGroupByUserId(param);
            if(groupMap != null && StringUtils.isNotBlank(groupMap.get("operation_group_name").toString())){
                reportMap.put("groupName",groupMap.get("operation_group_name").toString());
            }
        }

        PageHelper.startPage((Integer) paramMap.get("pageNum"),(Integer) paramMap.get("pageSize"));
        List<OperationMaintenanceOrder> mapList = operationMaintenanceDao.findReportList(reportMap);
        List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
        String[] serviceNameArr = {"","维保巡检","预防性试验","维护保养","维护维修","维护抢修","技术支持","工器具监测"};

        PageInfo pageInfo = new PageInfo(mapList);
        List<OperationMaintenanceOrder> list = pageInfo.getList();
        if(list !=null && list.size()>0){
            for(OperationMaintenanceOrder order : list){
                Map<String,Object> resultMap = new HashMap<String,Object>();
                String serviceName = serviceNameArr[(int)order.getServiceType()];
                resultMap.put("service_name",serviceName);
                resultMap.put("project_name",order.getOrderProjectName());
                resultMap.put("planned_time",order.getPlannedTime());
                resultMap.put("c_evaluate_level", 0);
                if(StringUtils.isNotBlank(order.getCEvaluateLevel())) {
                    resultMap.put("c_evaluate_level", Integer.parseInt(order.getCEvaluateLevel()));
                }
                resultMap.put("d_evaluate_level", 0);
                if(StringUtils.isNotBlank(order.getDEvaluateLevel())) {
                    resultMap.put("d_evaluate_level", Integer.parseInt(order.getDEvaluateLevel()));
                }
                resultMap.put("order_id",order.getOrderId());
                /*查询分组人员*/
                Map<String,String> paramMap1 = new HashMap<String, String>();
                paramMap1.put("groupName",order.getEngineer());
                List<Map<String,String>> memberList = operationMaintenanceDao.findGroupMemberName(paramMap1);
                if(memberList == null){
                    memberList = new ArrayList<Map<String, String>>();
                }
                resultMap.put("member_list",memberList);
                /*工单报告图片*/

                resultMap.put("order_report",new ArrayList<String>());
                if(StringUtils.isNotBlank(order.getWorkReportOne())){
                    //文档1
                    List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getWorkReportOne());
                    if(sysAccessoryList !=null && sysAccessoryList.size() >0){
                        SysAccessory sysAccessory = sysAccessoryList.get(0);
                        File file = new File(uploadPath + sysAccessory.getFilePath());
                        String[] reportOnePdfArray = file.list();
                        Set<String> reportSet = new TreeSet<String>();
                        if(reportOnePdfArray != null && reportOnePdfArray.length >0){
                            for (int i = 0; i < reportOnePdfArray.length; i++) {
                                if(!reportOnePdfArray[i].contains(".pdf")) {
                                    String path = sysAccessory.getFilePath().replace("\\", "/");
                                    String reportImg =  uploadHosts + path +"/" + reportOnePdfArray[i];
                                    reportSet.add(reportImg);
                                }
                            }
                        }
                        resultMap.put("order_report",reportSet);
                    }
                }

                /*是否发现异常*/
                EquipmentErrorMessage errorMessage = new EquipmentErrorMessage();
                errorMessage.setOrderId(order.getOrderId());
                int errorCount = operationMaintenanceDao.findEquipmentErrorCount(errorMessage);
                resultMap.put("error_count",errorCount);

                resultList.add(resultMap);
            }
        }
        return resultList;
    }

    /**
     * 查询不同类型工单列表
     * @param paramMap
     * @return
     */
    public PageBean findOrderList(Map<String, Object> paramMap) {
        int orderTypeStatus = 0;
        int serviceTypeStatus = 0;
        String orderTypeName = "";
        int pageNum = (Integer)paramMap.get("pageNum");
        int pageSize = (Integer)paramMap.get("pageSize");

        if("all".equals(paramMap.get("orderType"))){
        }else if("forDispatching".equals(paramMap.get("orderType"))){
            orderTypeStatus = 1;
        }else if("toImplement".equals(paramMap.get("orderType"))){
            orderTypeStatus = 2;
        }else if("forRemark".equals(paramMap.get("orderType"))){
            orderTypeStatus = 3;
        }else if("finish".equals(paramMap.get("orderType"))){
            orderTypeStatus = 4;
        }
        paramMap.put("orderType",orderTypeStatus);
        //服务类型（巡检："sjType",试验："syType",保养："byType",技术支持："jszcType",工器具监测："jcType"）
        // 1.巡检 2.试验 3.保养 4.维修 5.抢修 6.技术支持 7.工器具监测',
        if("allType".equals(paramMap.get("serviceType"))){
            serviceTypeStatus = 0;
        }else if("sjType".equals(paramMap.get("serviceType"))){
            serviceTypeStatus = 1;
        }else if("syType".equals(paramMap.get("serviceType"))){
            serviceTypeStatus = 2;
        }else if("byType".equals(paramMap.get("serviceType"))){
            serviceTypeStatus = 3;
        }else if("jszcType".equals(paramMap.get("serviceType"))){
            serviceTypeStatus = 6;
        }else if("jcType".equals(paramMap.get("serviceType"))){
            serviceTypeStatus = 7;
        }
        paramMap.put("serviceType",serviceTypeStatus);

        PageHelper.startPage(pageNum,pageSize);
        List<OperationMaintenanceOrder> orderList = operationMaintenanceDao.findOrderListLike(paramMap);

        for(OperationMaintenanceOrder order :orderList){
            System.out.println(order.getPlannedTime()+"||###################################");
        }
        PageInfo pageInfo = new PageInfo(orderList);

        List<OperationMaintenanceOrder> newOrderList = pageInfo.getList();

        String[] orderNameArr = {"","待派工","待实施","待评价","完成"};
        String[] serviceNameArr = {"","维保巡检","预防性试验","维护保养","维护维修","维护抢修","技术支持","工器具监测"};
        List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
        for(OperationMaintenanceOrder order : newOrderList){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("project_name",order.getOrderProjectName());
            map.put("service_name",serviceNameArr[(int)order.getServiceType()]);
            map.put("order_code",order.getOrderCode());
            map.put("service_time",order.getPlannedTime());
            if(StringUtils.isNotBlank(order.getEngineer())) {
                map.put("group_name", order.getEngineer());
            }else{
                map.put("group_name", "-");
            }
            orderTypeName = orderNameArr[(int)order.getOrderType()];
            map.put("order_status",orderTypeName);
            map.put("order_id",order.getOrderId());
            resultList.add(map);
        }
        PageBean pageBean = new PageBean();
        pageBean.setList(resultList);
        pageBean.setTotalPage(pageInfo.getPages());
        pageBean.setTotalCount((int)pageInfo.getTotal());

        return pageBean;
    }

    public Map<String,String> findGroupName(String userId){
        Map<String,String> paramMap = new HashMap<String, String>();
        Map<String,String> param = new HashMap<String, String>();

         /*查询该员工所在组*/
        param.put("userId",userId);
        Map<String,Object> groupMap = operationMaintenanceDao.findGroupByUserId(param);
        paramMap.put("groupName","");
        if(groupMap != null && StringUtils.isNotBlank(groupMap.get("operation_group_name").toString())){
            paramMap.put("groupName",groupMap.get("operation_group_name").toString());
        }
        return paramMap;
    }

    /**
     * 查询工单以及项目内容
     * @param orderVo
     * @return
     */
    public Map<String, Object> findOrderContent(OrderVo orderVo) {
        Map<String,Object> map = new HashMap<String, Object>();
        //查询工单详情
        OperationMaintenanceOrder order = this.queryOrderDetail(orderVo);
        Map<String,Object> orderMap = new HashMap<String, Object>();
        orderMap.put("orderId", order.getOrderId());
        orderMap.put("orderName", order.getOrderName());
        orderMap.put("plannedTime", order.getPlannedTime());
        orderMap.put("equipmentInfo", order.getEquipmentInfo());
        orderMap.put("groupName", order.getEngineer());
        orderMap.put("address", order.getProjectDetailAddress());
        orderMap.put("phoneNum", order.getPhone());
        Map<String,Object> temp = new HashMap<String,Object>();
        List<String> nameList = new ArrayList<String>();
        List<String> phoneList = new ArrayList<String>();
        phoneList.add(order.getPhone());
        nameList.add(order.getContacts());
        temp.put("name",nameList);
        temp.put("phone",phoneList);
        orderMap.put("lxr", temp);
        orderMap.put("operProductsId",order.getOperProductsId());
        orderMap.put("customerId", order.getCustomerId());
        orderMap.put("finishStatus", false);

        /*工作状态需要通过轨迹中状态查询*/
        boolean flag = false;//ture已经开始工作  false 未开始工作
        Map<String,Object> recordsMap = new HashMap<String,Object>();
        recordsMap.put("orderCode",order.getOrderCode());
        recordsMap.put("status",6);
        int count = operationMaintenanceDao.findRecordsTypeCount(recordsMap);
        if(count >0){
            flag = true;
        }
        orderMap.put("workStatus",flag);
        boolean dispatchingStatus = true;//true显示派工,false不显示
        if(order.getOrderType() == 3 || order.getOrderType() == 4 ){
            orderMap.put("finishStatus", true);
            dispatchingStatus = false;
        }
        orderMap.put("dispatchingStatus",dispatchingStatus);
        map.put("order", orderMap);
        //查询工单对应的项目信息
        OperationMaintenanceProject project = this.findProjectById((int)order.getProjectId());
        Map<String,Object> projectMap = new HashMap<String, Object>();
        projectMap.put("projectName",project.getProjectName());
        projectMap.put("projectNo",project.getProjectNo());
        map.put("project",projectMap);
            /*查询某项目的执行情况*/
        Map<String,Object> executeMap = this.findProjectDetail(order);
        map.put("executeMap",executeMap);

        return map;
    }

    /**
     * 条件查询客户中的所有项目
     * @param paramMap
     * @return
     */
    public List<Map<String,Object>> findProjectListLike(Map<String, Object> paramMap) {
        List<OperationMaintenanceProject> mapList = operationMaintenanceDao.findProjectListLike(paramMap);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(mapList !=null && mapList.size()>0){
            for(OperationMaintenanceProject outProject : mapList) {
                List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
                for(OperationMaintenanceProject innerProject : mapList) {
                    if(outProject.getCustomerId() !="" && innerProject.getCustomerId() !="" && outProject.getCustomerId().equals(innerProject.getCustomerId())){
                        Map<String,Object> tempMap = new HashMap<String,Object>();
                        tempMap.put("customerId",innerProject.getCustomerId());
                        tempMap.put("projectId",innerProject.getProjectId());
                        tempMap.put("projectName",innerProject.getProjectName());
                        resultList.add(tempMap);
                        if(!resultMap.containsKey(outProject.getCustomerName())){
                            resultMap.put(outProject.getCustomerName(),resultList);
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        for(Map.Entry<String,Object> entry :resultMap.entrySet()){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("title",entry.getKey());
            map.put("list",entry.getValue());
            list.add(map);
        }
        return list;
    }

    /**
     * 开始工作
     * @param map
     */
    public void startWork(Map<String,Object> map) {
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId((Integer) map.get("orderId"));
        SecUser sercUser = userDao.getUserById(map.get("userId").toString());
        OperationMaintenanceOrder order1 = operationMaintenanceDao.queryOrderDetail(orderVo);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderCode", order1.getOrderCode());
        paramMap.put("status",6);
        int count  = operationMaintenanceDao.findRecordsTypeCount(paramMap);
        if(count == 0) {
            paramMap.put("operationPeople", sercUser.getUserName());
            paramMap.put("operationTime", new Date());
            paramMap.put("operationContent", "开始工作!");
            operationMaintenanceDao.addOrderRecords(paramMap);
        }
    }

    /**
     * 修改班组分组
     * @param paramMap
     */
    @Transactional
    public void updateGroup(Map<String, Object> paramMap) {
        int orderId = (Integer) paramMap.get("orderId");
        String groupName = paramMap.get("groupName").toString();
        Date date = DateUtils.convertTimeToDate((Long) paramMap.get("executeTime"));
        paramMap.put("executeTime",DateUtils.date2Str(date,"yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> tempMap = new HashMap<String,Object>();
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        SecUser secUser = userDao.getUserById(paramMap.get("userId").toString());
        paramMap.put("orderType",2);
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        tempMap.put("orderCode", order.getOrderCode());
        tempMap.put("operationPeople", secUser.getUserName());
        tempMap.put("operationTime", new Date());
        if(order.getOrderType() == 2) {
            tempMap.put("status",5);
            tempMap.put("operationContent", "工单派工待实施修改实施人为" + groupName + "！");
        }else if(order.getOrderType() == 1){
            tempMap.put("status",4);
            tempMap.put("operationContent", "工单派工待实施，指派实施人" + groupName + "！");
        }
        operationMaintenanceDao.addOrderRecords(tempMap);

        operationMaintenanceDao.updateGroup(paramMap);
    }

    /**
     * 主管评价
     * @param paramMap
     */
    public void managerRemark(Map<String, Object> paramMap) {
        int orderId = (Integer) paramMap.get("orderId");
        Map<String,Object> tempMap = new HashMap<String,Object>();
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        SecUser secUser = userDao.getUserById(paramMap.get("userId").toString());
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        tempMap.put("orderCode", order.getOrderCode());
        tempMap.put("status",9);
        tempMap.put("operationPeople", secUser.getUserName());
        tempMap.put("operationTime", new Date());
        tempMap.put("operationContent", "主管评价工单！" );
        operationMaintenanceDao.managerRemark(paramMap);
    }

    /**
     * 工单轨迹
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> orderLocus(Map<String, Object> paramMap) {
        int orderId = (Integer)paramMap.get("orderId");
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        OperationMaintenanceOrder order = operationMaintenanceDao.queryOrderDetail(orderVo);
        paramMap.put("orderCode",order.getOrderCode());
        List<Map<String,Object>> locusList = operationMaintenanceDao.orderLocus(paramMap);

        if(locusList == null){
            locusList = new ArrayList<Map<String,Object>>();
        }
        return locusList;
    }

    public void findOrderDetail(Map<String, Object> orderMap, OperationMaintenanceOrder order) {

        orderMap.put("cEvaluateLevel",order.getCEvaluateLevel() == "" || order.getCEvaluateLevel() == null ? 0:Integer.parseInt(order.getCEvaluateLevel()));
        orderMap.put("dEvaluateLevel",order.getDEvaluateLevel() == "" || order.getCEvaluateLevel() == null  ? 0:Integer.parseInt(order.getDEvaluateLevel()));
        orderMap.put("cEvaluateContent",order.getCEvaluateContent() == null ? "":order.getCEvaluateContent());
        orderMap.put("dEvaluateContent",order.getDEvaluateContent() == null ? "":order.getDEvaluateContent());
        boolean manageRemarkStatus = false;
        if(order.getOrderType() == 3 && order.getDEvaluateLevel() !=""){
            manageRemarkStatus = true;
        }
        orderMap.put("manageRemarkStatus",manageRemarkStatus);
        orderMap.put("groupName",order.getEngineer() == null ? "":order.getEngineer());

        CustCustomer custCustomer = customerService.findCustomer(order.getCustomerId());
        orderMap.put("customerName",custCustomer.getCustomerName());
        String[] serviceNameArr = {"","维保巡检","预防性试验","维护保养","维护维修","维护抢修","技术支持","工器具监测"};
        String[] orderNameArr = {"","待派工","待实施","待评价","完成"};
        orderMap.put("orderTypeName",orderNameArr[(int)order.getOrderType()]);
        orderMap.put("serviceTypeName",serviceNameArr[(int)order.getServiceType()]);
        boolean reportStatus = false;
        if(order.getOrderType() == 3 || order.getOrderType() == 4){
            reportStatus = true;
        }

        if(reportStatus) {
            if (StringUtils.isNotBlank(order.getWorkReportOne())) {
                //文档1
                List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getWorkReportOne());
                if (sysAccessoryList != null && sysAccessoryList.size() > 0) {
                    SysAccessory sysAccessory = sysAccessoryList.get(0);
                    File file = new File(uploadPath + sysAccessory.getFilePath());
                    String[] reportOnePdfArray = file.list();
                    Set<String> reportSet = new TreeSet<String>();
                    if (reportOnePdfArray != null && reportOnePdfArray.length > 0) {
                        for (int i = 0; i < reportOnePdfArray.length; i++) {
                            if (!reportOnePdfArray[i].contains(".pdf")) {
                                String path = sysAccessory.getFilePath().replace("\\", "/");
                                String reportImg = uploadHosts + path + "/" + reportOnePdfArray[i];
                                reportSet.add(reportImg);
                            }
                        }
                    }
                    orderMap.put("orderReport", reportSet);
                    if(reportSet != null && reportSet.size() == 0){
                        reportStatus = false;
                    }
                }
            }
        }
        orderMap.put("reportStatus", reportStatus);
    }

    /**
     * 查询所有超时未完成数量
     * @param paramMap
     * @return
     */
    public int findAllOverTimeTaskCount(Map<String, String> paramMap) {
        paramMap.put("endTime",DateUtils.date2Str(new Date(),"yyyy-MM"));
        return operationMaintenanceDao.findAllOverTimeTaskCount(paramMap);
    }
}

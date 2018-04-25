package com.kexin.dkt.core.service.offlineOperation;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kexin.dkt.core.common.util.*;
import com.kexin.dkt.core.dao.offlineOperation.OfflineOperationDao;
import com.kexin.dkt.core.dao.operation.OperationMaintenanceDao;
import com.kexin.dkt.core.entity.file.SysAccessory;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceOrder;
import com.kexin.dkt.core.entity.operation.OperationMaintenanceProject;
import com.kexin.dkt.core.entity.vo.OrderVo;
import com.kexin.dkt.core.service.operation.OperationMaintenanceService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import freemarker.template.TemplateException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

@Service
public class OfflineOperationService {

    @Autowired
    private OfflineOperationDao offlineOperationDao;

    @Autowired
    private OperationMaintenanceDao operationMaintenanceDao;

    @Autowired
    private OperationMaintenanceService operationMaintenanceService;

    @Value("${word_mkdir}")
    //报告路径
    private String wordPath;

    @Value("${img_mkdir}")
    //图片路径
    private String imgPath;

    @Value("${upload_hosts}")
    //访问路径前缀
    private String uploadHosts;

    @Value("${upload_path}")
    //绝对路径前缀
    private String uploadPath;

    @Value("${pdf_mkdir}")
    //pdf图片路径
    private String pdfImgPath;

    @Value("${corporateSeal_path}")
    private String corporateSealPath;

    /**
     * 生成离线报告列表
     *
     * @param paramMap //orderId 设备数据
     * @return
     */
    public Map<String, Object> productReport(Map<String, Object> paramMap) throws IOException, TemplateException {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        int orderId = (Integer) paramMap.get("orderId");
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("order_id", paramMap.get("orderId"));
        Map<String, Object> map4 = offlineOperationDao.findOffLineOrder(map3);

        List<String> errorList = new LinkedList<String>();
        List<Map<String,Object>> list1 = (List<Map<String,Object>>)paramMap.get("data");
        if(list1 != null && list1.size()>0){
            for(int i = 0;i<list1.size();i++) {
                Map<String, Object> map1 = list1.get(i);
                List<Map<String, Object>> list2 = (List<Map<String, Object>>) map1.get("data");
                if (list2 !=null && list2.size()>0) {
                    for (int j = 0; j < list2.size();j++) {
                        Map<String,Object> map2 = list2.get(j);
                        if(!(Boolean) map2.get("type")){
                            String errorMessage = map2.get("msg").toString();
                            String[] errorArr = errorMessage.split("/n");
                            for(String error : errorArr){
                                errorList.add(error);
                            }
                        }
                    }
                }
            }
        }


//        if (paramMap.get("dateImage") !=null && paramMap.get("dateImage").toString() !="" && paramMap.get("signatureImage") !=null && paramMap.get("signatureImage").toString() !=""){
            String directoryPath = wordPath + "/" + DateUtils.getCurrentDay();
            File newFile = new File(uploadPath + directoryPath);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            String suffixWordPath = directoryPath + "/" + System.currentTimeMillis() + ".doc";
            Map<String, Object> dataModel = new HashMap<String, Object>();

            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(orderId);
            /*查询工单详情*/
            OperationMaintenanceOrder order = operationMaintenanceService.queryOrderDetail(orderVo);
            /*查询项目信息*/
            OperationMaintenanceProject project = operationMaintenanceService.findProjectById((int) order.getProjectId());


            Map<String, Object> projectMessageMap = new HashMap<String, Object>();
            Map<String, Object> orderMessageMap = new HashMap<String, Object>();
            /*项目名称*/
            projectMessageMap.put("projectName", project.getProjectName());
            /*公司名称*/
            projectMessageMap.put("companyName", "广东可信电力股份有限公司");
            /*报告时间*/
            projectMessageMap.put("time", DateUtils.date2Str(new Date(), "yyyy年MM月dd日"));
            /*项目编号*/
            projectMessageMap.put("projectCode", project.getProjectNo());
            /*项目地址*/
            projectMessageMap.put("projectAddress", project.getProjectDetailAddress());
            /*开始时间*/
            projectMessageMap.put("startTime", DateUtils.date2Str(project.getStartTime(), "yyyy/MM/dd"));
            /*结束时间*/
            projectMessageMap.put("endTime", DateUtils.date2Str(project.getEndTime(), "yyyy/MM/dd"));
            /*项目执行情况*/
            /*查询执行次数*/
            Map<String, Object> projectMap = operationMaintenanceService.findProjectDetail(order);
        /*查询服务类型最新一次执行时间*/

            //1.巡检 2.试验 3.保养 4.维修 5.抢修 6.技术支持 7.工器具监测'
            OperationMaintenanceOrder paramOrder = new OperationMaintenanceOrder();
            paramOrder.setCustomerId(order.getCustomerId());
            paramOrder.setServiceType(1);
            OperationMaintenanceOrder xjOrder = operationMaintenanceService.findServiceTypeNewTime(paramOrder);
            //巡检总次数
            projectMessageMap.put("XJSumCount", projectMap.get("sumXJCount"));
            //巡检已完成次数
            projectMessageMap.put("XJDoneCount", projectMap.get("doneXJCount"));

            String xjTime = "-";
            if (xjOrder != null) {
                xjTime = DateUtils.date2Str(xjOrder.getPlannedTime(), "yyyy/MM/dd");
            }
            //巡检执行时间
            projectMessageMap.put("XJLastTime", xjTime);

            paramOrder.setServiceType(2);
            OperationMaintenanceOrder syOrder = operationMaintenanceService.findServiceTypeNewTime(paramOrder);
            //试验总次数
            projectMessageMap.put("SYSumCount", projectMap.get("sumSYCount"));
            //试验已完成次数
            projectMessageMap.put("SYDoneCount", projectMap.get("doneSYCount"));

            String syTime = "-";
            if (syOrder != null) {
                syTime = DateUtils.date2Str(syOrder.getPlannedTime(), "yyyy/MM/dd");
            }
            //试验执行时间
            projectMessageMap.put("SYLastTime", syTime);

            paramOrder.setServiceType(3);
            OperationMaintenanceOrder byOrder = operationMaintenanceService.findServiceTypeNewTime(paramOrder);
            //保养总次数
            projectMessageMap.put("BYSumCount", projectMap.get("sumBYCount"));
            //保养已完成次数
            projectMessageMap.put("BYDoneCount", projectMap.get("doneBYCount"));

            String byTime = "-";
            if (byOrder != null) {
                byTime = DateUtils.date2Str(byOrder.getPlannedTime(), "yyyy/MM/dd");
            }
            //保养执行时间
            projectMessageMap.put("BYLastTime", byTime);

            paramOrder.setServiceType(6);
            OperationMaintenanceOrder zcOrder = operationMaintenanceService.findServiceTypeNewTime(paramOrder);
            //技术支持总次数
            projectMessageMap.put("JSZCSumCount", projectMap.get("sumJSZCCount"));
            //技术支持已完成次数
            projectMessageMap.put("JSZCDoneCount", projectMap.get("doneJSZCCount"));

            String zcTime = "-";
            if (zcOrder != null) {
                zcTime = DateUtils.date2Str(zcOrder.getPlannedTime(), "yyyy/MM/dd");
            }
            //技术支持执行时间
            projectMessageMap.put("JSZCLastTime", zcTime);

            paramOrder.setServiceType(7);
            OperationMaintenanceOrder jcOrder = operationMaintenanceService.findServiceTypeNewTime(paramOrder);
            //工器具检测总次数
            projectMessageMap.put("JCSumCount", projectMap.get("sumGQJJCCount"));
            //工器具检测已完成次数
            projectMessageMap.put("JCDoneCount", projectMap.get("doneGQJJCCount"));
            String jcTime = "-";
            if (jcOrder != null) {
                jcTime = DateUtils.date2Str(jcOrder.getPlannedTime(), "yyyy/MM/dd");
            }
            //工器具检测执行时间
            projectMessageMap.put("JCLastTime", jcTime);

            String[] reportNameArr = {"", "维保巡检", "预防性试验", "维护保养", "维护维修", "维护抢修", "技术支持", "工器具监测"};

            /*设置数据*/
            dataModel.put("projectMessage", projectMessageMap);

            /*需要定义一个数组，存放报告名称，在根据服务类型分类过滤出对应的名称*/
            /*报告名称*/
            orderMessageMap.put("reportName", "【" + reportNameArr[(int) order.getServiceType()] + "】");
            /*设备信息*/
            String equipmentMessageStr = order.getEquipmentInfo();
            String[] equipmentMessageArr = equipmentMessageStr.split("\n");
            orderMessageMap.put("equipmentMessage", equipmentMessageArr);

            /*巡检时间*/
            orderMessageMap.put("workTime", new Date());

            Map<String, String> memberMap = new HashMap<String, String>();
            memberMap.put("groupName", order.getEngineer());
            /*需要查询分组人员，并用分号隔开*/
            List<Map<String, String>> memberList = operationMaintenanceService.findGroupMemberName(memberMap);
            String meberStr = "";
            if (memberList != null && memberList.size() > 0) {
                for (int i = 0; i < memberList.size(); i++) {
                    if (i == 0) {
                        meberStr += memberList.get(i);
                    } else {
                        meberStr += ("、" + memberList.get(i));
                    }
                }
            }

            /*工作人员*/
            orderMessageMap.put("worker", meberStr);
            /*联系电话*/
            orderMessageMap.put("phoneNum", order.getPhone());
            /*服务类型名称*/
            orderMessageMap.put("serviceTypeName", reportNameArr[(int) order.getServiceType()]);

            /*异常数量*/
            orderMessageMap.put("errorNum", 3);

            /*工单数据*/
            dataModel.put("orderMessage", orderMessageMap);

            /*保存异常数据*/
            dataModel.put("errorList",errorList);

            List<Object> roomMessageList = (List<Object>) paramMap.get("data");

                        /*保存客户签名与日期*/
         /*定义一个目录*/
            String fileUrl1 = DateUtils.getCurrentDay() + "/" + "dateImage";
            File newFile1 = new File(uploadPath + imgPath + "/" + fileUrl1);
            if (!newFile1.exists()) {
                newFile1.mkdirs();
            }

            boolean status1= false;
            boolean status2 = false;


            dataModel.put("dateImgBs", "");
            if (paramMap.get("dateImage") != null && paramMap.get("dateImage").toString() != "") {
                Base64 base1 = new Base64();
                 /*添加日期图片*/
                byte[] buff = base1.decode(paramMap.get("dateImage").toString());
                String fileName = System.currentTimeMillis() + ".jpg";
                FileOutputStream fos = new FileOutputStream(uploadPath + imgPath + "/" + fileUrl1 + "/" + fileName);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(buff);
                bos.close();
                order.setDatePhoto(imgPath + "/" + fileUrl1 + "/" + fileName);
                /*客户签名日期图片*/
                dataModel.put("dateImgBs", paramMap.get("dateImage").toString());
                status1 = true;
            }

            String fileUrl2 = DateUtils.getCurrentDay() + "/" + "signatureImage";
            File newFile2 = new File(uploadPath + imgPath + "/" + fileUrl2);
            if (!newFile2.exists()) {
                newFile2.mkdirs();
            }

            dataModel.put("signatureImgBs", "");
            if (paramMap.get("signatureImage") != null && paramMap.get("signatureImage").toString() != "") {
                Base64 base64 = new Base64();
             /*添加签名图片*/
                byte[] buff1 = base64.decode(paramMap.get("signatureImage").toString());
                String fileName1 = System.currentTimeMillis() + ".jpg";
                FileOutputStream fos1 = new FileOutputStream(uploadPath + imgPath + "/" + fileUrl2 + "/" + fileName1);
                BufferedOutputStream bos1 = new BufferedOutputStream(fos1);
                bos1.write(buff1);
                bos1.close();
                order.setSignaturePhoto(imgPath + "/" + fileUrl2 + "/" + fileName1);

                 /*客户签名图片*/
                dataModel.put("signatureImgBs", paramMap.get("signatureImage").toString());
                status2 = true;
            }


            /*保存关联id到off_line_order中*/
             Map<String, Object> orderMap = new HashMap<String, Object>();
            if(status1 && status2){
                orderMap.put("report_status", 1);
                order.setOrderType(3);
            }else{
                orderMap.put("report_status", 0);
            }

            File file2 = new File(uploadPath + "/" + corporateSealPath);
            FileInputStream inputStream2 = new FileInputStream(file2);
            byte[] buff2 = new byte[inputStream2.available()];
            int length2 = inputStream2.read(buff2);
            Base64 base64 = new Base64();
            String corporateSealImg = base64.encodeAsString(buff2);
            /*公司印章图片*/
            dataModel.put("corporateSealImg", corporateSealImg);

            /*电房记录数据*/
            dataModel.put("roomMessageList", roomMessageList);


            String newWordPath = FreeMarkerUtils.toWord(dataModel, uploadPath + suffixWordPath, "offLineWord.ftl");

            /*定义图片地址*/
            String outPath = pdfImgPath + "/" + DateUtils.getCurrentDay() + "/" + System.currentTimeMillis();
            String targetPath = outPath + "/" + System.currentTimeMillis() + ".pdf";
            File file4 = new File(uploadPath + outPath);
            if (!file4.exists()) {
                file4.mkdirs();
            }

            SysAccessory sysAccessory = null;
            String relationId = "";
            if (StringUtils.isNotBlank(order.getWorkReportOne())) {
                List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(order.getWorkReportOne());
                sysAccessory = sysAccessoryList.get(0);
                relationId = sysAccessory.getRelationId();
            } else {
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
            if (sysAccessory.getId() != 0) {
                operationMaintenanceDao.updateSysAccessory(sysAccessory);
            } else {
                operationMaintenanceDao.addSysAccessory(sysAccessory);
            }

            /*保存工单信息*/
            order.setWorkReportOne(relationId);
            operationMaintenanceDao.customerSignature(order);

            /*查询离线工单信息*/
            Map<String, Object> temp1 = new HashMap<String, Object>();
            temp1.put("order_id", paramMap.get("orderId"));
            Map<String, Object> orderMap1 = offlineOperationDao.findOffLineOrder(temp1);
            if (orderMap1 == null) {
                 /*插入数据*/
                addOffLineOrder(paramMap);
            }


            orderMap.put("order_id", order.getOrderId());
            orderMap.put("report_url", relationId);
            offlineOperationDao.updateOffLineOrder(orderMap);

            Word2PdfUtil.wordToPdf(newWordPath, uploadPath, targetPath);
            Pdf2PicUtils.pdf2Png(uploadPath + targetPath, uploadPath + outPath);

                /*返回报告*/
            if (StringUtils.isNotBlank(relationId)) {
                //文档1
                List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(relationId);
                if (sysAccessoryList != null && sysAccessoryList.size() > 0) {
                    SysAccessory sysAccessory2 = sysAccessoryList.get(0);
                    File file = new File(uploadPath + sysAccessory2.getFilePath());
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
                    resultMap.put("order_report", reportSet);
                }
            }
//        } else {
//            //查询到了工单直接返回工单图片
//
//            String reportUrl = map4.get("report_url").toString();
//            if (StringUtils.isNotBlank(reportUrl)) {
//                //文档1
//                List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(reportUrl);
//                if (sysAccessoryList != null && sysAccessoryList.size() > 0) {
//                    SysAccessory sysAccessory2 = sysAccessoryList.get(0);
//                    File file = new File(uploadPath + sysAccessory2.getFilePath());
//                    String[] reportOnePdfArray = file.list();
//                    Set<String> reportSet = new TreeSet<String>();
//                    if (reportOnePdfArray != null && reportOnePdfArray.length > 0) {
//                        for (int i = 0; i < reportOnePdfArray.length; i++) {
//                            if (!reportOnePdfArray[i].contains(".pdf")) {
//                                String path = sysAccessory2.getFilePath().replace("\\", "/");
//                                String reportImg = uploadHosts + path + "/" + reportOnePdfArray[i];
//                                reportSet.add(reportImg);
//                            }
//                        }
//                    }
//                    resultMap.put("order_report", reportSet);
//                }
//            }
//        }
        return resultMap;
    }


    /**
     * 确认提交
     *
     * @param paramMap
     * @throws IOException
     * @throws TemplateException
     */
    public void submitInfomation(Map<String, Object> paramMap) throws IOException, TemplateException {
        productReport(paramMap);
    }

    /**
     * 保存离线工单信息
     *
     * @param paramMap
     */
    @Transactional
    public void addOffLineOrder(Map<String, Object> paramMap) {
        /*取出参数*/
        String user_id = paramMap.get("userId").toString();
        int order_id = (Integer) paramMap.get("orderId");
        String order_name = paramMap.get("orderName").toString();

        Map<String, Object> paramMap1 = new HashMap<String, Object>();
        paramMap1.put("user_id", user_id);
        paramMap1.put("order_id", order_id);
        paramMap1.put("order_name", order_name);
        paramMap1.put("report_status", 0);
        paramMap1.put("report_url", "");
        offlineOperationDao.addOffLineOrder(paramMap1);

        long z_id = (Long) paramMap1.get("id");
        List<Map<String, Object>> list = (List<Map<String, Object>>) paramMap.get("data");
        for (Map<String, Object> tempMap1 : list) {
            List<Map<String, Object>> contentList = (List<Map<String, Object>>) tempMap1.get("data");
            for (Map<String, Object> tempMap : contentList) {
                Map<String, Object> tempMap2 = new HashMap<String, Object>();
                tempMap2.put("z_id", z_id);
                tempMap2.put("equipment_type_name", tempMap1.get("name"));
                tempMap2.put("work_content", tempMap.get("pro"));
                boolean flag = (Boolean) tempMap.get("type");
                int status = 0;
                if (flag) {
                    status = 1;
                }
                tempMap2.put("status", status);
                tempMap2.put("error_message", tempMap.get("msg"));
                offlineOperationDao.addWorkMessage(tempMap2);
            }
        }

    }

    /**
     * 保存离线工作项信息
     *
     * @param paramMap
     */
    public void addWorkMessage(Map<String, Object> paramMap) {
        offlineOperationDao.addWorkMessage(paramMap);
    }

    /**
     * 查询离线工单列表
     *
     * @param paramMap
     */
    public Object findOffLineOrderList(Map<String, Object> paramMap) {

        int pageNum = (Integer) paramMap.get("pageNum");
        int pageSize = (Integer) paramMap.get("pageSize");
        if(paramMap.get("service_type") !=null) {
            int serviceNum = 0;
            String service_type = paramMap.get("service_type").toString();
            if(service_type.equals("xj")){
                serviceNum = 1;
            }else if(service_type.equals("sy")){
                serviceNum = 2;
            }else if(service_type.equals("by")){
                serviceNum = 3;
            }
            paramMap.put("service_type",serviceNum);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Map<String, Object>> orderList = offlineOperationDao.findOffLineOrderList(paramMap);

        PageInfo pageInfo = new PageInfo(orderList);

        List<Map<String, Object>> resultList = pageInfo.getList();
        /* 封装数据*/
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> tempMap : resultList) {

            int service_type = Integer.parseInt(tempMap.get("service_type").toString());
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("order_id", tempMap.get("order_id"));
            resultMap.put("order_name", tempMap.get("order_name"));
            /*1:已生成报告 0未生成报告*/
            boolean report_status = false;
            if ((Integer) tempMap.get("report_status") == 1) {
                report_status = true;
            }
            resultMap.put("report_status", report_status);

            list.add(resultMap);
        }
        map.put("data", list);
        map.put("pageNum",pageInfo.getPageNum());
        map.put("pages",pageInfo.getPages());
        map.put("total",pageInfo.getTotal());

        return map;
    }

    /**
     * 查询离线工单详情
     *
     * @param paramMap
     */
    public Object findOffLineOrder(Map<String, Object> paramMap) {
        List<Map<String, Object>> contentList = offlineOperationDao.findOffLineContent(paramMap);

         /*总集合*/
        List<Object> list = new ArrayList<Object>();
        if (contentList != null && contentList.size() > 0) {
            /*获取集合第一个名称*/
            String equipmentTypeName = contentList.get(0).get("equipment_type_name").toString();
            /*封装参数map*/
            Map<String, Object> map = new HashMap<String, Object>();
            /*封装工作项集合*/
            List<Object> tempList = new ArrayList<Object>();
            boolean flag = false;
            /*异常数量*/
            int count = 0;
            for (Map<String, Object> tempMap : contentList) {
                if (equipmentTypeName.equals(tempMap.get("equipment_type_name").toString())) {
                    Map<String, Object> tempMap1 = new HashMap<String, Object>();
                    map.put("name", equipmentTypeName);
                    tempMap1.put("pro", tempMap.get("work_content"));
                    tempMap1.put("msg", "");
                    if ((Integer) tempMap.get("status") == 1) {
                        count++;
                        flag = true;
                        tempMap1.put("msg", tempMap.get("error_message"));
                    }
                    tempMap1.put("type", flag);
                    /*添加工作项map到list中*/
                    tempList.add(tempMap1);
                } else {
                    /*添加list集合到map中*/
                    map.put("data", tempList);
                    map.put("err", count);
                    list.add(map);
                    map = new HashMap<String, Object>();
                    tempList = new ArrayList<Object>();
                    count = 0;
                }
            }
            list.add(map);
        }
        return list;
    }


    /**
     * 查看工单报告
     * @param paramMap
     * @return
     */
    public Object findOrderReport(Map<String, Object> paramMap) {
        Map<String,Object> map = offlineOperationDao.findOffLineOrder(paramMap);
        //查询到了工单直接返回工单图片
        String reportUrl = map.get("report_url").toString();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if (StringUtils.isNotBlank(reportUrl)) {
            //文档1
            List<SysAccessory> sysAccessoryList = operationMaintenanceDao.findFileList(reportUrl);
            if (sysAccessoryList != null && sysAccessoryList.size() > 0) {
                SysAccessory sysAccessory2 = sysAccessoryList.get(0);
                File file = new File(uploadPath + sysAccessory2.getFilePath());
                String[] reportOnePdfArray = file.list();
                Set<String> reportSet = new TreeSet<String>();
                if (reportOnePdfArray != null && reportOnePdfArray.length > 0) {
                    for (int i = 0; i < reportOnePdfArray.length; i++) {
                        if (!reportOnePdfArray[i].contains(".pdf")) {
                            String path = sysAccessory2.getFilePath().replace("\\", "/");
                            String reportImg = uploadHosts + path + "/" + reportOnePdfArray[i];
                            reportSet.add(reportImg);
                        }
                    }
                }
                resultMap.put("order_report", reportSet);
            }
        }

        return resultMap ;
    }
}

package com.kexin.dkt.core.web.app;

import com.alibaba.fastjson.JSON;
import com.aspose.cells.License;
import com.aspose.cells.Workbook;
import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.common.util.Excel2PdfUtil;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileTest {

    @Test
    public void test(){

        List<String> reportList = new ArrayList<String>();
        File file = new File("E:\\test\\123");
        String[] nameArr = file.list();
        for(int i = 0;i<nameArr.length;i++){
            if(!nameArr[i].contains(".pdf")){
                String path = "http://120.76.46.59:8080/upload/pdf/2018-01-31/1517370723217/"+nameArr[i];
                reportList.add(path);
            }
        }
        for(String aa :reportList){
            System.out.println(aa);
        }
    }


    @Test
    public void test2(){
        Set<String> set = new TreeSet<>();
        set.add("1517370733675.PNG");
        set.add("1517370729488.PNG");
        set.add("1517370732600.PNG");
        set.add("1517370731523.PNG");

        for(String a :set){
            System.out.println(a);
        }
    }

    @Test
    public void test3() throws IOException {
        List<String> list = new LinkedList<>();
        Map<String,String> map = new TreeMap<>();
        byte[] aa = Base64.getDecoder().decode("aaaa");
        FileOutputStream outputStream = new FileOutputStream("");
        outputStream.write(aa);
        map.put("a1","aa");
        map.put("a2","bb");
        map.put("a3","cc");
        map.entrySet().forEach(a -> System.out.println(a.getKey()+"##"+a.getValue()));
    }


    public String getCodes(String startCity ,String comeCity){

        String[] startArr = startCity.split("/");
        String[] comeArr = comeCity.split("/");

        StringBuilder builder = new StringBuilder();
        String result = "";
        int index = 0;
        if(comeArr !=null && comeArr.length>0 && startArr !=null && startArr.length>0){
            for(String start : startArr){
                for (String end :comeArr){
                    index++;
                    result +=(start+"-"+end+",");
                }
            }

            for(String start : comeArr){
                for (String end :startArr){
                    index ++;
                    result +=(start+"-"+end+",");
                }
            }
        }
        result = result.substring(0,result.length()-1);
        System.out.println(index);
        return result;
    }

    @Test
    public void test4(){
        String data = "{orderId:11,data:[{\n" +
                "\"name\": \"高压开关柜\",\n" +
                "\"err\": 2,\n" +
                "\"data\": [{\n" +
                "\"pro\": \"设备铭牌、柜体名称是否完好。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:75\"\n" +
                "}, {\n" +
                "\"pro\": \"设备外壳是否完整。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:76\"\n" +
                "}, {\n" +
                "\"pro\": \"柜体、内部元器件是否有灰尘、污渍。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:77\"\n" +
                "}, {\n" +
                "\"pro\": \"带点指示显示是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:78\"\n" +
                "}, {\n" +
                "\"pro\": \"指示灯显示是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:79\"\n" +
                "}, {\n" +
                "\"pro\": \"电缆终端头是否有灰尘、爬电痕迹。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:80\"\n" +
                "}, {\n" +
                "\"pro\": \"断路器运行情况是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:81\"\n" +
                "}, {\n" +
                "\"pro\": \"断路器操作按钮是否完好。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:82\"\n" +
                "}, {\n" +
                "\"pro\": \"综合继保运行是否运行正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:83\"\n" +
                "}, {\n" +
                "\"pro\": \"综合继保电流、电压值显示是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:84\"\n" +
                "}, {\n" +
                "\"pro\": \"柜内底层电缆出口是否封堵。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:85\"\n" +
                "}],\n" +
                "\"$$hashKey\": \"object:44\",\n" +
                "\"show\": false\n" +
                "}, {\n" +
                "\"name\": \"低压开关柜\",\n" +
                "\"err\": 2,\n" +
                "\"data\": [{\n" +
                "\"pro\": \"外观是否损坏、积污，铭牌是否完好。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:144\"\n" +
                "}, {\n" +
                "\"pro\": \"开关柜内是否有发热、震动、异响。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:145\"\n" +
                "}, {\n" +
                "\"pro\": \"开关柜操作手柄是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:146\"\n" +
                "}, {\n" +
                "\"pro\": \"仪表显示是否正常，指示灯是否良好。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:147\"\n" +
                "}, {\n" +
                "\"pro\": \"各连接点接头、插件有无发热变色。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:148\"\n" +
                "}, {\n" +
                "\"pro\": \"柜内元器件有无尘污、裂纹，表面有无放电痕迹。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:149\"\n" +
                "}, {\n" +
                "\"pro\": \"柜内底层电缆出口是否封堵。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:150\"\n" +
                "}, {\n" +
                "\"pro\": \"刀闸开关是否合闸到位。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:151\"\n" +
                "}, {\n" +
                "\"pro\": \"电容柜内的电容器外壳是否良好，有无渗漏、膨胀情况，接触器是否工作正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:152\"\n" +
                "}, {\n" +
                "\"pro\": \"电缆温度是否正常，有无放电痕迹。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:153\"\n" +
                "}],\n" +
                "\"$$hashKey\": \"object:45\",\n" +
                "\"show\": false\n" +
                "}, {\n" +
                "\"name\": \"直流屏\",\n" +
                "\"err\": 0,\n" +
                "\"data\": [{\n" +
                "\"pro\": \"直流屏仪表指示是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:207\"\n" +
                "}, {\n" +
                "\"pro\": \"直流屏模块是否有告警、故障。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:208\"\n" +
                "}, {\n" +
                "\"pro\": \"蓄电池连接片是否有松动和腐蚀、放电现象。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:209\"\n" +
                "}, {\n" +
                "\"pro\": \"蓄电池外壳温度是否过高。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:210\"\n" +
                "}, {\n" +
                "\"pro\": \"蓄电池有无膨胀、变形、漏液现象。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:211\"\n" +
                "}],\n" +
                "\"$$hashKey\": \"object:46\",\n" +
                "\"show\": false\n" +
                "}, {\n" +
                "\"name\": \"变压器\",\n" +
                "\"err\": 2,\n" +
                "\"data\": [{\n" +
                "\"pro\": \"有无异常声响、振动和气味。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:240\"\n" +
                "}, {\n" +
                "\"pro\": \"变压器铭牌是否完好。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:241\"\n" +
                "}, {\n" +
                "\"pro\": \"变压器外表有无积污。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:242\"\n" +
                "}, {\n" +
                "\"pro\": \"变压器外表有无开裂，有无放电痕迹。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:243\"\n" +
                "}, {\n" +
                "\"pro\": \"高低压侧接头有无过热，电缆头有无漏电、爬点现象。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:244\"\n" +
                "}, {\n" +
                "\"pro\": \"中性点接地是否满足要求。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:245\"\n" +
                "}, {\n" +
                "\"pro\": \"护栏、门锁是否完好。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:246\"\n" +
                "}, {\n" +
                "\"pro\": \"温度控制器、冷却风机是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:247\"\n" +
                "}, {\n" +
                "\"pro\": \"变压器温度是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:248\"\n" +
                "}, {\n" +
                "\"pro\": \"变压器外壳有无锈蚀、破损。（箱变）\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:249\"\n" +
                "}, {\n" +
                "\"pro\": \"油位是否正常、有无渗油。（油变）\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:250\"\n" +
                "}],\n" +
                "\"$$hashKey\": \"object:47\",\n" +
                "\"show\": false\n" +
                "}, {\n" +
                "\"name\": \"配电房\",\n" +
                "\"err\": 0,\n" +
                "\"data\": [{\n" +
                "\"pro\": \"有无一次接线图。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:309\"\n" +
                "}, {\n" +
                "\"pro\": \"环境控制箱、风机、防潮灯、驱鼠器是否正常。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:310\"\n" +
                "}, {\n" +
                "\"pro\": \"现场有无开关操作工具、柜门钥匙。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:311\"\n" +
                "}, {\n" +
                "\"pro\": \"绝缘胶垫配置是否符合要求。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:312\"\n" +
                "}, {\n" +
                "\"pro\": \"电房有无积尘情况。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:313\"\n" +
                "}, {\n" +
                "\"pro\": \"电房内消防器材是否齐全。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:314\"\n" +
                "}, {\n" +
                "\"pro\": \"电房内有无杂物妨碍电气设备安全运行。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:315\"\n" +
                "}, {\n" +
                "\"pro\": \"有无防小动物挡板、有无鼠迹。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:316\"\n" +
                "}, {\n" +
                "\"pro\": \"绝缘安全工器具是否合格、是否在有效检测周期内。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:317\"\n" +
                "}, {\n" +
                "\"pro\": \"房内是否漏水、渗水现象。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:318\"\n" +
                "}, {\n" +
                "\"pro\": \"房门名称标示是否清晰。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:319\"\n" +
                "}, {\n" +
                "\"pro\": \"门窗有无损坏、锈蚀，门锁是否完好。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:320\"\n" +
                "}],\n" +
                "\"$$hashKey\": \"object:48\",\n" +
                "\"show\": false\n" +
                "}, {\n" +
                "\"name\": \"10kV电力电缆\",\n" +
                "\"err\": 1,\n" +
                "\"data\": [{\n" +
                "\"pro\": \"电缆沟及盖板是否完整。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:384\"\n" +
                "}, {\n" +
                "\"pro\": \"电缆头绝缘胶有无老化、套管是否完整。\",\n" +
                "\"type\": false,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:385\"\n" +
                "}, {\n" +
                "\"pro\": \"引线和链接点是否有变形、发热现象。\",\n" +
                "\"type\": true,\n" +
                "\"msg\": \"\",\n" +
                "\"$$hashKey\": \"object:386\"\n" +
                "}],\n" +
                "\"$$hashKey\": \"object:49\",\n" +
                "\"show\": false\n" +
                "}]}";
        //        String data = "[1,2,3,4,5]";
        Map<String,Object> map = (Map<String,Object>)JSON.parse(data);
    }


    @Test
    public void test5() throws ParseException, ClassNotFoundException, IllegalAccessException, InstantiationException {
     String[]aa={"135.42","133.53","43.80","37.59","145.51","144.30","136.02","78.90","75.04","51.39","35.48","148.95","80.09","138.38","147.97","143.69","48.90","41.31","155.90","139.26","135.69","127.80"};
     List<String> bb =  Arrays.asList(aa);
     String a = Collections.max(bb, new Comparator<String>() {
         @Override
         public int compare(String o1, String o2) {
             if(Double.parseDouble(o1) > Double.parseDouble(o2)) {
                 return 1 ;
             }else{
                 return -1;
             }
         }
     });
        System.out.println(bb.get(bb.size()-1));
        System.out.println(bb.get(0));

    }

    @Test
    public void test06(){

        Excel2PdfUtil.excel2pdf("C:\\Users\\kexin\\Desktop\\bb.xlsx","C:\\Users\\kexin\\Desktop\\ac.pdf");
    }

    /**
     * @param excelPath
     * @param pdfPath
     */
    public static void excel2pdf(String excelPath, String pdfPath) {
        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            Workbook wb = new Workbook(excelPath);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(new File(pdfPath));
            wb.save(fileOS, com.aspose.cells.SaveFormat.PDF);
            fileOS.close();
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = FileTest.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Test
    public void Test09(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String aa = format.format(new Date());
    }
}

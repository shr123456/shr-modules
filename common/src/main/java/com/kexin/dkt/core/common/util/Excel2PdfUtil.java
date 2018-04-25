package com.kexin.dkt.core.common.util;

import com.aspose.cells.Workbook;
import com.aspose.words.License;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Excel2PdfUtil {


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
            InputStream is = Excel2PdfUtil.class.getClassLoader().getResourceAsStream("license.xml"); //  license.xml应放在..\WebRoot\WEB-INF\classes路径下
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

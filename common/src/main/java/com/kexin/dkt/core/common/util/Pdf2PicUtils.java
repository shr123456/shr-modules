package com.kexin.dkt.core.common.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @auther shr
 * @date 2017/11/9
 * @time 17:12
 * @desc PDF转成图片的工具类
 **/
public class Pdf2PicUtils {

    /**
     * PDF转PNG图片
     * @param path
     */
    public static String pdf2Png(String path){
        try {

            //获取保存PDF转成图片的地址
            PropertiesUtil.initFile("classpath:config/file.properties");
            String imgPath = PropertiesUtil.getProperty("upload_path")+ PropertiesUtil.getProperty("pdf_mkdir")+ "/"+ DateUtils.getCurrentDay()+ "/" + System.currentTimeMillis() ;
            File newMkdir = new File(imgPath);
            if(!newMkdir.exists()){
                newMkdir.mkdirs();
            }

            return pdf2Png(path, imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * PDF转PNG图片
     * 指定存放地址 ，地址必须存在，否则报错
     * @param path
     * @param imgPath
     * @return
     */
    public static String pdf2Png(String path, String imgPath) throws IOException {
        File file = new File(path);
        PDDocument pdDocument = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(pdDocument);
        int pageCount = pdDocument.getNumberOfPages();
        for (int i = 0; i < pageCount; i++) {
            // 方式1,第二个参数是设置缩放比(即像素)
            BufferedImage image = renderer.renderImageWithDPI(i, 296);
            // 方式2,第二个参数是设置缩放比(即像素)
//             BufferedImage image = renderer.renderImage(i, 2.5f);
            String filePath = imgPath + "/" + System.currentTimeMillis() + ".PNG";
            ImageIO.write(image, "PNG", new File(filePath));
        }
        return imgPath;
    }

    /**
     * 多线程
     *  将PDF转成PDF图片
     * @param path
     * @return
     */
    public static void  pdf2PngThread(final String path,String outPath){
        try{
            //获取保存PDF转成图片的地址
            PropertiesUtil.initFile("classpath:config/file.properties");
            final String imgPath = PropertiesUtil.getProperty("upload_path")+ outPath;
            File newMkdir = new File(imgPath);
            if(!newMkdir.exists()){
                newMkdir.mkdirs();
            }

            //多线程导出图片
            Thread thread =  new Thread() {
                public void run() {
                    try{
                        pdf2Png(path, imgPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

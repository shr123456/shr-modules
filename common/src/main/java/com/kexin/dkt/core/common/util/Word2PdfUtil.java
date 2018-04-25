package com.kexin.dkt.core.common.util;

import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.google.common.annotations.VisibleForTesting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Word2PdfUtil {


    /**
     * 将word转换成pdf
     * @param sourcePath
     * @param targetPath
     * @return
     */
    public static void wordToPdf(String sourcePath,String uploadPath ,String targetPath){
        try{
            //设置一个字体目录
            FontSettings.setFontsFolder("/usr/share/fonts", true);

            //doc路径
            Document document = new Document(sourcePath);
            //pdf路径
            File outputFile = new File(uploadPath+targetPath);

            //操作文档保存
            document.save(outputFile.getAbsolutePath(), com.aspose.words.SaveFormat.PDF);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}

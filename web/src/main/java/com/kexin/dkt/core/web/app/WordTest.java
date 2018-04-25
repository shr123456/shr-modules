package com.kexin.dkt.core.web.app;

import com.aspose.words.Document;
import com.kexin.dkt.core.common.util.Word2PdfUtil;
import org.junit.Test;

import java.io.File;

public class WordTest {

    @Test
    public void test01(){
        Word2PdfUtil.wordToPdf("\\1516239251631.doc","E:\\test","\\test.pdf");
    }

    @Test
    public void test02() throws Exception {
        //doc路径
        Document document = new Document("E:\\test\\1516237647518.doc");
        //pdf路径
        File outputFile = new File("E:\\test\\test.pdf");

        //操作文档保存
        document.save(outputFile.getAbsolutePath(), com.aspose.words.SaveFormat.PDF);
    }
}

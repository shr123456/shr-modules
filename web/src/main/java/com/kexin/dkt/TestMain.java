package com.kexin.dkt;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @auther shr
 * @date 2017/10/17
 * @time 10:10
 * @desc
 **/
public class TestMain {
    public static void loadLoggerContext() {
        System.getProperties().put("logback.configurationFile", "./logback.xml");
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.setPrintStream(System.err);
        StatusPrinter.print(lc);
    }

    public static void main(String[] args) {
        try {
            loadLoggerContext();
            FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("./spring/applicationContext.xml");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

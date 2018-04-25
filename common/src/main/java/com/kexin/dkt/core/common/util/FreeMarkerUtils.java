package com.kexin.dkt.core.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class FreeMarkerUtils {

    /**
     * freeMarker转换成word
     * @param obj 封装数据
     * @param path 生成文件路径
     * @param templateName 模板名称
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String toWord(Object obj , String path ,String templateName ) throws IOException, TemplateException {

    /*创建Configuration配置信息对象,需要指定版本号*/
    Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setDefaultEncoding("utf-8");
            /*设置word模板的基础路径*/
            configuration.setClassForTemplateLoading(FreeMarkerUtils.class,"/ftl/");
    /*通过configuration对象获取模板文件对应的模板对象*/
    Template template = configuration.getTemplate(templateName, "utf-8");
            /*定义模板中所需要的数据*/

     /*填充模板
            * 参数一：数据模型
            * 参数二：输出流,可以输出到指定目录
            **/
        PrintWriter printWriter = new PrintWriter(new File(path),"utf-8");
        template.process(obj,printWriter);
        printWriter.flush();
        return path;
    }



}

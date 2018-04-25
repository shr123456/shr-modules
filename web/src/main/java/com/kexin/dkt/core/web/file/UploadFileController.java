package com.kexin.dkt.core.web.file;

import com.kexin.dkt.core.common.util.DateUtils;
import com.kexin.dkt.core.common.util.Pdf2PicUtils;
import com.kexin.dkt.core.common.util.PropertiesUtil;
import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.file.FileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @auther shr
 * @date 2017/11/2
 * @time 18:41
 * @desc : 用于上传文件
 **/
@Slf4j
@Controller
@RequestMapping("upload")
public class UploadFileController {

    @RequestMapping("imgUpload")
    @ResponseBody
    public ActionResult imgUpload(HttpServletRequest request) throws IllegalStateException, IOException{
        List<FileVo> fileVoList = new ArrayList<FileVo>();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            PropertiesUtil.initFile("classpath:config/file.properties");
            String hosts = PropertiesUtil.getProperty("upload_hosts");
            while(iter.hasNext()){
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String mkdirPath = PropertiesUtil.getProperty("img_mkdir")+ "/"+ DateUtils.getCurrentDay() ;
                    String filePath = PropertiesUtil.getProperty("upload_path") + mkdirPath;
                    File newFile = new File(filePath);
                    if(!newFile.exists()){
                        newFile.mkdirs();
                    }
                    int length = file.getOriginalFilename().lastIndexOf(".");
                    String suffix = file.getOriginalFilename().substring(length);
                    suffix = "/" + new Date().getTime() + suffix;
                    String path= filePath + suffix;
                    FileVo vo = new FileVo();
                    vo.setFileName(file.getOriginalFilename());
                    vo.setFilePath(mkdirPath +suffix);
                    vo.setHosts(hosts);
                    fileVoList.add(vo);
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        ActionResult actionResult = new ActionResult();
        actionResult.setContent(fileVoList);
        return actionResult;
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public ActionResult fileUpload(HttpServletRequest request) throws IllegalStateException, IOException{
        List<FileVo> fileVoList = new ArrayList<FileVo>();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            Iterator iter=multiRequest.getFileNames();
            PropertiesUtil.initFile("classpath:config/file.properties");
            String hosts = PropertiesUtil.getProperty("upload_hosts");
            while(iter.hasNext()){
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    String mkdirPath = PropertiesUtil.getProperty("file_mkdir")+ "/"+ DateUtils.getCurrentDay() ;
                    String filePath = PropertiesUtil.getProperty("upload_path") + mkdirPath;
                    File newFile = new File(filePath);
                    if(!newFile.exists()){
                        newFile.mkdirs();
                    }
                    int length = file.getOriginalFilename().lastIndexOf(".");
                    String suffix = file.getOriginalFilename().substring(length);
                    suffix = "/" + new Date().getTime() + suffix;
                    String path= filePath + suffix;
                    FileVo vo = new FileVo();
                    vo.setFileName(file.getOriginalFilename());
                    vo.setFilePath(mkdirPath +suffix);
                    vo.setHosts(hosts);
                    fileVoList.add(vo);
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        ActionResult actionResult = new ActionResult();
        actionResult.setContent(fileVoList);
        return actionResult;
    }
}

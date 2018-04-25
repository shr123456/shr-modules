package com.kexin.dkt.core.web.app.version;

import com.kexin.dkt.core.entity.common.ActionResult;
import com.kexin.dkt.core.entity.common.ResultCode;
import com.kexin.dkt.core.entity.version.VersionMessage;
import com.kexin.dkt.core.service.version.VersionService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.schema.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.swing.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kexin on 2017/11/21.
 */
@RestController
@Slf4j
@RequestMapping("app/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    @Value("${app_version}")
    //客户端app版本
    private String version;

    @Value("${app_url}")
    //客户端app下载路径
    private String url ;

    @Value("${customer_update_content}")
    //客户端app下载路径
    private String updateContent ;



    @Value("${opt_app_version}")
    //运维app版本号
    private String optAppVersion;

    @Value("${opt_app_url}")
    //运维app下载地址
    private String optAppUrl ;

    @Value("${opt_update_content}")
    //运维app下载地址
    private String optUpdateContent ;


    /**
     * 更新客户端app版本
     * @return
     */
    @RequestMapping("update")
    public ActionResult updateVersion(){
        ActionResult result = new ActionResult();
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("version",version);
            map.put("url",url);
            updateContent = new String(updateContent.getBytes("utf-8"));
            String htmlContent = htmlReplace(updateContent);
            map.put("updateContent",htmlContent);
            VersionMessage versionMessage = new VersionMessage();
            versionMessage.setVersionNum(version);
            versionMessage.setVersionContent(htmlContent);
            versionMessage.setVersionType(1);
            versionMessage.setUpdateUrl(url);
            versionMessage.setCreateTime(new Date());
            versionMessage.setDeleted(0);
            versionService.addVersionMessage(versionMessage);
            result.setContent(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新运维app版本
     * @return
     */
    @RequestMapping("updateOptApp")
    public ActionResult updateOptVersion(){
        ActionResult result = new ActionResult();
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("version",optAppVersion);
            map.put("url",optAppUrl);
            optUpdateContent = new String(optUpdateContent.getBytes("utf-8"));
            String htmlContent = htmlReplace(optUpdateContent);
            map.put("updateContent",htmlContent);
            VersionMessage versionMessage = new VersionMessage();
            versionMessage.setVersionNum(optAppVersion);
            versionMessage.setVersionContent(htmlContent);
            versionMessage.setUpdateUrl(optAppUrl);
            versionMessage.setVersionType(1);
            versionMessage.setCreateTime(new Date());
            versionMessage.setDeleted(0);
            versionService.addVersionMessage(versionMessage);
            result.setContent(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新运维app版本
     * @return
     */
    @RequestMapping("versionMessageList")
    public ActionResult versionMessageList(){
        ActionResult result = new ActionResult();
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("version",optAppVersion);
            map.put("url",optAppUrl);
            map.put("updateContent",optUpdateContent);
            String htmlContent = HtmlUtils.htmlEscapeHex(optUpdateContent);
            VersionMessage versionMessage = new VersionMessage();
            List<VersionMessage> versionMessageList = versionService.findVersionMessageList(versionMessage);
            if(versionMessageList !=null && versionMessageList.size()>0){
            }
            result.setContent(map);
        }catch (Exception e){
            result.setMessage("操作失败");
            result.setCode(ResultCode.ERROR.getCode());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    public String htmlReplace(String str){
        str = str.replace(" ","&nbsp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("&","&amp;")
                .replace("\n" ,"<br/>")
                .replace("\"","&quot;");

        return str;
    }

}

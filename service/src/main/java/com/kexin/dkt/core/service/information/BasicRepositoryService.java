package com.kexin.dkt.core.service.information;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kexin.dkt.core.dao.information.BasicRepositoryDao;
import com.kexin.dkt.core.entity.custcustomer.CustBusinessCoop;
import com.kexin.dkt.core.entity.school.BasicRepository;
import com.kexin.dkt.core.entity.vo.PageBean;
import com.kexin.dkt.core.entity.vo.PageBeanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kexin on 2017/11/27.
 */
@Service
public class BasicRepositoryService {

    @Value("${upload_hosts}")
    private String uploadHosts;

    @Autowired
    private BasicRepositoryDao basicRepositoryDao;

    /*获取方案信息，如无人托管/电力建设等*/
    public List<Map<String,Object>> loadSchemeinformation(String repositoryType) {
        List<Map<String,Object>> basicRepositoryList =  basicRepositoryDao.loadSchemeinformation(repositoryType);
        Map<String ,Map<String,Object>> map = new TreeMap<String, Map<String,Object>>();
        map.put("1",new HashMap<String, Object>());
        map.put("2",new HashMap<String, Object>());
        map.put("3",new HashMap<String, Object>());
        map.put("4",new HashMap<String, Object>());
        map.put("5",new HashMap<String, Object>());
        map.put("6",new HashMap<String, Object>());
        for(Map<String,Object> tempMap : basicRepositoryList){
            if("无人托管".equals(tempMap.get("repositoryName"))){
                map.put("1",tempMap);
            }else if("电力建设".equals(tempMap.get("repositoryName"))){
                map.put("2",tempMap);
            }else if("节能改造".equals(tempMap.get("repositoryName"))){
                map.put("3",tempMap);
            }else if("电气火灾".equals(tempMap.get("repositoryName"))){
                map.put("4",tempMap);
            }else if("光伏微网".equals(tempMap.get("repositoryName"))){
                map.put("5",tempMap);
            }else if("集抄秒付费".equals(tempMap.get("repositoryName"))){
                map.put("6",tempMap);
            }
        }
        List<Map<String ,Object>> list = new ArrayList<Map<String ,Object>>();
        for(Map.Entry<String,Map<String ,Object>> entry : map.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    /*查询政策资讯/分页查询*/
    public PageBean<Map<String,Object>> findPolicyinformationByPage(PageBeanVo pageBeanVo,String repositoryType) {
        PageBean<Map<String,Object>> pageBean = new PageBean<Map<String,Object>>();
        PageHelper.startPage(pageBeanVo.getPage(),pageBeanVo.getLimit());
        List<Map<String,Object>> basicRepositorieList = basicRepositoryDao.findPolicyinformationByPage(repositoryType);
        for(Map<String,Object> tempMap : basicRepositorieList) {
            tempMap.put("repositoryPicUrl", uploadHosts + tempMap.get("repositoryPicUrl"));
        }
        PageInfo pageInfo = new PageInfo(basicRepositorieList);
        pageBean.setList(pageInfo.getList());
        pageBean.setTotalCount((int)pageInfo.getTotal());
        pageBean.setTotalPage(pageInfo.getPages());

        return pageBean;
    }

    /*查询内容详情*/
    public BasicRepository getInformationById(String repositoryId) {
        BasicRepository basicRepository = basicRepositoryDao.getInformationById(repositoryId);
        basicRepository.setRepositoryPicUrl(uploadHosts+basicRepository.getRepositoryPicUrl());
        return basicRepository;
    }
}

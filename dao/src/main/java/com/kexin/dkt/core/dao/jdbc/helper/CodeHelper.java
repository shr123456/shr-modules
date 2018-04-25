package com.kexin.dkt.core.dao.jdbc.helper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 编码帮助类
 * <pre>
 *
 *
 *
 * </pre>
 *
 * @author: shr
 * @date: 17/12/27.
 */
@Slf4j
@Component("codeHelper")
public class CodeHelper {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public String getProjectCode(){
        Map<String, Object> map = jdbcTemplate.queryForMap("select f_get_projectcode()");
        String value = "";
        Set<String> keySet = map.keySet();
        for (String key:keySet) {
            value = map.get(key).toString();
        }
        return value;
    }

    /**
     *
     * @param stableName  模块首字母大写，如：报价 BJ
     * @return
     */
    public String getCommnCode(String stableName){
        Map<String, Object> map = jdbcTemplate.queryForMap("select f_get_commcode('"+stableName+"')");
        String value = "";
        Set<String> keySet = map.keySet();
        for (String key:keySet) {
            value = map.get(key).toString();
        }
        return value;
    }
}

package com.kexin.dkt.core.common.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @auther shr
 * @date 2017/10/18
 * @time 17:10
 * @desc
 **/
public class DynamicDataSource  extends AbstractRoutingDataSource {
    /**
     * 继承spring的数据源拓展类AbstractRoutingDataSource，实现做到动态切换数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSources();
    }
}

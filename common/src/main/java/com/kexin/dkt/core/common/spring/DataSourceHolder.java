package com.kexin.dkt.core.common.spring;

/**
 * @auther shr
 * @date 2017/10/18
 * @time 17:16
 * @desc 动态切换数据源
 **/
public class DataSourceHolder {
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    /**
     * 传入数据源名称
     * @param dataSource
     */
    public static void setDataSources(String dataSource) {
        dataSources.set(dataSource);
    }

    public static String getDataSources() {
        return dataSources.get();
    }
}

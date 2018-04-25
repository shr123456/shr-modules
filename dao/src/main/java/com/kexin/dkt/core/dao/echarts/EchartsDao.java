package com.kexin.dkt.core.dao.echarts;

import com.kexin.dkt.core.entity.collect.CollectTotalPAtEeDay;
import org.mybatis.spring.annotation.MapperScan;
import java.util.List;
/**
 * Created by admin on 2017/10/25.
 */
@MapperScan
public interface EchartsDao {
 /**
  * 获取某个设备总峰平谷数据
  * @param collectTotalPAtEeDay
  * @return
  */
 public List<CollectTotalPAtEeDay> getOption(CollectTotalPAtEeDay collectTotalPAtEeDay);

 /**
  * 获取某个用户当月总峰平谷数据
  * @param collectTotalPAtEeDay
  * @return
  */
 public List<CollectTotalPAtEeDay> getOptionbyMonth(CollectTotalPAtEeDay collectTotalPAtEeDay);


}

package com.kexin.dkt.core.common.util;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
*
* 消防云系统
*
* File: ValidationUtil.java
* Version: 0.1
* 
* ChinaNetCenter Inc.
* Copyright (C): 2016
*
* Description:
* 
* Action 校验类
*
* Notes:ValidationUtil.java 2016-5-27 张乐根
*/
public class ValidationUtil 
{
	/**
	 * 
	 * 验证值是否是空
	 * 
	 * @param object	验证值
	 * @return	验证值是否是空
	 */
	public static boolean isEmpty(Object object)
	{
		boolean isEmpty = false;
		
		if(object == null)
		{
			isEmpty = true;
		}
		//验证字符串类型
		else if(object instanceof String)
		{
			String validatedObject = (String)object;
			if(validatedObject == null || "".equals(validatedObject.trim()))
			{
				isEmpty = true;
			}
		}
		// 验证集合类型
		else if(object instanceof Collection)
		{
			Collection validatedObject = (Collection)object;
			if(validatedObject == null || validatedObject.size() == 0 )
			{
				isEmpty = true;
			}
		}
		// 验证Map类型
		else if(object instanceof Map)
		{
			Map validatedObject = (Map)object;
			if(validatedObject == null || validatedObject.size() == 0 )
			{
				isEmpty = true;
			}
		}
		// 验证日期类型
		else if(object instanceof Date)
		{
			Date validatedObject = (Date)object;
			if(validatedObject == null)
			{
				isEmpty = true;
			}
		}
		// 验证日期类型
		else if(object instanceof Timestamp)
		{
			Timestamp validatedObject = (Timestamp)object;
			if(validatedObject == null)
			{
				isEmpty = true;
			}
		}
		// 验证Set类型
		else if(object instanceof Set)
		{
			Set validatedObject = (Set)object;
			if(validatedObject == null || validatedObject.size() == 0)
			{
				isEmpty = true;
			}
		}
		
		return isEmpty;
	}
	
	/**
	 * 
	 * 验证数组是否为空
	 * 
	 * @param
	 * @return
	 */
	public static boolean isEmpty(Object[] objects)
	{
		boolean isEmpty = false;
		
		if(objects == null || objects.length == 0)
		{
			isEmpty = true;
		}
		
		return isEmpty;
	}
}
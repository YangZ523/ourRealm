/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.ourrealm.boys.YZ.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author ThinkGem
 * @version 2014-8-19
 */
public class IdGenerate {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 生成UUID, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

}

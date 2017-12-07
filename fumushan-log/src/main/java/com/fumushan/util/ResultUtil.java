package com.fumushan.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ResultUtil {

	public static final String SUCCESS_MSG = "数据加载成功";
	public static final String FAIL_MSG = "数据加载失败";
	public static final Integer SUCCESS_CODE = 100;
	public static final Integer FAIL_CODE = 100;

	private static SerializerFeature[] features = { SerializerFeature.WriteMapNullValue,
			SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse,
			SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect };

	/**
	 * 生成json返回结果
	 */
	public static String success(String msg, Object obj) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("code", SUCCESS_CODE);
		rs.put("msg", StringUtils.isNotEmpty(msg) ? msg : SUCCESS_MSG);
		rs.put("data", obj == null ? new Object() : obj);
		return JSON.toJSONString(rs, features);
	}

	/**
	 * 生成json返回结果
	 */
	public static String fail(String msg, Object obj) {
		Map<String, Object> rs = new HashMap<String, Object>();
		rs.put("code", FAIL_CODE);
		rs.put("msg", StringUtils.isNotEmpty(msg) ? msg : FAIL_MSG);
		rs.put("data", obj == null ? new Object() : obj);
		return JSON.toJSONString(rs, features);
	}

}

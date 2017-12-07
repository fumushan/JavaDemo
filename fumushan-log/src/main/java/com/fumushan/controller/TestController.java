package com.fumushan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fumushan.aop.LogDetail;
import com.fumushan.aop.LogDetail.HandleType;
import com.fumushan.model.TestModel;

@Controller
@RequestMapping(value = "test")
public class TestController {

	@RequestMapping(value = "save")
	@LogDetail(desc = "测试日志", handle = HandleType.SAVE, model = "后台", modelName = "#test.name")
	public String save(TestModel test) {
		return "test log";
	}
	
}

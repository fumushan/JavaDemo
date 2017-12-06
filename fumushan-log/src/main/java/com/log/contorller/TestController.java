package com.log.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.log.aop.LogDetail;
import com.log.aop.LogDetail.HandleType;
import com.log.model.TestModel;

@Controller
@RequestMapping(value = "test")
public class TestController {

	@RequestMapping(value = "save")
	@LogDetail(desc = "测试日志", handle = HandleType.SAVE, model = "后台", modelName = "#test.name")
	public String save(TestModel test) {
		return "test log";
	}
	
}

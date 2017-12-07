package com.fumushan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fumushan.model.LogModel;
import com.fumushan.service.LogService;
import com.fumushan.util.ResultUtil;

@Controller
@RequestMapping(value = "log")
public class LogController {

	@Autowired
	private LogService logService;

	@ResponseBody
	@RequestMapping(value = "findList")
	public String findList() {
		List<LogModel> list;
		try {
			list = logService.findList();
			return ResultUtil.success("查询成功", list);
		} catch (Exception e) {
			return ResultUtil.fail("查询失败", null);
		}
	}

}

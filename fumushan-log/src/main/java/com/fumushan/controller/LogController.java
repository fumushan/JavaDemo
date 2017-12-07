package com.fumushan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fumushan.model.LogModel;
import com.fumushan.service.LogService;

@Controller
@RequestMapping(value = "log")
public class LogController {

	@Autowired
	private LogService logService;

	@ResponseBody
	@RequestMapping(value = "findList")
	public List<LogModel> findList() {
		return logService.findList();
	}

}

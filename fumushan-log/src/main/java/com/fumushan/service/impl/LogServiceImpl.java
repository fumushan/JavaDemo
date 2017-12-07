package com.fumushan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fumushan.dao.LogMapper;
import com.fumushan.model.LogModel;
import com.fumushan.service.LogService;


@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper;

	@Override
	public void save(LogModel model) {
		logMapper.save(model);
	}

	@Override
	public List<LogModel> findList() {
		return logMapper.findList();
	}

}

package com.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.log.dao.LogMapper;
import com.log.model.LogModel;
import com.log.service.LogService;

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

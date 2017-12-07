package com.fumushan.service;

import java.util.List;

import com.fumushan.model.LogModel;

public interface LogService {

	/**
	 * 保存操作日志
	 */
	public void save(LogModel entity);

	/**
	 * 查询操作日志列表
	 */
	public List<LogModel> findList();

}

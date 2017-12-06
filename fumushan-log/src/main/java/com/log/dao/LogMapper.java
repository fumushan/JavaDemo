package com.log.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.log.model.LogModel;

@Repository
public interface LogMapper {

	public void save(LogModel mdoel);

	public List<LogModel> findList();

}

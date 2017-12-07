package com.fumushan.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fumushan.model.LogModel;

@Repository
public interface LogMapper {

	public void save(LogModel mdoel);

	public List<LogModel> findList();

}

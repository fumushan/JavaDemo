package com.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 操作日志记录
 * 
 * @author FUBINBIN
 */
@SpringBootApplication
@MapperScan(basePackages = { "com.log.dao" })
public class LogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogApplication.class, args);
	}

}

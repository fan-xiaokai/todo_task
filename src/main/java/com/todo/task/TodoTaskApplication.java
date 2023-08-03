package com.todo.task;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxiaokai
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.todo.task.web.**.mapper"})
public class TodoTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoTaskApplication.class, args);
	}

}

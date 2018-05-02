package com.song.export;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.song.export.dao")
@ServletComponentScan
public class ExportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExportApplication.class, args);
	}
}

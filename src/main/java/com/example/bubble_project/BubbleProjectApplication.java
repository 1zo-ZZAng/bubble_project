package com.example.bubble_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource(value = {"classpath:global.properties"}) // 직접 만든 환경설정파일 위치
@MapperScan(basePackages = {"com.example.mapper"}) // 매퍼 위치 설정
@ComponentScan(basePackages = {"com.example.controller",
								"com.example.controller.jpa",
								"com.example.controller.mybatis",
								"com.example.service",
								"com.example.service.jpa",
								"com.example.service.mybatis",
								"com.example.config",
								"com.example.restcontroller",
								"com.example.filter",
								"com.example.scheduler"}) // 컨트롤러, 서비스 위치, 시큐리티환경, 레스트컨트롤러, 필터, 스케쥴러 설정
@EntityScan(basePackages = {"com.example.entity"}) // 엔티티 위치
@EnableJpaRepositories(basePackages = {"com.example.repository"}) // 저장소 위치

@EnableScheduling //스케쥴링 사용
public class BubbleProjectApplication extends SpringBootServletInitializer {

	

	//배포
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(BubbleProjectApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BubbleProjectApplication.class, args);
	}

}

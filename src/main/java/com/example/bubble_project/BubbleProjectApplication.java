package com.example.bubble_project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@PropertySource(value= {"classpath:global.properties"}) // 직접만든 환경설정파일 위치
@MapperScan(basePackages = {"com.example.mapper"}) // 매퍼 위치 설정
@ComponentScan(basePackages = {"com.example.controller","com.example.controller.jpa", "com.example.controller.mybatis", "com.example.service", "com.example.config", "com.example.restcontroller", "com.example.filter"}) //컨트롤러, 서비스 위치, 시큐리티환결설정, 레스트컨트롤러, 필터


@EntityScan(basePackages = {"com.example.entity"}) //엔티티 위치
@EnableJpaRepositories(basePackages = {"com.example.repository"}) //저장소 위치
public class BubbleProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BubbleProjectApplication.class, args);
	}

}

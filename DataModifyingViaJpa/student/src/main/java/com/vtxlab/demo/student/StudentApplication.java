package com.vtxlab.demo.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.vtxlab.demo.student.entity.Student;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication // =
// @SpringBootConfiguration (@Configuration) +
// @ComponentScan
// ↓↓↓↓↓↓↓↓↓↓↓↓
//(@Component =
// @Repository +
// @Service +
// @Controller
// @Configuration)


// @EnableAutoConfiguration (TBC)


// Autowired -> get something in the container


@Slf4j
public class StudentApplication {

	private static ConfigurableApplicationContext configurableApplicationContext;

	@Bean(name = "BeanForSteve")
	public Student getSteve() {
		return new Student();
	}


	public static void main(String[] args) {
		configurableApplicationContext = SpringApplication.run(StudentApplication.class, args);
		displayAllBeans();
	}

	public static void displayAllBeans() {
		String[] allBeanNames = configurableApplicationContext.getBeanDefinitionNames();
		for (String s : allBeanNames) {
			log.info("Bean = {}", s);
		}
	}



}

package com.ex.boot;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ex.boot.*"})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
	
	@Bean(name="httpClient")
	public HttpClient httpClient(){
		return HttpClients.createDefault();
	}
}

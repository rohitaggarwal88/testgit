package com.rohi.first.movie_catalog_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class DeptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeptServiceApplication.class, args);
	}
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
    /*public WebClient webclient() {
		//return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create())).build();
		return WebClient.builder().build();
	}
	@Bean
    @LoadBalanced
	public WebClient webClient() {
        //return WebClient.builder();
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(HttpClient.create()
				.resolver(DefaultAddressResolverGroup.INSTANCE))).build();
    }*/
}

package com.rohi.first.movie_catalog_service.config;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.netflix.discovery.converters.Auto;
import com.rohi.first.movie_catalog_service.client.MovieClient;
import com.rohi.first.movie_catalog_service.client.RatingClient;
import com.rohi.first.movie_catalog_service.models.UserRating;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;



@Configuration
public class WebClientConf {

	@Autowired
	private LoadBalancedExchangeFilterFunction fil;
		
	@Bean
	public WebClient ratingWebClient() {
	try {
		SslContext sslContext = SslContextBuilder
		        .forClient()
		        .trustManager(InsecureTrustManagerFactory.INSTANCE)
		        .build();

HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
	
		return WebClient.builder()
				.baseUrl("http://rating-data-service").clientConnector(new ReactorClientHttpConnector(httpClient))
				.filter(fil).build();
		} catch (SSLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	return null;
	}
	@Bean
	public RatingClient ratWebClientMethod() {
		HttpServiceProxyFactory httpServiceProxyFactory
		=HttpServiceProxyFactory.builderFor(WebClientAdapter.create(ratingWebClient())).build();
		return httpServiceProxyFactory.createClient(RatingClient.class);
	}
	@Bean
	public WebClient movieWebClient() {
		SslContext sslContext;
		try {
			sslContext = SslContextBuilder
			        .forClient()
			        .trustManager(InsecureTrustManagerFactory.INSTANCE)
			        .build();
	HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
		return WebClient.builder()
				.baseUrl("http://movie-info-service").clientConnector(new ReactorClientHttpConnector(httpClient))
				.filter(fil).build();
		} catch (SSLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Bean
	public MovieClient movWebClientMethod() {
		HttpServiceProxyFactory httpServiceProxyFactory
		=HttpServiceProxyFactory.builderFor(WebClientAdapter.create(movieWebClient())).build();
		return httpServiceProxyFactory.createClient(MovieClient.class);
	}
}

    
package com.rohi.first.movie_catalog_service.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.rohi.first.movie_catalog_service.client.MovieClient;
import com.rohi.first.movie_catalog_service.client.RatingClient;
import com.rohi.first.movie_catalog_service.models.CatalogItem;
import com.rohi.first.movie_catalog_service.models.Movie;
import com.rohi.first.movie_catalog_service.models.UserRating;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/catalog")
public class CatalogResource {

    @Autowired
    private RestTemplate restTemplate;  
    @Autowired
    private RatingClient ratWebClient;
    @Autowired
    private MovieClient movWebClient;
    //@Autowired
    //private WebClient.Builder webClientBuilder;
    //private final WebClient.Builder webClientBuilder;
    int count=1;

    @Autowired
    public CatalogResource() {
	  }
    /*@Autowired
    public CatalogResource(WebClient.Builder webClient) {
	      this.webClientBuilder = webClient;
	  }*/
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        //UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/user/" + userId, UserRating.class);
    	UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/user/" + userId, UserRating.class);
    	
        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());

    }
    
    
    @RequestMapping("/web/{customuserId}")
    public List<CatalogItem> getCatalogCustom(@PathVariable("customuserId") String userId) {
    	
    	 //UserRating userRating = webClientBuilder.baseUrl("http://rating-data-service").build().get().uri("/ratingsdata/user/" + "123")
    	//UserRating userRating = webClient.get().uri("http://RATING-DATA-SERVICE/ratingsdata/user/" + "123").retrieve().bodyToMono(UserRating.class).block();

    	UserRating userRating = getUserRatings(userId);
    	
         return userRating.getRatings().stream()
                 .map(rating -> {
                    // Movie movie = webClientBuilder.baseUrl("http://movie-info-service").build().get().uri("/movies/" + rating.getMovieId())
                	 //Movie movie = webClient.get().uri("http://movie-info-service/movies/" + rating.getMovieId()).retrieve().bodyToMono(Movie.class).block();
                	 Movie movie =movWebClient.getMovieInfo(rating.getMovieId());
                     return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                 })
                 .collect(Collectors.toList());      
    }
    public UserRating getUserRatings(String userId) {
    	return ratWebClient.getUserRatings(userId);
    }
    
    /*@RequestMapping("/withoutp")
    public List<CatalogItem> getCatalogCustom() {
    	
    	 UserRating userRating = webClient.get().uri("http://RATING-DATA-SERVICE/ratingsdata/user/" + "123")
     			.retrieve().bodyToMono(UserRating.class).block();

         return userRating.getRatings().stream()
                 .map(rating -> {
                     Movie movie = webClient.get().uri("http://movie-info-service/movies/" + rating.getMovieId())
                 			.retrieve().bodyToMono(Movie.class).block();
                     return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                 })
                 .collect(Collectors.toList());      
    }*/
    @RequestMapping("/test")
    public String getCatalogCustom1 () {
    return "test pass";
    }
    
    @RequestMapping("/one/web")
    public UserRating getOneCatalog() {

    	/*HttpClient httpClient = HttpClient.create();
	    WebClient wc = WebClient.builder()
	      //.baseUrl("https://localhost:9994").apply(ssl.fromBundle("client"))
	    		.baseUrl("http://rating-data-service").clientConnector(new ReactorClientHttpConnector(httpClient))	    		
	      .defaultCookie("cookie-name", "cookie-value")
	      .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	      .build();
	    
	    
	    UserRating userRating = wc.get().uri("/ratingsdata/user/" + "123")
     			.retrieve().bodyToMono(UserRating.class).block();*/
    	UserRating userRating =ratWebClient.getUserRatings("123");

        return userRating;

    }
    @RequestMapping("/one/mov")
    public Movie getOneCatalogMov() {
    	
    	Movie userRating =movWebClient.getMovieInfo("123");

        return userRating;

    }
    @RequestMapping("/normal/{userId}")
    @CircuitBreaker(name = "inventory", fallbackMethod = "testfaMethod")
    public List<CatalogItem> getCatalogNormal(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/user/" + userId, UserRating.class);
    	//UserRating userRating = restTemplate.getForObject("http://localhost:4547/ratingsdata/user/" + userId, UserRating.class);
    	
        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());

    }
    @RequestMapping("/normalcircuitweb/{userId}")
    @CircuitBreaker(name = "inventory", fallbackMethod = "testfaMethod")
    //@Retry(name = "inventory", fallbackMethod = "testfaMethod")
    //@RateLimiter(name="inventory", fallbackMethod = "testfaMethod")
    public List<CatalogItem> getCatalogNormalcircuit(@PathVariable("userId") String userId) {       
  System.out.println("Retrying first times "+count++ + new Date());      
        UserRating userRating = getUserRatings(userId);
    	
        return userRating.getRatings().stream()
                .map(rating -> {
                   // Movie movie = webClientBuilder.baseUrl("http://movie-info-service").build().get().uri("/movies/" + rating.getMovieId())
               	 //Movie movie = webClient.get().uri("http://movie-info-service/movies/" + rating.getMovieId()).retrieve().bodyToMono(Movie.class).block();
               	 Movie movie =movWebClient.getMovieInfo(rating.getMovieId());
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());   

    }
    @RequestMapping("/normalcircuitrest/{userId}")
    //@CircuitBreaker(name = "inventory", fallbackMethod = "testfaMethod")
    @Bulkhead(name = "inventory", fallbackMethod = "testfaMethod")
    public List<CatalogItem> getCatalogNormalcircuitRest(@PathVariable("userId") String userId) {       
       
    	UserRating userRating = restTemplate.getForObject("http://rating-data-service/ratingsdata/user/" + userId, UserRating.class);
    	//UserRating userRating = restTemplate.getForObject("http://localhost:4547/ratingsdata/user/" + userId, UserRating.class);
        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());


    }
    public List<CatalogItem> testfaMethod(String userId,Exception ex){
    	List<CatalogItem> l=new ArrayList<>();
    	CatalogItem c=new CatalogItem("falba","fade",2);
    	l.add(c);
    	
    	return l;
    	
    }
}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/
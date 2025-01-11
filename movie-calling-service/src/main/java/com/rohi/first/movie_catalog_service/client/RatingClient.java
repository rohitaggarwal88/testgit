package com.rohi.first.movie_catalog_service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.rohi.first.movie_catalog_service.models.UserRating;



@HttpExchange
public interface RatingClient {

	
		@GetExchange("/ratingsdata/user/{userId}")
	    public UserRating getUserRatings(@PathVariable("userId") String userId) ;
	
}

    
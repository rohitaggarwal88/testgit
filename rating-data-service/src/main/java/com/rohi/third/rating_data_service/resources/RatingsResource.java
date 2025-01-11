package com.rohi.third.rating_data_service.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohi.third.rating_data_service.model.Rating;
import com.rohi.third.rating_data_service.model.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/movies/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

    }
    @RequestMapping("/tst/user")
    public UserRating getUserRatingsTest() {
        UserRating userRating = new UserRating();
        userRating.initData("123");
        return userRating;

    }

}

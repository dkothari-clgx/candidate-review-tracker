package com.corelogic.candidatereviewtracker.controller;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/applicants")
    public Review createReview(@RequestBody Review review){
        return reviewService.save(review);
    }
}

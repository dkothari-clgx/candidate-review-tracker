package com.corelogic.candidatereviewtracker.Controllers;

import com.corelogic.candidatereviewtracker.Models.Reviews;
import com.corelogic.candidatereviewtracker.Service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ReviewsController {

    private ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/applicants")
    public Reviews createReview(@RequestBody Reviews review){
        return reviewsService.save(review);
    }
}

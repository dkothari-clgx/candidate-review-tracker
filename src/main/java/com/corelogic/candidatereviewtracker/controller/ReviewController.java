package com.corelogic.candidatereviewtracker.controller;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/applicants")
    public Review createReview(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/applicants")
    public List<Review> listReview(@RequestParam(required = false) String candidateFirstName, @RequestParam(required = false) String candidateLastName) { return reviewService.findAll(candidateFirstName, candidateLastName); }
}

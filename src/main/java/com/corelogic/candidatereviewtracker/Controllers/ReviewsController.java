package com.corelogic.candidatereviewtracker.Controllers;

import com.corelogic.candidatereviewtracker.Models.Reviews;
import com.corelogic.candidatereviewtracker.Repositories.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ReviewsController {

    @Autowired
    ReviewsRepository repository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/applicants")
    @Transactional
    public Reviews createReview(@RequestBody Reviews review){
        return repository.save(review);
    }
}

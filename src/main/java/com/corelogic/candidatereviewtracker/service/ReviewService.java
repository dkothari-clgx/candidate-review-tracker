package com.corelogic.candidatereviewtracker.service;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }
}


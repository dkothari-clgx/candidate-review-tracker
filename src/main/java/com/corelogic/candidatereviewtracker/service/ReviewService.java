package com.corelogic.candidatereviewtracker.service;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {

        this.reviewRepository = reviewRepository;
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> findAll(String candidateFirstName, String candidateLastName) {
        if (candidateFirstName != null && candidateLastName != null) {
            return reviewRepository.findByCandidateFirstNameAndCandidateLastName(candidateFirstName, candidateLastName);
        }
        else if (candidateFirstName != null) {
            return reviewRepository.findByCandidateFirstName(candidateFirstName);
        } else if (candidateLastName != null) {
            return reviewRepository.findByCandidateLastName(candidateLastName);
        }
        return reviewRepository.findAll();
    }
}


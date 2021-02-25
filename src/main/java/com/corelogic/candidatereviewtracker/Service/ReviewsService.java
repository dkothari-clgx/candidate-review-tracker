package com.corelogic.candidatereviewtracker.Service;
import com.corelogic.candidatereviewtracker.Models.Reviews;
import com.corelogic.candidatereviewtracker.Repositories.ReviewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewsService {

    private ReviewsRepository reviewsRepository;

    public ReviewsService(ReviewsRepository reviewsRepository) {

        this.reviewsRepository = reviewsRepository;
    }

    @Transactional
    public Reviews save(Reviews review) {
        return reviewsRepository.save(review);
    }
}


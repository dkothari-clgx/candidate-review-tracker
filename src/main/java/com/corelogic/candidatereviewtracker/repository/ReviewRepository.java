package com.corelogic.candidatereviewtracker.repository;

import com.corelogic.candidatereviewtracker.model.Review;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaAuditing
public interface ReviewRepository extends CrudRepository<Review, UUID> {
    Review save(Review r);

    List<Review> findAll();

    Optional<Review> findById(UUID id);

    List<Review> findByCandidateFirstNameAndCandidateLastName(String candidateFirstName, String candidateLastName);
}

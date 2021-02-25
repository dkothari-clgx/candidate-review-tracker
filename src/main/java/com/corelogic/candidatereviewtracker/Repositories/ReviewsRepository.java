package com.corelogic.candidatereviewtracker.Repositories;
import com.corelogic.candidatereviewtracker.Models.Reviews;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewsRepository extends CrudRepository<Reviews, UUID> {
    public Reviews save(Reviews r);
    public List<Reviews> findAll();
    public Optional<Reviews> findById(UUID id);
    public Optional<Reviews> findFirstByCandidateFirstNameAndCandidateLastName(String firstName, String lastName);
}

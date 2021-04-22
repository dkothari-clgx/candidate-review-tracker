package com.corelogic.candidatereviewtracker.service;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.repository.ReviewRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUnitTest {
    @Mock
    private ReviewRepository reviewRepositoryMock;

    private Review review;
    private ReviewService subject;

    @BeforeEach
    void setUp() {
        review = new Review().builder()
                .candidateFirstName("John")
                .candidateLastName("Doe")
                .review("Test")
                .interviewerName("Adam")
                .interviewerEmail("Adam@corelogic.com")
                .hiringManagerName("Mateo")
                .hiringManagerEmail("Mateo@corelogic.com")
                .dateInterviewed(LocalDate.of(2021, 02, 25))
                .build();
        subject = new ReviewService(reviewRepositoryMock);
    }

    @Test
    public void ReviewsRepositorySaveReview() {
        when(reviewRepositoryMock.save(review)).thenReturn(any(Review.class));

        subject.save(review);

        verify(reviewRepositoryMock).save(review);
    }

    @Test
    public void ReviewsRepositoryFindAllReviews() {
        when(reviewRepositoryMock.findAll()).thenReturn(Lists.newArrayList(review));

        subject.findAll(null, null);

        verify(reviewRepositoryMock).findAll();
    }

    @Test
    public void ReviewsRespositoryFindAllByCandidateFirstName() {
        when(reviewRepositoryMock.findByCandidateFirstName("John")).thenReturn(Lists.newArrayList(review));

        subject.findAll("John", null);

        verify(reviewRepositoryMock).findByCandidateFirstName(eq("John"));
    }

    @Test
    public void ReviewsRespositoryFindAllByCandidateLastName() {
        when(reviewRepositoryMock.findByCandidateLastName("Doe")).thenReturn(Lists.newArrayList(review));

        subject.findAll(null, "Doe");

        verify(reviewRepositoryMock).findByCandidateLastName(eq("Doe"));
    }

    @Test
    public void ReviewsRespositoryFindAllByCandidateFirstAndLastName() {
        when(reviewRepositoryMock.findByCandidateFirstNameAndCandidateLastName("John", "Doe")).thenReturn(Lists.newArrayList(review));

        subject.findAll("John", "Doe");

        verify(reviewRepositoryMock).findByCandidateFirstNameAndCandidateLastName(eq("John"), eq("Doe"));
    }
}

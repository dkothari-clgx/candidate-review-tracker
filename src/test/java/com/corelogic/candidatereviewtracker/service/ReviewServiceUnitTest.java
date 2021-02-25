package com.corelogic.candidatereviewtracker.service;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUnitTest {
    @Mock
    private ReviewRepository reviewRepositoryMock;

    private Review reviewTest;
    private Review reviewReturn = new Review();

    @BeforeEach
    void setUp() {
        reviewTest = new Review().builder()
                .candidateFirstName("John")
                .candidateLastName("Doe")
                .review("Test")
                .interviewerName("Adam")
                .interviewerEmail("Adam@corelogic.com")
                .hiringManagerName("Mateo")
                .hiringManagerEmail("Mateo@corelogic.com")
                .dateInterviewed(LocalDate.of(2021, 02, 25))
                .build();
        ReflectionUtils.shallowCopyFieldState(reviewTest, reviewReturn);
    }

    @Test
    public void ReviewsRepositorySaveReview(){
        reviewReturn.setId(UUID.randomUUID());
        reviewReturn.setCreatedDate(LocalDateTime.now());
        reviewReturn.setUpdatedDate(LocalDateTime.now());

        ReviewService subject = new ReviewService(reviewRepositoryMock);
        when(reviewRepositoryMock.save(reviewTest)).thenReturn(reviewReturn);
        Review returnedValue = subject.save(reviewTest);
        verify(reviewRepositoryMock).save(reviewTest);
        assertThat(returnedValue).isEqualTo(reviewReturn);
    }
}

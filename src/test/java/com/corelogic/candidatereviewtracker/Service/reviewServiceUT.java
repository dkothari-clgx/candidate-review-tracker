package com.corelogic.candidatereviewtracker.Service;

import com.corelogic.candidatereviewtracker.Models.Reviews;
import com.corelogic.candidatereviewtracker.Repositories.ReviewsRepository;
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
public class reviewServiceUT {
    @Mock
    private ReviewsRepository reviewsRepositoryMock;

    private Reviews reviewTest;
    private Reviews reviewReturn = new Reviews();

    @BeforeEach
    void setUp() {
        reviewTest = new Reviews().builder()
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

        ReviewsService subject = new ReviewsService(reviewsRepositoryMock);
        when(reviewsRepositoryMock.save(reviewTest)).thenReturn(reviewReturn);
        Reviews returnedValue = subject.save(reviewTest);
        verify(reviewsRepositoryMock).save(reviewTest);
        assertThat(returnedValue).isEqualTo(reviewReturn);
    }
}

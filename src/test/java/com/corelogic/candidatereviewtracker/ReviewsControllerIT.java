package com.corelogic.candidatereviewtracker;

import com.corelogic.candidatereviewtracker.Models.Reviews;
import com.corelogic.candidatereviewtracker.Repositories.ReviewsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@EnableJpaAuditing
public class ReviewsControllerIT {

    @Autowired
    private MockMvc mockUser;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewsRepository repository;


    @Test
    public void createReviewsModelWhenUserPerformsPOSTToApplicantsEndpoint_ThenSaveReviewInDatabase() throws Exception {

        Reviews expectedReview = Reviews.builder()
                .candidateFirstName("John")
                .candidateLastName("Doe")
                .review("Test")
                .interviewerName("Adam")
                .interviewerEmail("Adam@corelogic.com")
                .hiringManagerName("Mateo")
                .hiringManagerEmail("Mateo@corelogic.com")
                .dateInterviewed(LocalDate.of(2021, 02, 25))
                .build();

        MvcResult mvcResult = mockUser.perform(post("/api/applicants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expectedReview)))
                .andExpect(status().isCreated())
                .andReturn();

        Reviews results = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Reviews.class);


        assertThat(results.getCandidateFirstName()).isEqualTo(expectedReview.getCandidateFirstName());
        assertThat(results.getCandidateLastName()).isEqualTo(expectedReview.getCandidateLastName());
        assertThat(results.getReview()).isEqualTo(expectedReview.getReview());
        assertThat(results.getInterviewerName()).isEqualTo(expectedReview.getInterviewerName());
        assertThat(results.getInterviewerEmail()).isEqualTo(expectedReview.getInterviewerEmail());
        assertThat(results.getHiringManagerName()).isEqualTo(expectedReview.getHiringManagerName());
        assertThat(results.getHiringManagerEmail()).isEqualTo(expectedReview.getHiringManagerEmail());
        assertThat(results.getDateInterviewed()).isEqualTo(expectedReview.getDateInterviewed());
        assertThat(results.getId().toString()).matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
        assertThat(results.getCreatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
        assertThat(results.getUpdatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));

        Reviews dbResults = repository.findFirstByCandidateFirstNameAndCandidateLastName(expectedReview.getCandidateFirstName(), expectedReview.getCandidateLastName()).get();

        assertThat(dbResults.getCandidateFirstName()).isEqualTo(expectedReview.getCandidateFirstName());
        assertThat(dbResults.getCandidateLastName()).isEqualTo(expectedReview.getCandidateLastName());
        assertThat(dbResults.getReview()).isEqualTo(expectedReview.getReview());
        assertThat(dbResults.getInterviewerName()).isEqualTo(expectedReview.getInterviewerName());
        assertThat(dbResults.getInterviewerEmail()).isEqualTo(expectedReview.getInterviewerEmail());
        assertThat(dbResults.getHiringManagerName()).isEqualTo(expectedReview.getHiringManagerName());
        assertThat(dbResults.getHiringManagerEmail()).isEqualTo(expectedReview.getHiringManagerEmail());
        assertThat(dbResults.getDateInterviewed()).isEqualTo(expectedReview.getDateInterviewed());
        assertThat(dbResults.getId().toString()).matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
        assertThat(dbResults.getCreatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
        assertThat(dbResults.getUpdatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
    }


}

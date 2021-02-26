package com.corelogic.candidatereviewtracker.controller;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockUser;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewRepository repository;

    @Test
    public void createReviewsModelWhenUserPerformsPOSTToApplicantsEndpoint_ThenSaveReviewInDatabase() throws Exception {

        Review expectedReview = Review.builder()
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

        Review response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);


        assertThat(response.getCandidateFirstName()).isEqualTo(expectedReview.getCandidateFirstName());
        assertThat(response.getCandidateLastName()).isEqualTo(expectedReview.getCandidateLastName());
        assertThat(response.getReview()).isEqualTo(expectedReview.getReview());
        assertThat(response.getInterviewerName()).isEqualTo(expectedReview.getInterviewerName());
        assertThat(response.getInterviewerEmail()).isEqualTo(expectedReview.getInterviewerEmail());
        assertThat(response.getHiringManagerName()).isEqualTo(expectedReview.getHiringManagerName());
        assertThat(response.getHiringManagerEmail()).isEqualTo(expectedReview.getHiringManagerEmail());
        assertThat(response.getDateInterviewed()).isEqualTo(expectedReview.getDateInterviewed());
        assertThat(response.getId().toString()).matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
        assertThat(response.getCreatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
        assertThat(response.getUpdatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));

        Review dbReview = repository.findByCandidateFirstNameAndCandidateLastName(expectedReview.getCandidateFirstName(), expectedReview.getCandidateLastName()).get(0);

        assertThat(dbReview.getCandidateFirstName()).isEqualTo(expectedReview.getCandidateFirstName());
        assertThat(dbReview.getCandidateLastName()).isEqualTo(expectedReview.getCandidateLastName());
        assertThat(dbReview.getReview()).isEqualTo(expectedReview.getReview());
        assertThat(dbReview.getInterviewerName()).isEqualTo(expectedReview.getInterviewerName());
        assertThat(dbReview.getInterviewerEmail()).isEqualTo(expectedReview.getInterviewerEmail());
        assertThat(dbReview.getHiringManagerName()).isEqualTo(expectedReview.getHiringManagerName());
        assertThat(dbReview.getHiringManagerEmail()).isEqualTo(expectedReview.getHiringManagerEmail());
        assertThat(dbReview.getDateInterviewed()).isEqualTo(expectedReview.getDateInterviewed());
        assertThat(dbReview.getId().toString()).matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");
        assertThat(dbReview.getCreatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
        assertThat(dbReview.getUpdatedDate()).isCloseTo(LocalDateTime.now(), within(1L, ChronoUnit.MINUTES));
    }


}

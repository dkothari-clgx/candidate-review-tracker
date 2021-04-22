package com.corelogic.candidatereviewtracker.controller;

import com.corelogic.candidatereviewtracker.model.Review;
import com.corelogic.candidatereviewtracker.repository.ReviewRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private static final String urlTemplate = "/api/applicants";

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void createReviewsModelWhenUserPerformsPOSTToApplicantsEndpoint_ThenSaveReviewInDatabase() throws Exception {
        Review expectedReview = buildReview();
        Review response = createReview(expectedReview);

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
        assertThat(response).isEqualTo(dbReview);
    }


    @Test
    public void listReviewsModelsWhenUserPerformsGETToApplicantsEndpoints_ExpectingEmptyResults() throws Exception {
        mockUser.perform(get(urlTemplate)).andExpect(status().isOk()).andExpect(content().string("[]"));
    }

    @Test
    public void listReviewsModelsWhenUserPerformsGETToApplicantsEndpoints_ExpectingResultsSizeOfOne() throws Exception {
        Review expectedReview = createReview(buildReview());

        MvcResult mvcResult = mockUser.perform(get(urlTemplate)).andExpect(status().isOk()).andReturn();

        List<Review> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Review>>() {});

        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0)).isEqualTo(expectedReview);
    }

    @Test
    public void listReviewsModelsWhenUserPerformsGETToApplicantsEndpoints_ExpectingResultsSizeOfMulitple() throws Exception {
        // Create a random amount of reviews
        List<Review> expectedReviews = new ArrayList<>();

        for (int i = 0; i < (Math.random() % 9) + 2; i++) {
            expectedReviews.add(createReview(buildReview()));
        }

        // Make the API call to get our reviews
        MvcResult mvcResult = mockUser.perform(get(urlTemplate)).andExpect(status().isOk()).andReturn();

        List<Review> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Review>>() {});

        // Verify the size of the list
        assertThat(response.size()).isEqualTo(expectedReviews.size());

        // Compare the elements
        assertThat(response).isEqualTo(expectedReviews);
    }

    private Review buildReview() {
        return Review.builder()
                .candidateFirstName("John")
                .candidateLastName("Doe")
                .review("Test")
                .interviewerName("Adam")
                .interviewerEmail("Adam@corelogic.com")
                .hiringManagerName("Mateo")
                .hiringManagerEmail("Mateo@corelogic.com")
                .dateInterviewed(LocalDate.of(2021, 02, 25))
                .build();
    }

    private Review createReview(Review expectedReview) throws Exception {

        MvcResult mvcResult = mockUser.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expectedReview)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Review.class);
    }


}

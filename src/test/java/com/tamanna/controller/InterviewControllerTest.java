package com.tamanna.controller;

import com.tamanna.service.InterviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InterviewControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private InterviewController controller;

    @Mock
    private InterviewService interviewService;

    final String controllerEndpoint = "/interviews";

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void should_retrieve_all_available_periods_of_time_given_candidates_and_interviewers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(controllerEndpoint + "/available")
                        .param("candidateId", "1")
                        .param("interviewerIds", "1", "2")
                        .param("weekDays", "THURSDAY", "TUESDAY")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }
}

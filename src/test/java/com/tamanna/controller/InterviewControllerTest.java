package com.tamanna.controller;

import com.tamanna.dto.AvailableInterviewerPeriodDTO;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InterviewControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private InterviewController controller;

    @Mock
    private InterviewService service;

    final String controllerEndpoint = "/interviews";

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void should_retrieve_all_available_periods_of_time_given_candidates_interviewers_weekDays() throws Exception {
        List<AvailableInterviewerPeriodDTO> availableInterviewerPeriodDTOS = buildAvailableInterviewerPeriodDTOS();

        given(service.getAvailablePeriodsOfTime(anyLong(), anyList(), anyList())).willReturn(availableInterviewerPeriodDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(controllerEndpoint + "/available")
                        .param("candidateId", "1")
                        .param("interviewerIds", "1", "2")
                        .param("weekDays", "THURSDAY", "TUESDAY")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Debora")))
                .andExpect(jsonPath("$[0].lastName", is("Silva")))
                .andExpect(jsonPath("$[0].date", is("2021-08-25T17:00:00.000Z")))
                .andExpect(jsonPath("$[1].firstName", is("Ingrid")))
                .andExpect(jsonPath("$[1].lastName", is("Martins")))
                .andExpect(jsonPath("$[1].date", is("2021-08-26T17:00:00.000Z")))
        ;

        verify(service, times(1)).getAvailablePeriodsOfTime(anyLong(), anyList(), anyList());

        verifyNoMoreInteractions(service);
    }

    private List<AvailableInterviewerPeriodDTO> buildAvailableInterviewerPeriodDTOS() {
        AvailableInterviewerPeriodDTO firstAvailablePeriod = AvailableInterviewerPeriodDTO.builder()
                .firstName("Debora")
                .lastName("Silva")
                .date(LocalDateTime.of(2021, 8, 25, 17, 0, 0))
                .build();

        AvailableInterviewerPeriodDTO secondAvailablePeriod = AvailableInterviewerPeriodDTO.builder()
                .firstName("Ingrid")
                .lastName("Martins")
                .date(LocalDateTime.of(2021, 8, 26, 17, 0, 0))
                .build();

        return Arrays.asList(firstAvailablePeriod, secondAvailablePeriod);
    }
}

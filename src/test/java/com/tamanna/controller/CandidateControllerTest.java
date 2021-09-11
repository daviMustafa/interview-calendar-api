package com.tamanna.controller;

import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.PeriodDTO;
import com.tamanna.service.CandidateService;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CandidateControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CandidateController controller;

    @Mock
    private CandidateService service;

    private ObjectMapper mapper;

    final String controllerEndpoint = "/candidates";

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void verify_fields_after_execute_get_candidates() throws Exception {
        List<CandidateDTO> candidateDTOS = buildListCandidatesDTO();

        given(service.findAllCandidates()).willReturn(candidateDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Davi")))
                .andExpect(jsonPath("$[0].lastName", is("Mustafa")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Carl")))
                .andExpect(jsonPath("$[1].lastName", is("Silva")))
        ;

        verify(service, times(1)).findAllCandidates();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void verify_fields_after_execute_get_interviews_by_candidate() throws Exception {
        List<InterviewDTO> interviewDTOS = buildListInterviewDTO();

        given(service.getAllScheduledInterviewsByCandidateId(1L)).willReturn(interviewDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(controllerEndpoint + "/{id}/interviews", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].interviewerId", is(1)))
                .andExpect(jsonPath("$[0].interviewerFirstName", is("Debora")))
                .andExpect(jsonPath("$[0].interviewerLastName", is("Silva")))
                .andExpect(jsonPath("$[0].startDateTime", is("2021-08-25T17:00:00.000Z")))
                .andExpect(jsonPath("$[0].endDateTime", is("2021-08-25T18:00:00.000Z")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].interviewerId", is(2)))
                .andExpect(jsonPath("$[1].interviewerFirstName", is("Ingrid")))
                .andExpect(jsonPath("$[1].interviewerLastName", is("Martins")))
                .andExpect(jsonPath("$[1].startDateTime", is("2021-08-26T17:00:00.000Z")))
                .andExpect(jsonPath("$[1].endDateTime", is("2021-08-26T18:00:00.000Z")))
        ;

        verify(service, times(1)).getAllScheduledInterviewsByCandidateId(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_save_candidate_return_200() throws Exception {
        CandidateDTO candidateDTO = new CandidateDTO("Davi", "Mustafa");

        String json = mapper.writeValueAsString(candidateDTO);

        doNothing().when(service).save(any(CandidateDTO.class));

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(any(CandidateDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_save_candidate_return_400() throws Exception {
        CandidateDTO candidateDTO = new CandidateDTO(null, null);

        String json = mapper.writeValueAsString(candidateDTO);

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).save(any(CandidateDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_save_candidate_return_415() throws Exception {
        CandidateDTO candidateDTO = new CandidateDTO("Davi", "Mustafa");

        String json = mapper.writeValueAsString(candidateDTO);

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_XML)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isUnsupportedMediaType());

        verify(service, times(0)).save(any(CandidateDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_save_candidate_available_period_return_400() throws Exception {
        PeriodDTO periodDTO = new PeriodDTO(null, null);

        String json = mapper.writeValueAsString(periodDTO);

        this.mockMvc.perform(post(controllerEndpoint + "/{id}/period", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).saveAvailablePeriod(anyLong(), any(PeriodDTO.class));
        verifyNoMoreInteractions(service);
    }

    private List<CandidateDTO> buildListCandidatesDTO() {
        CandidateDTO firstCandidate = CandidateDTO.builder().id(1L)
                .firstName("Davi").lastName("Mustafa").build();

        CandidateDTO secondCandidate = CandidateDTO.builder().id(2L)
                .firstName("Carl").lastName("Silva").build();

        return Arrays.asList(firstCandidate, secondCandidate);
    }

    private List<InterviewDTO> buildListInterviewDTO() {
        InterviewDTO firstInterview = InterviewDTO.builder().id(1L)
                .interviewerId(1L)
                .interviewerFirstName("Debora")
                .interviewerLastName("Silva")
                .startDateTime(LocalDateTime.of(2021, 8, 25, 17, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 8, 25, 18, 0, 0))
                .build();

        InterviewDTO secondInterview = InterviewDTO.builder().id(1L)
                .interviewerId(2L)
                .interviewerFirstName("Ingrid")
                .interviewerLastName("Martins")
                .startDateTime(LocalDateTime.of(2021, 8, 26, 17, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 8, 26, 18, 0, 0))
                .build();

        return Arrays.asList(firstInterview, secondInterview);
    }
}

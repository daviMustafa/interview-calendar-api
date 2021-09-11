package com.tamanna.controller;

import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.dto.PeriodDTO;
import com.tamanna.service.InterviewerService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InterviewerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private InterviewerController controller;

    @Mock
    private InterviewerService service;

    private ObjectMapper mapper;

    final String controllerEndpoint = "/interviewers";

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void verify_fields_after_execute_get_interviews_by_interviewers() throws Exception {
        List<InterviewDTO> interviewDTOS = buildListInterviewDTO();

        given(service.getAllScheduledInterviewsByInterviewerId(1L)).willReturn(interviewDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(controllerEndpoint + "/{id}/interviews", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].candidateId", is(1)))
                .andExpect(jsonPath("$[0].candidateFirstName", is("Davi")))
                .andExpect(jsonPath("$[0].candidateLastName", is("Mustafa")))
                .andExpect(jsonPath("$[0].startDateTime", is("2021-08-25T17:00:00.000Z")))
                .andExpect(jsonPath("$[0].endDateTime", is("2021-08-25T18:00:00.000Z")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].candidateId", is(2)))
                .andExpect(jsonPath("$[1].candidateFirstName", is("Carl")))
                .andExpect(jsonPath("$[1].candidateLastName", is("Silva")))
                .andExpect(jsonPath("$[1].startDateTime", is("2021-08-26T17:00:00.000Z")))
                .andExpect(jsonPath("$[1].endDateTime", is("2021-08-26T18:00:00.000Z")))
        ;

        verify(service, times(1)).getAllScheduledInterviewsByInterviewerId(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void save_interviewer_return_200() throws Exception {
        InterviewerDTO interviewerDTO = new InterviewerDTO("Debora", "Silva");

        String json = mapper.writeValueAsString(interviewerDTO);

        doNothing().when(service).save(any(InterviewerDTO.class));

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isCreated());

        verify(service, times(1)).save(any(InterviewerDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void save_interviewer_return_400() throws Exception {
        InterviewerDTO interviewerDTO = new InterviewerDTO(null, null);

        String json = mapper.writeValueAsString(interviewerDTO);

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(service, times(0)).save(any(InterviewerDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_save_candidate_return_415() throws Exception {
        InterviewerDTO interviewerDTO = new InterviewerDTO("Debora", "Silva");

        String json = mapper.writeValueAsString(interviewerDTO);

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_XML)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isUnsupportedMediaType());

        verify(service, times(0)).save(any(InterviewerDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_save_interviewer_available_period_return_400() throws Exception {
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

    private List<InterviewDTO> buildListInterviewDTO() {
        InterviewDTO firstInterview = InterviewDTO.builder().id(1L)
                .candidateId(1L)
                .candidateFirstName("Davi")
                .candidateLastName("Mustafa")
                .startDateTime(LocalDateTime.of(2021, 8, 25, 17, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 8, 25, 18, 0, 0))
                .build();

        InterviewDTO secondInterview = InterviewDTO.builder().id(1L)
                .candidateId(2L)
                .candidateFirstName("Carl")
                .candidateLastName("Silva")
                .startDateTime(LocalDateTime.of(2021, 8, 26, 17, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 8, 26, 18, 0, 0))
                .build();

        return Arrays.asList(firstInterview, secondInterview);
    }
}
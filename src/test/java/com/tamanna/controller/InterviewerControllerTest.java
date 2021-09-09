package com.tamanna.controller;

import com.google.gson.Gson;
import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.entity.Interviewer;
import com.tamanna.repository.InterviewerRepository;
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
    private InterviewerRepository repository;

    private Gson gson;

    final String controllerEndpoint = "/interviewers";

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        gson = new Gson();
    }

    @Test
    public void verify_fields_after_execute_get_interviews_by_interviewers() throws Exception {
        List<InterviewDTO> interviewDTOS = buildListInterviewDTO();

        given(repository.getAllScheduledInterviewsByInterviewerId(1L)).willReturn(interviewDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get(controllerEndpoint + "/{id}/interviews", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].candidate.id", is(1)))
                .andExpect(jsonPath("$[0].candidate.firstName", is("Davi")))
                .andExpect(jsonPath("$[0].candidate.lastName", is("Mustafa")))
                .andExpect(jsonPath("$[0].startDateTime", is("2021-08-25 17:00")))
                .andExpect(jsonPath("$[0].endDateTime", is("2021-08-25 18:00")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].candidate.id", is(2)))
                .andExpect(jsonPath("$[1].candidate.firstName", is("Carl")))
                .andExpect(jsonPath("$[1].candidate.lastName", is("Silva")))
                .andExpect(jsonPath("$[1].startDateTime", is("2021-08-26 17:00")))
                .andExpect(jsonPath("$[1].endDateTime", is("2021-08-26 18:00")))
        ;

        verify(repository, times(1)).getAllScheduledInterviewsByInterviewerId(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void test_save_interviewer_return_200() throws Exception {
        InterviewerDTO interviewerDTO = new InterviewerDTO("Debora", "Silva");
        Interviewer interviewer = new Interviewer(interviewerDTO.getFirstName(), interviewerDTO.getLastName());

        String json = gson.toJson(interviewerDTO);
        when(repository.save(any(Interviewer.class))).thenReturn(interviewer);

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(any(Interviewer.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void test_save_interviewer_return_400() throws Exception {
        InterviewerDTO interviewerDTO = new InterviewerDTO(null, null);

        String json = gson.toJson(interviewerDTO);

        this.mockMvc.perform(post(controllerEndpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(String.valueOf(Charset.defaultCharset()))
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(repository, times(0)).save(any(Interviewer.class));
        verifyNoMoreInteractions(repository);
    }

    private List<InterviewDTO> buildListInterviewDTO() {
        InterviewDTO firstInterview = InterviewDTO.builder().id(1L)
                .candidate(new CandidateDTO(1L, "Davi", "Mustafa"))
                .startDateTime(LocalDateTime.of(2021, 8, 25, 17, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 8, 25, 18, 0, 0))
                .build();

        InterviewDTO secondInterview = InterviewDTO.builder().id(1L)
                .candidate(new CandidateDTO(2L, "Carl", "Silva"))
                .startDateTime(LocalDateTime.of(2021, 8, 26, 17, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 8, 26, 18, 0, 0))
                .build();

        return Arrays.asList(firstInterview, secondInterview);
    }
}
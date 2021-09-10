package com.tamanna.controller;

import com.tamanna.dto.AvailableInterviewerPeriodDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.service.InterviewService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/interviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class InterviewController {

    private final InterviewService service;

    @Autowired
    public InterviewController(InterviewService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get availables interviewers given candidate available time.", response = AvailableInterviewerPeriodDTO.class, responseContainer = "List")
    @GetMapping(value = "/available")
    public ResponseEntity<?> getAvailablePeriodsOfTime(@RequestParam(value = "candidateId") Long candidateId,
                                                       @RequestParam(value = "interviewerIds") List<Long> interviewerIds,
                                                       @RequestParam(value = "weekDays") List<DayOfWeek> weekDays) {
        log.info("Listing availables periods of time for interview.");
        List<AvailableInterviewerPeriodDTO> result = service.getAvailablePeriodsOfTime(candidateId, interviewerIds, weekDays);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Save intervew.", response = InterviewDTO.class)
    @PostMapping()
    public ResponseEntity<?> saveInterview(@Valid @RequestBody InterviewDTO interviewDTO) throws Exception {
        log.info("Save interview.");
        InterviewDTO result = service.saveInterview(interviewDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}

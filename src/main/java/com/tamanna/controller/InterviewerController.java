package com.tamanna.controller;

import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.dto.PeriodDTO;
import com.tamanna.service.InterviewerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/interviewers", produces = MediaType.APPLICATION_JSON_VALUE)
public class InterviewerController {

    private final InterviewerService service;

    @Autowired
    public InterviewerController(InterviewerService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get all interviewers", response = InterviewerDTO.class, responseContainer = "List")
    @GetMapping()
    public ResponseEntity<?> getAllInterviewers(){
        log.info("Listing all interviewers.");
        List<InterviewerDTO> result = service.findAllInterviewers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get scheduled interviews given interviewer id.", response = InterviewDTO.class, responseContainer = "List")
    @GetMapping(value = "/{id}/interviews")
    public ResponseEntity<?> getAllScheduledInterviewsByInterviewerId(@PathVariable("id") Long id){
        log.info("Listing all interviews from a interviewer.");
        List<InterviewDTO> result = service.getAllScheduledInterviewsByInterviewerId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Save interviewer.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody InterviewerDTO interviewer){
        log.info("Saving interviewer.");
        service.save(interviewer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Save interviewer available period.")
    @PostMapping(value = "/{id}/period", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAvailablePeriod(@PathVariable("id") Long id,
                                                 @Valid @RequestBody PeriodDTO periodDTO) {
        log.info("Saving interviewer available period.");
        service.saveAvailablePeriod(id, periodDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

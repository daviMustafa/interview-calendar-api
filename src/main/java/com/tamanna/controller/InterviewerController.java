package com.tamanna.controller;

import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.entity.Interviewer;
import com.tamanna.repository.InterviewerRepository;
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

    //There was no need to create service at this point of the test. Only to test a controller and check
    //how many times this repository was called and its result
    private final InterviewerRepository repository;

    @Autowired
    public InterviewerController(InterviewerRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get all interviewers", response = InterviewerDTO.class, responseContainer = "List")
    @GetMapping()
    public ResponseEntity<?> getAllInterviewers(){
        log.info("Listing all interviewers.");
        List<InterviewerDTO> result = repository.findAllInterviewers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get scheduled interviews given interviewer id.", response = InterviewDTO.class, responseContainer = "List")
    @GetMapping(value = "/{id}/interviews")
    public ResponseEntity<?> getAllScheduledInterviewsByInterviewerId(@PathVariable("id") Long id){
        log.info("Listing all interviews from a interviewer.");
        List<InterviewDTO> result = repository.getAllScheduledInterviewsByInterviewerId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Save interviewer.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody InterviewerDTO interviewer){
        log.info("Saving interviewer.");
        repository.save(new Interviewer(interviewer.getFirstName(), interviewer.getLastName()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

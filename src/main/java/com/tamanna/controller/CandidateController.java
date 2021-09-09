package com.tamanna.controller;

import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.entity.Candidate;
import com.tamanna.repository.CandidateRepository;
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
@RequestMapping(value = "/candidates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CandidateController {

    //There was no need to create service at this point of the test. Only to test a controller and check
    //how many times this repository was called and its result
    private final CandidateRepository repository;

    @Autowired
    public CandidateController(CandidateRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Get all candidates", response = CandidateDTO.class, responseContainer = "List")
    @GetMapping()
    public ResponseEntity<?> getAllCandidates(){
        log.info("Listing all candidates.");
        List<CandidateDTO> result = repository.findAllCandidates();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get scheduled interviews given candidate id.", response = InterviewDTO.class, responseContainer = "List")
    @GetMapping(value = "/{id}/interviews")
    public ResponseEntity<?> getAllScheduledInterviewsByCandidateId(@PathVariable("id") Long id){
        log.info("Listing all interviews from a candidate.");
        List<InterviewDTO> result = repository.getAllScheduledInterviewsByCandidateId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Save candidate.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody CandidateDTO candidate){
        log.info("Saving candidate.");
        repository.save(new Candidate(candidate.getFirstName(), candidate.getLastName()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

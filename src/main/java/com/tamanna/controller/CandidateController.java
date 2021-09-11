package com.tamanna.controller;

import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.PeriodDTO;
import com.tamanna.service.CandidateService;
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

    private final CandidateService service;

    @Autowired
    public CandidateController(CandidateService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get all candidates", response = CandidateDTO.class, responseContainer = "List")
    @GetMapping()
    public ResponseEntity<?> getAllCandidates() {
        log.info("Listing all candidates.");
        List<CandidateDTO> result = service.findAllCandidates();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get scheduled interviews given candidate id.", response = InterviewDTO.class, responseContainer = "List")
    @GetMapping(value = "/{id}/interviews")
    public ResponseEntity<?> getAllScheduledInterviewsByCandidateId(@PathVariable("id") Long id) {
        log.info("Listing all interviews from a candidate.");
        List<InterviewDTO> result = service.getAllScheduledInterviewsByCandidateId(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Save candidate.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody CandidateDTO candidate) {
        log.info("Saving candidate.");
        service.save(candidate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Save candidate available period.")
    @PostMapping(value = "/{id}/period", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAvailablePeriod(@PathVariable("id") Long id,
                                                 @Valid @RequestBody PeriodDTO periodDTO) {
        log.info("Saving candidate available period.");
        service.saveAvailablePeriod(id, periodDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

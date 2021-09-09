package com.tamanna.repository;

import com.tamanna.dto.CandidateDTO;
import com.tamanna.dto.InterviewDTO;
import com.tamanna.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query("SELECT new com.tamanna.dto.InterviewDTO(iew.id, c.id, c.firstName, c.lastName, ier.id, " +
            "ier.firstName, ier.lastName, iew.startDateTime, iew.endDateTime) \n" +
            "FROM Candidate c \n" +
            "LEFT JOIN Interview iew ON c = iew.candidate \n" +
            "INNER JOIN Interviewer ier ON ier = iew.interviewer \n" +
            "WHERE c.id=:id ORDER BY ier.id")
    List<InterviewDTO> getAllScheduledInterviewsByCandidateId(@Param("id") Long id);

    @Query("SELECT new com.tamanna.dto.CandidateDTO(c.id, c.firstName, c.lastName)\n" +
            "FROM Candidate c")
    List<CandidateDTO> findAllCandidates();

    @Query("SELECT c FROM Candidate c JOIN FETCH c.candidateTimeSlots WHERE c.id =:id")
    Optional<Candidate> findCandidateTimeSlots(@Param("id") Long id);
}

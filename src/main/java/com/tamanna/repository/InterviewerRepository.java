package com.tamanna.repository;

import com.tamanna.dto.InterviewDTO;
import com.tamanna.dto.InterviewerDTO;
import com.tamanna.entity.Interviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

    @Query("SELECT new com.tamanna.dto.InterviewDTO(iew.id, c.id, c.firstName, c.lastName, ier.id, " +
            "ier.firstName, ier.lastName, iew.startDateTime, iew.endDateTime) \n" +
            "FROM Interviewer ier \n" +
            "LEFT JOIN Interview iew ON ier = iew.interviewer \n" +
            "INNER JOIN Candidate c ON c = iew.candidate \n" +
            "WHERE ier.id=:id ORDER BY ier.id")
    List<InterviewDTO> getAllScheduledInterviewsByInterviewerId(@Param("id") Long id);

    @Query("SELECT DISTINCT i FROM Interviewer i JOIN FETCH i.interviewerTimeSlots WHERE i.id in :interviewerIds")
    List<Interviewer> findInterviewersTimeSlots(@Param("interviewerIds") List<Long> interviewerIds);

    @Query("SELECT new com.tamanna.dto.InterviewerDTO(i.id, i.firstName, i.lastName)\n" +
            "FROM Interviewer i")
    List<InterviewerDTO> findAllInterviewers();

}

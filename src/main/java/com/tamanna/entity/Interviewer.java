package com.tamanna.entity;

import com.tamanna.dto.AvailableInterviewerPeriodDTO;
import com.tamanna.dto.InterviewerDTO;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "interviewer", schema = "public")
public class Interviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interviewer_generator")
    @SequenceGenerator(name = "interviewer_generator", sequenceName = "interviewer_id_seq", allocationSize = 1)
    public Long id;

    @NotNull
    @Column(name = "firstName", columnDefinition = "varchar(20)", unique = true)
    public String firstName;

    @NotNull
    @Column(name = "lastName", columnDefinition = "varchar(20)", unique = true)
    public String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewer", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Interview.class)
    private List<Interview> interviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewer", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = InterviewerTimeSlot.class)
    private List<InterviewerTimeSlot> interviewerTimeSlots;

    protected Interviewer() {
    }

    public Interviewer(@NotBlank String firstName, @NotBlank String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<InterviewerTimeSlot> getInterviewerTimeSlots() {
        return interviewerTimeSlots;
    }

    public List<AvailableInterviewerPeriodDTO> getInterviewerPeriodsList(TemporalQuery<Boolean> filterWeekDays) {
        List<AvailableInterviewerPeriodDTO> availableInterviewPeriodsDTO = new ArrayList<>();

        if (!CollectionUtils.isEmpty(interviewerTimeSlots)) {
            interviewerTimeSlots.forEach(interviewerTimeSlot ->
                    interviewerTimeSlot.getDateFrom().datesUntil(interviewerTimeSlot.getDateTo().plusDays(1)).collect(Collectors.toList())
                            .stream().filter(date -> date.query(filterWeekDays)).forEach(t -> {
                                LocalTime timePartStart = interviewerTimeSlot.getTimeFrom();
                                LocalTime timePartEnd = interviewerTimeSlot.getTimeTo();

                                //At this point consider hours in between? Remove plusHours(1) from while loop
                                while (timePartStart.isBefore(timePartEnd)) {
                                    LocalDateTime d = LocalDateTime.of(t, timePartStart);
                                    timePartStart = timePartStart.plusHours(1);

                                    InterviewerDTO interviewerDTO = new InterviewerDTO(interviewerTimeSlot.getInterviewer().getFirstName(),
                                            interviewerTimeSlot.getInterviewer().getLastName());
                                    availableInterviewPeriodsDTO.add(new AvailableInterviewerPeriodDTO(interviewerDTO, d));
                                }
                            }));
        }
        return availableInterviewPeriodsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interviewer interviewer = (Interviewer) o;
        return id.equals(interviewer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.tamanna.entity;

import com.tamanna.dto.AvailableCandidatePeriodDTO;
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
@Table(name = "candidate", schema = "public")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_generator")
    @SequenceGenerator(name = "candidate_generator", sequenceName = "candidate_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "firstName", columnDefinition = "varchar(20)", unique = true)
    private String firstName;

    @NotNull
    @Column(name = "lastName", columnDefinition = "varchar(20)", unique = true)
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = Interview.class)
    private List<Interview> interviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = CandidateTimeSlot.class)
    private List<CandidateTimeSlot> candidateTimeSlots;

    protected Candidate() {
    }

    public Candidate(@NotBlank String firstName, @NotBlank String lastName) {
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

    public List<AvailableCandidatePeriodDTO> getCandidatePeriodsList(TemporalQuery<Boolean> filterWeekDays) {
        List<AvailableCandidatePeriodDTO> availableCandidatePeriodsDTO = new ArrayList<>();

        if (!CollectionUtils.isEmpty(candidateTimeSlots)) {
            candidateTimeSlots.forEach(candidateTimeSlot ->
                    candidateTimeSlot.getDateFrom().datesUntil(candidateTimeSlot.getDateTo().plusDays(1)).collect(Collectors.toList())
                            .stream().filter(date -> date.query(filterWeekDays))
                            .forEach(t -> {
                                LocalTime timePartStart = candidateTimeSlot.getTimeFrom();
                                LocalTime timePartEnd = candidateTimeSlot.getTimeTo();

                                //At this point consider hours in between? Remove plusHours(1) from while loop
                                while (timePartStart.isBefore(timePartEnd.plusHours(1))) {
                                    LocalDateTime date = LocalDateTime.of(t, timePartStart);
                                    timePartStart = timePartStart.plusHours(1);

                                    availableCandidatePeriodsDTO.add(new AvailableCandidatePeriodDTO(candidateTimeSlot.getCandidate().getId(),
                                            candidateTimeSlot.getCandidate().getFirstName(), candidateTimeSlot.getCandidate().getLastName(),
                                            date));
                                }
                            }));
        }
        return availableCandidatePeriodsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id.equals(candidate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

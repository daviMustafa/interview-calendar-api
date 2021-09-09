package com.tamanna.entity;

import com.tamanna.entity.converters.LocalDateAttributeConverter;
import com.tamanna.entity.converters.LocalTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "candidate_time_slot", schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "dateFrom", "dateTo", "timeFrom", "timeTo"}))
public class CandidateTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_time_slot_generator")
    @SequenceGenerator(name = "candidate_time_slot_generator", sequenceName = "candidate_time_slot_id_seq", allocationSize = 1)
    public Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @Column(name = "dateFrom", columnDefinition = "DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate dateFrom;

    @Column(name = "dateTo", columnDefinition = "DATE")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate dateTo;

    @Column(name = "timeFrom", columnDefinition = "TIME")
    @Convert(converter = LocalTimeAttributeConverter.class)
    private LocalTime timeFrom;

    @Column(name = "timeTo", columnDefinition = "TIME")
    @Convert(converter = LocalTimeAttributeConverter.class)
    private LocalTime timeTo;

    public Candidate getCandidate() {
        return candidate;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateTimeSlot candidateTimeSlot = (CandidateTimeSlot) o;
        return id.equals(candidateTimeSlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

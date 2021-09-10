package com.tamanna.entity;

import com.tamanna.entity.converters.LocalDateAttributeConverter;
import com.tamanna.entity.converters.LocalTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "interviewer_time_slot", schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"interviewer_id", "dateFrom", "dateTo", "timeFrom", "timeTo"}))
public class InterviewerTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interviewer_time_slot_generator")
    @SequenceGenerator(name = "interviewer_time_slot_generator", sequenceName = "interviewer_time_slot_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewer_id", referencedColumnName = "id")
    private Interviewer interviewer;

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

    public Interviewer getInterviewer() {
        return interviewer;
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
        InterviewerTimeSlot interviewerTimeSlot = (InterviewerTimeSlot) o;
        return id.equals(interviewerTimeSlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

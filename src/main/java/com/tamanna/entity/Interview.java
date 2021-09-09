package com.tamanna.entity;

import com.tamanna.entity.converters.LocalDateTimeAttributeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalQuery;
import java.util.Objects;

@Entity
@Table(name = "interview", schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"candidate_id", "interviewer_id", "startDateTime", "endDateTime"}))
@Getter
@Setter
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interview_generator")
    @SequenceGenerator(name = "interview_generator", sequenceName = "interview_id_seq", allocationSize = 1)
    public Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewer_id", referencedColumnName = "id")
    private Interviewer interviewer;

    @Column(name = "startDateTime", columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime startDateTime;

    @Column(name = "endDateTime", columnDefinition = "TIMESTAMP")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime endDateTime;

    protected Interview(){}

    public Interview(@NotNull Candidate candidate, @NotNull Interviewer interviewer, @NotNull LocalDateTime startDateTime, @NotNull LocalDateTime endDateTime) {
        this.candidate = candidate;
        this.interviewer = interviewer;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @AssertFalse(message = "start date or end date is a weekend day.")
    public boolean isWeekend(){
        TemporalQuery<Boolean> weekend = t -> {
            DayOfWeek dow = DayOfWeek.from(t);
            return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
        };

        return startDateTime.query(weekend) || endDateTime.query(weekend);
    }

    @AssertTrue(message = "start time and end time must be within 1 hour range and beginning of any hour until the beginning of the next hour.")
    public boolean isValidTimeSpam(){
        return (startDateTime.getMinute() == 0 && startDateTime.getSecond() == 0)
                && (endDateTime.getMinute() == 0 && endDateTime.getSecond() == 0)
                && Duration.between(startDateTime, endDateTime).toHours() == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interview interview = (Interview) o;
        return id.equals(interview.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

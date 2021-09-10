package com.tamanna.entity;

import com.tamanna.entity.converters.LocalDateTimeAttributeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    private Long id;

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

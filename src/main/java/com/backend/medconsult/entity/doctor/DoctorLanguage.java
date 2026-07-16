package com.backend.medconsult.entity.doctor;

import jakarta.persistence.*;

import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.references.Language;
import com.backend.medconsult.enums.doctor.*;

@Entity
@Table(
    name = "doctor_languages",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_dlang_doc_lang", columnNames = {"doctor_id", "language_id"})
    },
    indexes = {
        @Index(name = "idx_dlang_lang", columnList = "language_id")
    }
)
public class DoctorLanguage {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_languages_doctor"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "language_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_languages_language"))
    private Language language;

    @Column(name = "proficiency", nullable = false, length = 30)
    private LanguageProficiency proficiency = LanguageProficiency.FLUENT;

    public DoctorLanguage() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageProficiency getProficiency() {
        return proficiency;
    }

    public void setProficiency(LanguageProficiency proficiency) {
        this.proficiency = proficiency;
    }
}

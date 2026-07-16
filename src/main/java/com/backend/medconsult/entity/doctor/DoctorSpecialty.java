package com.backend.medconsult.entity.doctor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.references.Specialty;
import com.backend.medconsult.entity.references.SubSpecialty;

@Entity
@Table(
    name = "doctor_specialties",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_dspec_doc_spec", columnNames = {"doctor_id", "specialty_id"})
    },
    indexes = {
        @Index(name = "idx_dspec_spec", columnList = "specialty_id")
    }
)
public class DoctorSpecialty {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_specialties_doctor"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "specialty_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_specialties_specialty"))
    private Specialty specialty;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "sub_specialty_id", foreignKey = @ForeignKey(name = "fk_doctor_specialties_sub_specialty"))
    private SubSpecialty subSpecialty;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DoctorSpecialty() {
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

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public SubSpecialty getSubSpecialty() {
        return subSpecialty;
    }

    public void setSubSpecialty(SubSpecialty subSpecialty) {
        this.subSpecialty = subSpecialty;
    }

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean primary) {
        isPrimary = primary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

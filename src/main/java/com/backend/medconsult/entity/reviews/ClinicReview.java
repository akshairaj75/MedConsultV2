package com.backend.medconsult.entity.reviews;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.entity.clinic.Clinic;
import com.backend.medconsult.entity.usersAndPatients.Patient;

@Entity
@Table(
    name = "clinic_reviews",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_crev_clinic_pat", columnNames = {"clinic_id", "patient_id", "appointment_id"})
    },
    indexes = {
        @Index(name = "idx_crev_clinic", columnList = "clinic_id, is_published, rating DESC")
    }
)
public class ClinicReview {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "review_id", nullable = false, updatable = false, length = 36)
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "fk_clinic_reviews_clinic"))
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_clinic_reviews_patient"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", foreignKey = @ForeignKey(name = "fk_clinic_reviews_appointment"))
    private Appointment appointment;

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Column(name = "rating_cleanliness")
    private Byte ratingCleanliness;

    @Column(name = "rating_staff")
    private Byte ratingStaff;

    @Column(name = "rating_wait")
    private Byte ratingWait;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public ClinicReview() {
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public Byte getRatingCleanliness() {
        return ratingCleanliness;
    }

    public void setRatingCleanliness(Byte ratingCleanliness) {
        this.ratingCleanliness = ratingCleanliness;
    }

    public Byte getRatingStaff() {
        return ratingStaff;
    }

    public void setRatingStaff(Byte ratingStaff) {
        this.ratingStaff = ratingStaff;
    }

    public Byte getRatingWait() {
        return ratingWait;
    }

    public void setRatingWait(Byte ratingWait) {
        this.ratingWait = ratingWait;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

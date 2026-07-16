package com.backend.medconsult.entity.reviews;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.backend.medconsult.entity.appointments.Appointment;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.entity.usersAndPatients.Patient;

@Entity
@Table(
    name = "doctor_reviews",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_rev_doc_pat_appt", columnNames = {"doctor_id", "patient_id", "appointment_id"})
    },
    indexes = {
        @Index(name = "idx_rev_doctor_pub", columnList = "doctor_id, is_published, rating DESC")
    }
)
public class DoctorReview {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "review_id", nullable = false, updatable = false, length = 36)
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_reviews_doctor"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctor_reviews_patient"))
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", foreignKey = @ForeignKey(name = "fk_doctor_reviews_appointment"))
    private Appointment appointment;

    @Column(name = "rating", nullable = false)
    private Byte rating;

    @Column(name = "rating_bedside")
    private Byte ratingBedside;

    @Column(name = "rating_knowledge")
    private Byte ratingKnowledge;

    @Column(name = "rating_wait")
    private Byte ratingWait;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "is_anonymous", nullable = false)
    private Boolean isAnonymous = false;

    @Column(name = "doctor_reply", columnDefinition = "TEXT")
    private String doctorReply;

    @Column(name = "doctor_replied_at")
    private LocalDateTime doctorRepliedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DoctorReview() {
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

    public Byte getRatingBedside() {
        return ratingBedside;
    }

    public void setRatingBedside(Byte ratingBedside) {
        this.ratingBedside = ratingBedside;
    }

    public Byte getRatingKnowledge() {
        return ratingKnowledge;
    }

    public void setRatingKnowledge(Byte ratingKnowledge) {
        this.ratingKnowledge = ratingKnowledge;
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

    public String getDoctorReply() {
        return doctorReply;
    }

    public void setDoctorReply(String doctorReply) {
        this.doctorReply = doctorReply;
    }

    public LocalDateTime getDoctorRepliedAt() {
        return doctorRepliedAt;
    }

    public void setDoctorRepliedAt(LocalDateTime doctorRepliedAt) {
        this.doctorRepliedAt = doctorRepliedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

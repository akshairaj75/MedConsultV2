package com.backend.medconsult.entity.clinic;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "clinic_operating_hours",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_hours_branch_day", columnNames = {"branch_id", "day_of_week"})
    },
    indexes = {
        @Index(name = "idx_hours_branch", columnList = "branch_id")
    }
)
public class ClinicOperatingHour {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "hours_id", nullable = false, updatable = false, length = 36)
    private UUID hoursId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "fk_clinic_operating_hours_branch"))
    private ClinicBranch branch;

    @Column(name = "day_of_week", nullable = false)
    private Byte dayOfWeek; // 0=Sunday ... 6=Saturday

    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed = false;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "break_start")
    private LocalTime breakStart;

    @Column(name = "break_end")
    private LocalTime breakEnd;

    @Column(name = "notes", length = 255)
    private String notes;

    public ClinicOperatingHour() {
    }

    public UUID getHoursId() {
        return hoursId;
    }

    public void setHoursId(UUID hoursId) {
        this.hoursId = hoursId;
    }

    public ClinicBranch getBranch() {
        return branch;
    }

    public void setBranch(ClinicBranch branch) {
        this.branch = branch;
    }

    public Byte getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Byte dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean closed) {
        isClosed = closed;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public LocalTime getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(LocalTime breakStart) {
        this.breakStart = breakStart;
    }

    public LocalTime getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(LocalTime breakEnd) {
        this.breakEnd = breakEnd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

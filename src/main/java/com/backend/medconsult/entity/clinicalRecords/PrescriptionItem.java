package com.backend.medconsult.entity.clinicalRecords;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "prescription_items",
    indexes = {
        @Index(name = "idx_rxitem_rx", columnList = "prescription_id, sort_order")
    }
)
public class PrescriptionItem {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "item_id", nullable = false, updatable = false, length = 36)
    private UUID itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false, foreignKey = @ForeignKey(name = "fk_prescription_items_prescription"))
    private Prescription prescription;

    @Column(name = "drug_name", nullable = false, length = 200)
    private String drugName;

    @Column(name = "dosage", nullable = false, length = 100)
    private String dosage;

    @Column(name = "route", length = 60)
    private String route;

    @Column(name = "frequency", nullable = false, length = 100)
    private String frequency;

    @Column(name = "duration_days")
    private Short durationDays;

    @Column(name = "quantity")
    private Short quantity;

    @Column(name = "refills_allowed", nullable = false)
    private Byte refillsAllowed = 0;

    @Column(name = "refills_used", nullable = false)
    private Byte refillsUsed = 0;

    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;

    @Column(name = "sort_order", nullable = false)
    private Byte sortOrder = 0;

    public PrescriptionItem() {
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Short getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Short durationDays) {
        this.durationDays = durationDays;
    }

    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public Byte getRefillsAllowed() {
        return refillsAllowed;
    }

    public void setRefillsAllowed(Byte refillsAllowed) {
        this.refillsAllowed = refillsAllowed;
    }

    public Byte getRefillsUsed() {
        return refillsUsed;
    }

    public void setRefillsUsed(Byte refillsUsed) {
        this.refillsUsed = refillsUsed;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public Byte getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }
}

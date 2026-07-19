package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.entity.clinicalRecords.PrescriptionItem;
import java.util.UUID;

public class PrescriptionItemResponseDto {

    private UUID itemId;
    private UUID prescriptionId;
    private String drugName;
    private String dosage;
    private String route;
    private String frequency;
    private Short durationDays;
    private Short quantity;
    private Byte refillsAllowed;
    private Byte refillsUsed;
    private String specialInstructions;
    private Byte sortOrder;

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public UUID getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(UUID prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public static PrescriptionItemResponseDto fromEntity(PrescriptionItem item) {
        if (item == null) {
            return null;
        }
        PrescriptionItemResponseDto dto = new PrescriptionItemResponseDto();
        dto.setItemId(item.getItemId());
        if (item.getPrescription() != null) {
            dto.setPrescriptionId(item.getPrescription().getPrescriptionId());
        }
        dto.setDrugName(item.getDrugName());
        dto.setDosage(item.getDosage());
        dto.setRoute(item.getRoute());
        dto.setFrequency(item.getFrequency());
        dto.setDurationDays(item.getDurationDays());
        dto.setQuantity(item.getQuantity());
        dto.setRefillsAllowed(item.getRefillsAllowed());
        dto.setRefillsUsed(item.getRefillsUsed());
        dto.setSpecialInstructions(item.getSpecialInstructions());
        dto.setSortOrder(item.getSortOrder());
        return dto;
    }
}

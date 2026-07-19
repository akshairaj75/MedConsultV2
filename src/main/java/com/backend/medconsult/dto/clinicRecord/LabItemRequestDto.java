package com.backend.medconsult.dto.clinicRecord;

import com.backend.medconsult.enums.clinicalRecords.LabItemFlag;
import java.math.BigDecimal;
import java.util.UUID;

public class LabItemRequestDto {

    private UUID labResultId;
    private String testName;
    private String loincCode;
    private String value;
    private String unit;
    private BigDecimal referenceLow;
    private BigDecimal referenceHigh;
    private LabItemFlag flag;
    private Short sortOrder;

    public UUID getLabResultId() {
        return labResultId;
    }

    public void setLabResultId(UUID labResultId) {
        this.labResultId = labResultId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getLoincCode() {
        return loincCode;
    }

    public void setLoincCode(String loincCode) {
        this.loincCode = loincCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getReferenceLow() {
        return referenceLow;
    }

    public void setReferenceLow(BigDecimal referenceLow) {
        this.referenceLow = referenceLow;
    }

    public BigDecimal getReferenceHigh() {
        return referenceHigh;
    }

    public void setReferenceHigh(BigDecimal referenceHigh) {
        this.referenceHigh = referenceHigh;
    }

    public LabItemFlag getFlag() {
        return flag;
    }

    public void setFlag(LabItemFlag flag) {
        this.flag = flag;
    }

    public Short getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }
}

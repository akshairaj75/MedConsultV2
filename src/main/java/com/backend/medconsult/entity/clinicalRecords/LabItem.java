package com.backend.medconsult.entity.clinicalRecords;

import jakarta.persistence.*;

import com.backend.medconsult.enums.clinicalRecords.*;
import java.math.BigDecimal;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "lab_items",
    indexes = {
        @Index(name = "idx_litem_result", columnList = "lab_result_id, sort_order")
    }
)
public class LabItem {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "item_id", nullable = false, updatable = false, length = 36)
    private UUID itemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lab_result_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lab_items_result"))
    private LabResult labResult;

    @Column(name = "test_name", nullable = false, length = 150)
    private String testName;

    @Column(name = "loinc_code", length = 20)
    private String loincCode;

    @Column(name = "value", nullable = false, length = 50)
    private String value;

    @Column(name = "unit", length = 30)
    private String unit;

    @Column(name = "reference_low", precision = 10, scale = 4)
    private BigDecimal referenceLow;

    @Column(name = "reference_high", precision = 10, scale = 4)
    private BigDecimal referenceHigh;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag", nullable = false)
    private LabItemFlag flag = LabItemFlag.NORMAL;

    @Column(name = "sort_order", nullable = false)
    private Short sortOrder = 0;

    public LabItem() {
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public LabResult getLabResult() {
        return labResult;
    }

    public void setLabResult(LabResult labResult) {
        this.labResult = labResult;
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

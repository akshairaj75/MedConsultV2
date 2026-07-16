package com.backend.medconsult.entity.platformAndCompliance;

import jakarta.persistence.*;

import com.backend.medconsult.entity.usersAndPatients.User;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "search_logs",
    indexes = {
        @Index(name = "idx_slog_created", columnList = "created_at"),
        @Index(name = "idx_slog_spec", columnList = "specialty_filter, created_at"),
        @Index(name = "idx_slog_locality", columnList = "locality_filter, created_at")
    }
)
public class SearchLog {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "search_id", nullable = false, updatable = false, length = 36)
    private UUID searchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_search_logs_user"))
    private User user;

    @Column(name = "query_text", length = 255)
    private String queryText;

    @Column(name = "specialty_filter", length = 60)
    private String specialtyFilter;

    @Column(name = "locality_filter", length = 100)
    private String localityFilter;

    @Column(name = "city_filter", length = 80)
    private String cityFilter;

    @Column(name = "insurance_filter", length = 100)
    private String insuranceFilter;

    @Column(name = "language_filter", length = 60)
    private String languageFilter;

    @Column(name = "sort_applied", length = 40)
    private String sortApplied;

    @Column(name = "result_count", nullable = false)
    private Short resultCount = 0;

    @Column(name = "result_clicked", nullable = false)
    private Boolean resultClicked = false;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public SearchLog() {
    }

    public UUID getSearchId() {
        return searchId;
    }

    public void setSearchId(UUID searchId) {
        this.searchId = searchId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getSpecialtyFilter() {
        return specialtyFilter;
    }

    public void setSpecialtyFilter(String specialtyFilter) {
        this.specialtyFilter = specialtyFilter;
    }

    public String getLocalityFilter() {
        return localityFilter;
    }

    public void setLocalityFilter(String localityFilter) {
        this.localityFilter = localityFilter;
    }

    public String getCityFilter() {
        return cityFilter;
    }

    public void setCityFilter(String cityFilter) {
        this.cityFilter = cityFilter;
    }

    public String getInsuranceFilter() {
        return insuranceFilter;
    }

    public void setInsuranceFilter(String insuranceFilter) {
        this.insuranceFilter = insuranceFilter;
    }

    public String getLanguageFilter() {
        return languageFilter;
    }

    public void setLanguageFilter(String languageFilter) {
        this.languageFilter = languageFilter;
    }

    public String getSortApplied() {
        return sortApplied;
    }

    public void setSortApplied(String sortApplied) {
        this.sortApplied = sortApplied;
    }

    public Short getResultCount() {
        return resultCount;
    }

    public void setResultCount(Short resultCount) {
        this.resultCount = resultCount;
    }

    public Boolean getResultClicked() {
        return resultClicked;
    }

    public void setResultClicked(Boolean resultClicked) {
        this.resultClicked = resultClicked;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

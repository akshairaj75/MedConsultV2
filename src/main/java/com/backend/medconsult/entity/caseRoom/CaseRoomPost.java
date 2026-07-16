package com.backend.medconsult.entity.caseRoom;

import jakarta.persistence.*;

import com.backend.medconsult.entity.platformAndCompliance.FileMetadata;
import com.backend.medconsult.entity.doctor.Doctor;
import com.backend.medconsult.enums.caseRoom.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "case_room_posts",
    indexes = {
        @Index(name = "idx_crp_room", columnList = "case_room_id, posted_at ASC"),
        @Index(name = "idx_crp_actions", columnList = "case_room_id, post_type, action_status")
    }
)
public class CaseRoomPost {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "post_id", nullable = false, updatable = false, length = 36)
    private UUID postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "case_room_id", nullable = false, foreignKey = @ForeignKey(name = "fk_case_room_posts_room"))
    private CaseRoom caseRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_case_room_posts_author"))
    private Doctor author;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type", nullable = false, columnDefinition = "ENUM('note','action_item','file','system_event')")
    private PostType postType = PostType.note;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_case_room_posts_file"))
    private FileMetadata file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_assigned_to", foreignKey = @ForeignKey(name = "fk_case_room_posts_assignee"))
    private Doctor actionAssignedTo;

    @Column(name = "action_due_date")
    private LocalDate actionDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_status", columnDefinition = "ENUM('pending','done','cancelled')")
    private ActionStatus actionStatus;

    @Column(name = "tags", columnDefinition = "json")
    private String tags;

    @Column(name = "posted_at", nullable = false, updatable = false)
    private LocalDateTime postedAt = LocalDateTime.now();

    @Column(name = "edited_at")
    private LocalDateTime editedAt;

    public CaseRoomPost() {
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public CaseRoom getCaseRoom() {
        return caseRoom;
    }

    public void setCaseRoom(CaseRoom caseRoom) {
        this.caseRoom = caseRoom;
    }

    public Doctor getAuthor() {
        return author;
    }

    public void setAuthor(Doctor author) {
        this.author = author;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public FileMetadata getFile() {
        return file;
    }

    public void setFile(FileMetadata file) {
        this.file = file;
    }

    public Doctor getActionAssignedTo() {
        return actionAssignedTo;
    }

    public void setActionAssignedTo(Doctor actionAssignedTo) {
        this.actionAssignedTo = actionAssignedTo;
    }

    public LocalDate getActionDueDate() {
        return actionDueDate;
    }

    public void setActionDueDate(LocalDate actionDueDate) {
        this.actionDueDate = actionDueDate;
    }

    public ActionStatus getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(ActionStatus actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }
}

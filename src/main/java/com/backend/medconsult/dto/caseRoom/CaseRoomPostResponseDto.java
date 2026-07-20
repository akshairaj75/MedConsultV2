package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.entity.caseRoom.CaseRoomPost;
import com.backend.medconsult.enums.caseRoom.ActionStatus;
import com.backend.medconsult.enums.caseRoom.PostType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CaseRoomPostResponseDto {

    private UUID postId;
    private UUID caseRoomId;
    private UUID authorId;
    private String authorName;
    private PostType postType;
    private String body;
    private UUID fileId;
    private UUID actionAssignedTo;
    private String actionAssignedToName;
    private LocalDate actionDueDate;
    private ActionStatus actionStatus;
    private String tags;
    private LocalDateTime postedAt;
    private LocalDateTime editedAt;

    public CaseRoomPostResponseDto() {
    }

    public static CaseRoomPostResponseDto fromEntity(CaseRoomPost entity) {
        if (entity == null) {
            return null;
        }
        CaseRoomPostResponseDto dto = new CaseRoomPostResponseDto();
        dto.setPostId(entity.getPostId());

        if (entity.getCaseRoom() != null) {
            dto.setCaseRoomId(entity.getCaseRoom().getCaseRoomId());
        }

        if (entity.getAuthor() != null) {
            dto.setAuthorId(entity.getAuthor().getDoctorId());
            if (entity.getAuthor().getUser() != null) {
                dto.setAuthorName(entity.getAuthor().getUser().getFullName());
            }
        }

        dto.setPostType(entity.getPostType());
        dto.setBody(entity.getBody());

        if (entity.getFile() != null) {
            dto.setFileId(entity.getFile().getFileId());
        }

        if (entity.getActionAssignedTo() != null) {
            dto.setActionAssignedTo(entity.getActionAssignedTo().getDoctorId());
            if (entity.getActionAssignedTo().getUser() != null) {
                dto.setActionAssignedToName(entity.getActionAssignedTo().getUser().getFullName());
            }
        }

        dto.setActionDueDate(entity.getActionDueDate());
        dto.setActionStatus(entity.getActionStatus());
        dto.setTags(entity.getTags());
        dto.setPostedAt(entity.getPostedAt());
        dto.setEditedAt(entity.getEditedAt());

        return dto;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getCaseRoomId() {
        return caseRoomId;
    }

    public void setCaseRoomId(UUID caseRoomId) {
        this.caseRoomId = caseRoomId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public UUID getActionAssignedTo() {
        return actionAssignedTo;
    }

    public void setActionAssignedTo(UUID actionAssignedTo) {
        this.actionAssignedTo = actionAssignedTo;
    }

    public String getActionAssignedToName() {
        return actionAssignedToName;
    }

    public void setActionAssignedToName(String actionAssignedToName) {
        this.actionAssignedToName = actionAssignedToName;
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

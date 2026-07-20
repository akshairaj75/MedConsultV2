package com.backend.medconsult.dto.caseRoom;

import com.backend.medconsult.enums.caseRoom.PostType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class CaseRoomPostRequestDto {

    @NotNull(message = "Case Room ID is required")
    private UUID caseRoomId;

    @NotNull(message = "Post type is required")
    private PostType postType = PostType.NOTE;

    private String body;

    private UUID fileId;

    private UUID actionAssignedTo;

    private LocalDate actionDueDate;

    private String tags;

    public CaseRoomPostRequestDto() {
    }

    public UUID getCaseRoomId() {
        return caseRoomId;
    }

    public void setCaseRoomId(UUID caseRoomId) {
        this.caseRoomId = caseRoomId;
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

    public LocalDate getActionDueDate() {
        return actionDueDate;
    }

    public void setActionDueDate(LocalDate actionDueDate) {
        this.actionDueDate = actionDueDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

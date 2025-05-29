package com.example.demo.dto;

import java.io.Serializable;

public class CheckpointDTO implements Serializable {
    private Long checkpointId;
    private String checkpointName;
    private String location;

    public CheckpointDTO() {}

    public CheckpointDTO(Long checkpointId, String checkpointName, String location) {
        this.checkpointId = checkpointId;
        this.checkpointName = checkpointName;
        this.location = location;
    }

    public Long getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(Long checkpointId) {
        this.checkpointId = checkpointId;
    }

    public String getCheckpointName() {
        return checkpointName;
    }

    public void setCheckpointName(String checkpointName) {
        this.checkpointName = checkpointName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
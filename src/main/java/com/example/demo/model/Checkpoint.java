package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "luggage_checkpoints")
public class Checkpoint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkpointId;

    @Column(name = "checkpoint_name", nullable = false)
    private String checkpointName;

    @Column(name = "location")
    private String location;

    public Checkpoint() {}

    public Checkpoint(String checkpointName, String location) {
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
package com.example.demo.model.entity;

import com.example.demo.util.BaggageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Baggage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double length;
    private double width;
    private double height;
    private double weight;

    @Enumerated(EnumType.STRING)
    private BaggageStatus status;
}

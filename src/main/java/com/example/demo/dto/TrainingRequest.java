package com.example.demo.dto;

import lombok.Data;

@Data
public class TrainingRequest {
    private int userId;
    private int petId;
    private TypeOfExercises typeOfExercises;
    private double duration;
}

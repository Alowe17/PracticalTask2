package com.example.demo.model.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRq {
    private String title = null;
    private String description = null;
    private Boolean completed = null;
}
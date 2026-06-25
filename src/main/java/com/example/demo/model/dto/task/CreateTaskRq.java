package com.example.demo.model.dto.task;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRq {
    @NotBlank(message = "Заголовок не может быть пустым!")
    private String title;
    @NotBlank(message = "Описание не может быть пустым!")
    private String description;
}
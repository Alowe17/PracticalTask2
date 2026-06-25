package com.example.demo.model.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRq {
    @NotBlank(message = "Имя не может быть пустым!")
    @Size(max = 100, message = "Имя не может быть длиннее 100 символов!")
    private String name;
    @NotBlank(message = "Фамилия не может быть пустой!")
    @Size(max = 100, message = "Фамилия не может быть длиннее 100 символов!")
    private String surname;
    @NotEmpty(message = "Возраст не может быть пустым!")
    @Positive(message = "Возраст должен быть положительным!")
    private Integer age;
}
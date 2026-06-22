package com.example.demo.model.entity.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private int age;

    public Account (String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Account() {}
}
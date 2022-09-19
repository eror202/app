package com.edu.ulab.app.entity;

import lombok.*;

import java.util.List;

@Data
public class User {
    private Long id;
    private int age;
    private String fullName;
    private String title;
    private List<Long> booksList;
}

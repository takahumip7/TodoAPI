package com.example.todo.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {

  private Long id;

  private String title;

  private boolean completed;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}

package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoCreateRequest {

  @NotBlank
  private String title;
}

package com.example.todo.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoUpdateRequest {

  @Size(max = 50, message = "タイトルは50文字以内で入力してください。")
  private String title;

  private Boolean completed;
}

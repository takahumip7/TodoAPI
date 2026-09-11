package com.example.todo.dto;

import lombok.Getter;

@Getter
public class ErrorResponse {

  private int status;
  private String message;
  private String path;

  public ErrorResponse(int status, String message, String path) {
    this.status = status;
    this.message = message;
    this.path = path;
  }
}

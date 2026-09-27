package com.example.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.todo.controller.TodoController;
import com.example.todo.dto.TodoCreateRequest;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

  @InjectMocks
  private TodoController todoController;

  @Mock
  private TodoService todoService;

  @Test
  void register_正常に登録できること() {

    // 準備
    TodoCreateRequest request = new TodoCreateRequest();
    request.setTitle("テスト");

    // 実行
    ResponseEntity<Void> response = todoController.register(request);

    // 確認
    verify(todoService).register(request);
    assertEquals(201, response.getStatusCode().value());
  }

}

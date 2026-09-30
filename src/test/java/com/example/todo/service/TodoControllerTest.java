package com.example.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.todo.controller.TodoController;
import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;

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

  @Test
  void findTodoList_一覧が取得されること() {
    // 準備
    TodoResponse todo = new TodoResponse(1L, "テスト", false);
    List<TodoResponse> todoList = List.of(todo);
    when(todoService.findTodoList(String title, Boolean completed)).thenReturn(todoList);
    // 実行
    ResponseEntity<List<TodoResponse>> response = todoController.findTodoList(String title, Boolean completed);
    // 確認
    verify(todoService).findTodoList(String title, Boolean completed);
    assertEquals(200, response.getStatusCode().value());
    assertEquals(1, response.getBody().size());
    assertEquals(1L, response.getBody().get(0).getId());
    assertEquals("テスト", response.getBody().get(0).getTitle());
    assertFalse(response.getBody().get(0).isCompleted());
  }

  @Test
  void deleteTodo_正常に削除されること() {
    // 準備
    Long id = 1L;
    // 実行
    ResponseEntity<Void> response = todoController.deleteTodo(id);
    // 確認
    verify(todoService).deleteTodo(id);
    assertEquals(204, response.getStatusCode().value());
  }
}

package com.example.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;
import com.example.todo.entity.Todo;
import com.example.todo.exception.TodoNotFoundException;
import com.example.todo.repository.TodoMapper;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

  @InjectMocks
  private TodoService todoService;

  @Mock
  private TodoMapper todoMapper;

  // register 正常に登録できる
  @Test
  void register_正常に登録できること() {
    // Arrange（準備）
    TodoCreateRequest request = new TodoCreateRequest();
    request.setTitle("テスト");

    // Act（実行）
    todoService.register(request);

    // Assert（確認）
    ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
    verify(todoMapper).insert(captor.capture());

    Todo actual = captor.getValue();

    assertEquals("テスト", actual.getTitle());
    assertFalse(actual.isCompleted());
  }

  // findTodoList 一覧を取得できる
  @Test
  void findTodoList_一覧を取得できること() {
    // 準備
    Todo todo = new Todo();
    todo.setId(1L);
    todo.setTitle("テスト");
    todo.setCompleted(false);

    List<Todo> todoList = List.of(todo);

    when(todoMapper.findTodoList()).thenReturn(todoList);

    // 実行
    List<TodoResponse> actual = todoService.findTodoList();

    // Assert（確認）
    assertEquals(1, actual.size());
    assertEquals(1L, actual.get(0).getId());
    assertEquals("テスト", actual.get(0).getTitle());
    assertFalse(actual.get(0).isCompleted());
  }

  // deleteTodo 正常に削除できる
  @Test
  void deleteTodo_正常に削除できること() {
    // 準備
    Long id = 1L;

    // 実行
    todoService.deleteTodo(id);

    // 確認
    verify(todoMapper).deleteTodo(id);
  }

  // updateTodo 更新できるか
  // title と comletedを更新できる
  @Test
  void updateTodo_タイトルと完了状態を更新できること() {
    // 準備
    Long id = 1L;

    TodoUpdateRequest request = new TodoUpdateRequest();
    request.setTitle("テスト");
    request.setCompleted(true);

    when(todoMapper.updateTodo(any(Todo.class))).thenReturn(1);

    // 実行
    todoService.updateTodo(id, request);

    // 確認
    ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
    verify(todoMapper).updateTodo(captor.capture());

    Todo actual = captor.getValue();

    assertEquals(1L, actual.getId());
    assertEquals("テスト", actual.getTitle());
    assertTrue(actual.isCompleted());
  }

  // title だけ更新できる
  @Test
  void updateTodo_タイトルだけ更新できること() {
    // 準備
    Long id = 1L;

    TodoUpdateRequest request = new TodoUpdateRequest();
    request.setTitle("テスト");
    request.setCompleted(null);

    when(todoMapper.updateTodo(any(Todo.class))).thenReturn(1);

    // 実行
    todoService.updateTodo(id, request);

    // 確認
    ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
    verify(todoMapper).updateTodo(captor.capture());

    Todo actual = captor.getValue();

    assertEquals(1L, actual.getId());
    assertEquals("テスト", actual.getTitle());
  }

  // comleted だけ更新できる
  @Test
  void updateTodo_完了状態だけ更新できること() {
    // 準備
    Long id = 1L;

    TodoUpdateRequest request = new TodoUpdateRequest();
    request.setTitle(null);
    request.setCompleted(true);

    when(todoMapper.updateTodo(any(Todo.class))).thenReturn(1);

    // 実行
    todoService.updateTodo(id, request);

    // 確認
    ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
    verify(todoMapper).updateTodo(captor.capture());

    Todo actual = captor.getValue();

    assertEquals(1L, actual.getId());
    assertTrue(actual.isCompleted());
  }

  // Todoが存在しない場合はTodoNotFoundException
  @Test
  void updateTodo_Todoが存在しない場合はTodoNotFoundExceptionを投げること() {
    // 準備
    Long id = 1L;

    TodoUpdateRequest request = new TodoUpdateRequest();
    request.setTitle("テスト");
    request.setCompleted(true);

    when(todoMapper.updateTodo(any(Todo.class))).thenReturn(0);

    // 実行・確認
    assertThrows(TodoNotFoundException.class, () -> {
      todoService.updateTodo(id, request);
    });
  }
}

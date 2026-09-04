package com.example.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoUpdateRequest;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoMapper;

@Service
public class TodoService {

  private final TodoMapper todoMapper;

  public TodoService(TodoMapper todoMapper) {
    this.todoMapper = todoMapper;
  }

  // DTOを受け取る
  public void register(TodoCreateRequest request) {
    // Entityを作成
    Todo todo = new Todo();
    todo.setTitle(request.getTitle());
    todo.setCompleted(false);
    todoMapper.insert(todo);
  }

  public List<Todo> findTodoList() {
    return todoMapper.findTodoList();
  }

  public void deleteTodo(Long id) {
    todoMapper.deleteTodo(id);
  }

  public void updateTodo(Long id, TodoUpdateRequest updateRequest) {
    Todo todo = new Todo();

    todo.setId(id);
    todo.setTitle(updateRequest.getTitle());
    todo.setCompleted(updateRequest.isCompleted());

    int result = todoMapper.updateTodo(todo);

    if (result == 0) {
      throw new IllegalArgumentException("Todoが存在しません");
    }
  }
}

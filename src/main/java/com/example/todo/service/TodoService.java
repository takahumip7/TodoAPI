package com.example.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoCreateRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.dto.TodoUpdateRequest;
import com.example.todo.entity.Todo;
import com.example.todo.exception.TodoNotFoundException;
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

  public List<TodoResponse> findTodoList() {
    List<Todo> todoList = todoMapper.findTodoList();

    return todoList.stream().map(todo -> new TodoResponse(todo.getId(), todo.getTitle(), todo.isCompleted())).toList();
  }

  public void deleteTodo(Long id) {
    todoMapper.deleteTodo(id);
  }

  public void updateTodo(Long id, TodoUpdateRequest updateRequest) {
    Todo todo = new Todo();

    todo.setId(id);
    if (updateRequest.getTitle() != null) {
      todo.setTitle(updateRequest.getTitle());
    }

    if (updateRequest.getCompleted() != null) {
      todo.setCompleted(updateRequest.getCompleted());
    }

    int result = todoMapper.updateTodo(todo);

    if (result == 0) {
      throw new TodoNotFoundException("Todoが存在しません");
    }
  }
}

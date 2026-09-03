package com.example.todo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.todo.entity.Todo;

@Mapper
public interface TodoMapper {

  int insert(Todo todo);

  List<Todo> findTodoList();

  int deleteTodo(Long id);

  int updateTodo(Todo todo);
}

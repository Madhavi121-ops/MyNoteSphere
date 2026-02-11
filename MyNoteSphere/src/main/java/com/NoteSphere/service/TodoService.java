package com.NoteSphere.service;

import java.util.List;
import com.NoteSphere.model.Todo;

public interface TodoService {

    Todo createTodo(String title);

    List<Todo> getUserTodos();

    Todo toggleTodoStatus(Long todoId);

    void deleteTodo(Long todoId);

    List<Todo> getTrashedTodos();

    void restoreTodo(Long todoId);

    void permanentDeleteTodo(Long todoId);
}

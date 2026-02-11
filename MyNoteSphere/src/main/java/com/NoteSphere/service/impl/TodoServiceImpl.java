package com.NoteSphere.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.NoteSphere.model.Todo;
import com.NoteSphere.model.User;
import com.NoteSphere.repository.TodoRepository;
import com.NoteSphere.repository.UserRepository;
import com.NoteSphere.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoServiceImpl(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Todo createTodo(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new RuntimeException("Task title cannot be empty");
        }

        User user = getCurrentUser();

        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        todo.setUser(user);

        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getUserTodos() {
        User user = getCurrentUser();
        return todoRepository.findByUserAndDeletedFalse(user);
    }

    @Override
    public Todo toggleTodoStatus(Long todoId) {
        User user = getCurrentUser();

        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long todoId) {
        User user = getCurrentUser();

        Todo todo = todoRepository.findByIdAndUser(todoId, user)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        todo.setDeleted(true);
        todoRepository.save(todo);
    }

    @Override
    public List<Todo> getTrashedTodos() {
        User user = getCurrentUser();
        return todoRepository.findByUserAndDeletedTrue(user);
    }

    @Override
    public void restoreTodo(Long todoId) {
        User user = getCurrentUser();
        todoRepository.findByIdAndUser(todoId, user)
                .ifPresent(todo -> {
                    todo.setDeleted(false);
                    todoRepository.save(todo);
                });
    }

    @Override
    public void permanentDeleteTodo(Long todoId) {
        User user = getCurrentUser();
        todoRepository.findByIdAndUser(todoId, user)
                .ifPresent(todo -> todoRepository.delete(todo));
    }
}

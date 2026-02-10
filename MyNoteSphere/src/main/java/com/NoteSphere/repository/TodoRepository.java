package com.NoteSphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.NoteSphere.model.Todo;
import com.NoteSphere.model.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUser(User user);

    Optional<Todo> findByIdAndUser(Long id, User user);
}

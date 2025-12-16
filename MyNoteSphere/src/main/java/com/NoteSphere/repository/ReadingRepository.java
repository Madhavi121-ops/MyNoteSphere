package com.NoteSphere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NoteSphere.model.ReadingItem;
import com.NoteSphere.model.User;

public interface ReadingRepository extends JpaRepository<ReadingItem, Long> {

    List<ReadingItem> findByUser(User user);
}

package com.NoteSphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.NoteSphere.model.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}

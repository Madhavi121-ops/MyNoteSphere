package com.NoteSphere.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.NoteSphere.model.Flashcard;
import com.NoteSphere.repository.FlashcardRepository;

@Service
public class FlashcardService {

    private final FlashcardRepository repository;

    public FlashcardService(FlashcardRepository repository) {
        this.repository = repository;
    }

    public List<Flashcard> findAll() {
        return repository.findAll();
    }

    public void save(Flashcard flashcard) {
        repository.save(flashcard);
    }
}

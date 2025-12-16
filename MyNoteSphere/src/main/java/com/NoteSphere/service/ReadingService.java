package com.NoteSphere.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.NoteSphere.model.ReadingItem;
import com.NoteSphere.model.User;
import com.NoteSphere.repository.ReadingRepository;

@Service
public class ReadingService {

    private final ReadingRepository repository;

    public ReadingService(ReadingRepository repository) {
        this.repository = repository;
    }

    public List<ReadingItem> getReadingList(User user) {
        return repository.findByUser(user);
    }

    public void save(ReadingItem item) {
        repository.save(item);
    }
}

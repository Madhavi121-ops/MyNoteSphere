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
        return repository.findByUserAndDeletedFalse(user);
    }

    public List<ReadingItem> getTrashList(User user) {
        return repository.findByUserAndDeletedTrue(user);
    }

    public void save(ReadingItem item) {
        repository.save(item);
    }

    public void deleteById(Long id) {
        repository.findById(id).ifPresent(item -> {
            item.setDeleted(true);
            repository.save(item);
        });
    }

    public void restoreById(Long id) {
        repository.findById(id).ifPresent(item -> {
            item.setDeleted(false);
            repository.save(item);
        });
    }

    public void permanentDelete(Long id) {
        repository.deleteById(id);
    }
}

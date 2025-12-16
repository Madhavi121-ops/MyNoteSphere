package com.NoteSphere.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.NoteSphere.model.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long>{
	List<Journal> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	Optional<Journal> findByIdAndUserId(Long journalId, Long userId);
}

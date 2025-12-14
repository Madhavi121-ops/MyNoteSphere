package com.NoteSphere.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.NoteSphere.model.Journal;
import com.NoteSphere.model.User;
import com.NoteSphere.repository.JournalRepository;
import com.NoteSphere.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class JournalService {
	private JournalRepository journalRepository;
	private final UserRepository userRepository;
	
	public JournalService(JournalRepository journalRepository,
			UserRepository userRepository) {
		this.journalRepository = journalRepository;
		this.userRepository = userRepository;
	}
	
	private User getCurrentAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found : " +username));
	}
	
	@Transactional
	public Journal createJournal(String title, String content) {
		User currentUser = getCurrentAuthenticatedUser();
		Journal journal = new Journal(title, content, currentUser);
		return journalRepository.save(journal);
	}
	
	public List<Journal> getAllUserJournals(){
		User currentUser = getCurrentAuthenticatedUser();
		return journalRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
	}
	
	public Optional<Journal> getJournalById(Long journalId){
		User currentUser = getCurrentAuthenticatedUser();
		
		return journalRepository.findByIdAndUserId(journalId, currentUser.getId());
	}
	
	@Transactional
	public Optional<Journal> updateJournal(Long journalId, String newTitle, String newContent){
		User currentUser = getCurrentAuthenticatedUser();
		return journalRepository.findByIdAndUserId(journalId, currentUser.getId())
				.map(journal ->{
					journal.setTitle(newTitle);
					journal.setContent(newContent);
					return journalRepository.save(journal);
				});
	}
    
	@Transactional
	public boolean deleteJournal(Long journalId) {
		User currentUser = getCurrentAuthenticatedUser();
		
		return journalRepository.findByIdAndUserId(journalId, currentUser.getId())
				.map(journal ->{
					journalRepository.delete(journal);
					return true;
				})
				.orElse(false);
	}
}


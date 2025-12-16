package com.NoteSphere.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "journals")
@EntityListeners(AuditingEntityListener.class)

public class Journal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String title;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	
	private String content;
	private String moodEmoji;
	
	@ManyToOne(fetch = FetchType.LAZY)
	
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
	
	@PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
	
  
	public Journal() {
	
	}
	
	public Journal(String title, String content, User user) {
		this.title = title;
		this.content = content;
		this.user = user;
		this.createdAt = LocalDateTime.now(); 
	    this.updatedAt = LocalDateTime.now();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	 public String getMoodEmoji() { 
		 return moodEmoji; 
	 }
	    
	 public void setMoodEmoji(String moodEmoji) { 
		 this.moodEmoji = moodEmoji; 
	 }
	 

	public Object getEntryDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getEntryTime() {
		// TODO Auto-generated method stub
		return null;
	}

}

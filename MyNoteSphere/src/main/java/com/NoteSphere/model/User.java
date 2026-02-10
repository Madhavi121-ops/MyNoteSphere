package com.NoteSphere.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({
    "authorities",
    "accountNonExpired",
    "accountNonLocked",
    "credentialsNonExpired",
    "enabled"
})
@Entity
@Table(name = "users")
@Getter
@Setter

public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String username;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.singletonList(new
				SimpleGrantedAuthority("ROLE_USER"));
				
	}
	
	@Override
	public boolean isAccountNonExpired() {return true;}
	
	@Override
	public boolean isAccountNonLocked() {return true;}
	
	@Override
	public boolean isCredentialsNonExpired() {return true;}
	
	@Override
	public boolean isEnabled() {return true;}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUpdatedAt(LocalDateTime now) {
		this.updatedAt = now;
		
	}

	public void setCreatedAt(LocalDateTime now) {
		this.createdAt = now;
		
	}

	public void setPassword(String encode) {
		this.password = encode;
		
	}

	public void setEmail(String email2) {
		this.email = email2;
		
	}

	public void setUsername(String username2) {
		this.username = username2;
		
	}

	public Long getId() {
        return id;   
    }

    public void setId(Long id) {
        this.id = id;
    }

}



package com.NoteSphere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.NoteSphere.service.UserService;

class RegisterRequest{
	private String username;
	private String email;
	private String password;
	private String confirmPassword;
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	public String getConfirmPassword() {return confirmPassword;}
	public void setConfirmPassword(String confirmPassword) {this.confirmPassword = confirmPassword;}
}

@Controller
public class AuthController {
	private final UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("registerRequest", new RegisterRequest());
		return "register";
	}
	@GetMapping("/todos")
    public String todosPage() {
        return "todo"; // maps to todo.html
    }
	@PostMapping("/register")
	public String registerUser(
	        @ModelAttribute("registerRequest") RegisterRequest request,
	        BindingResult result,
	        RedirectAttributes redirectAttributes) {

	    if (!request.getPassword().equals(request.getConfirmPassword())) {
	        result.rejectValue(
	                "confirmPassword",
	                "passwordMismatch",
	                "Passwords do not match."
	        );
	        return "register";
	    }
	    

	    // ✅ Proper username existence check
	    try {
	        userService.findByUsername(request.getUsername());
	        result.rejectValue(
	                "username",
	                "usernameExists",
	                "Username already exists."
	        );
	        return "register";
	    } catch (org.springframework.security.core.userdetails.UsernameNotFoundException e) {
	        // user does not exist → OK
	    }

	    try {
	        userService.registerNewUser(
	                request.getUsername(),
	                request.getEmail(),
	                request.getPassword()
	        );
	        redirectAttributes.addFlashAttribute(
	                "successMessage",
	                "Registration successful! Please log in."
	        );
	        return "redirect:/login";

	    } catch (IllegalArgumentException e) {
	        result.rejectValue("email", "emailExists", e.getMessage());
	        return "register";
	    }
	}

	
}

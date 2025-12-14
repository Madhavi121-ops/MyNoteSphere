package com.NoteSphere.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String landing(Authentication auth) {
	    if (auth != null && auth.isAuthenticated()) {
	        return "redirect:/dashboard";
	    }
	    return "landing";
	}

}

package com.NoteSphere.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardsController {
	@GetMapping("/dashboard")
	public String showDashboard(Model model, Authentication authentication) {
		if(authentication !=null) {
			model.addAttribute("username", authentication.getName());
		}
		
		return "dashboard";
	}
}

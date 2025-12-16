package com.NoteSphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.NoteSphere.model.Flashcard;
import com.NoteSphere.service.FlashcardService;

@Controller
public class DashboardsController {

    @Autowired
    private FlashcardService flashcardService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication auth) {

        model.addAttribute("flashcards", flashcardService.findAll());
        model.addAttribute("flashcard", new Flashcard());

        return "dashboard";
    }
}


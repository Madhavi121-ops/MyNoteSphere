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

    @Autowired
    private com.NoteSphere.repository.UserRepository userRepository;

    @Autowired
    private com.NoteSphere.repository.JournalRepository journalRepository;

    @Autowired
    private com.NoteSphere.repository.TodoRepository todoRepository;

    @Autowired
    private com.NoteSphere.repository.ReadingRepository readingRepository;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication auth) {
        if (auth != null) {
            String username = auth.getName();
            com.NoteSphere.model.User user = userRepository.findByUsername(username).orElse(null);

            if (user != null) {
                // Reading Count
                long readingCount = readingRepository.findByUserAndDeletedFalse(user).size();
                model.addAttribute("readingCount", readingCount);

                // Journal Count
                long journalCount = journalRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(user.getId())
                        .size();
                model.addAttribute("journalCount", journalCount);

                // Pending Tasks Count
                long todoCount = todoRepository.findByUserAndDeletedFalse(user).stream()
                        .filter(todo -> !todo.isCompleted())
                        .count();
                model.addAttribute("todoCount", todoCount);
            }
        }

        model.addAttribute("flashcards", flashcardService.findAll());
        model.addAttribute("flashcard", new Flashcard());

        return "dashboard";
    }
}

package com.NoteSphere.controller;

import com.NoteSphere.repository.JournalRepository;
import com.NoteSphere.repository.ReadingRepository;
import com.NoteSphere.repository.TodoRepository;
import com.NoteSphere.repository.UserRepository;
import com.NoteSphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @GetMapping("/account/add")
    public String addAccount() {
        return "user/add-account";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null) {
                model.addAttribute("user", user);
                // Fetch stats using efficient repository methods
                model.addAttribute("journalCount",
                        journalRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).size());
                model.addAttribute("todoCount", todoRepository.findByUser(user).stream()
                        .filter(com.NoteSphere.model.Todo::isCompleted).count());
                model.addAttribute("readingCount", readingRepository.findByUser(user).size());
            }
        }
        return "user/profile";
    }

    @GetMapping("/workspace/new")
    public String newWorkspace() {
        return "user/new-workspace";
    }

    @GetMapping("/share")
    public String share() {
        return "user/share";
    }

    @GetMapping("/password/change")
    public String changePassword() {
        return "user/change-password";
    }
}

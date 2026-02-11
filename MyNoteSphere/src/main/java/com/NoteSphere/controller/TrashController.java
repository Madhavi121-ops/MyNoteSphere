package com.NoteSphere.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.NoteSphere.model.User;
import com.NoteSphere.service.JournalService;
import com.NoteSphere.service.ReadingService;
import com.NoteSphere.service.TodoService;
import com.NoteSphere.service.UserService;

@Controller
@RequestMapping("/trash")
public class TrashController {

    private final JournalService journalService;
    private final TodoService todoService;
    private final ReadingService readingService;
    private final UserService userService;

    public TrashController(JournalService journalService, TodoService todoService, ReadingService readingService,
            UserService userService) {
        this.journalService = journalService;
        this.todoService = todoService;
        this.readingService = readingService;
        this.userService = userService;
    }

    @GetMapping
    public String viewTrash(Model model, Authentication auth) {
        // model attributes for trash items
        model.addAttribute("journals", journalService.getTrashedJournals());
        model.addAttribute("todos", todoService.getTrashedTodos());
        try {
            UserDetails userDetails = (UserDetails) auth.getPrincipal(); // Get specific user
            User user = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("readingItems", readingService.getTrashList(user));
        } catch (Exception e) {
            // Handle if reading service needs specific user object not available directly
            // from service wrapper
        }
        return "user/trash";
    }

    // Restore
    @PostMapping("/restore/journal/{id}")
    public String restoreJournal(@PathVariable Long id) {
        journalService.restoreJournal(id);
        return "redirect:/trash";
    }

    @PostMapping("/restore/todo/{id}")
    public String restoreTodo(@PathVariable Long id) {
        todoService.restoreTodo(id);
        return "redirect:/trash";
    }

    @PostMapping("/restore/reading/{id}")
    public String restoreReading(@PathVariable Long id) {
        readingService.restoreById(id);
        return "redirect:/trash";
    }

    // Permanent Delete
    @PostMapping("/delete/journal/{id}")
    public String deleteJournal(@PathVariable Long id) {
        journalService.permanentDeleteJournal(id);
        return "redirect:/trash";
    }

    @PostMapping("/delete/todo/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.permanentDeleteTodo(id);
        return "redirect:/trash";
    }

    @PostMapping("/delete/reading/{id}")
    public String deleteReading(@PathVariable Long id) {
        readingService.permanentDelete(id);
        return "redirect:/trash";
    }
}

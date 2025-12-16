package com.NoteSphere.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.NoteSphere.model.Flashcard;
import com.NoteSphere.service.FlashcardService;

@Controller
public class HomeController {

    @Autowired
    private FlashcardService flashcardService;

    // Landing page (public)
    @GetMapping("/")
    public String landing(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/dashboard";
        }
        return "landing";
    }

    // Add flashcard (POST only)
    @PostMapping("/flashcard/add")
    public String addFlashcard(@ModelAttribute Flashcard flashcard,
                               @RequestParam("image") MultipartFile image)
            throws IOException {

        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            String uploadDir = "src/main/resources/static/uploads/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            image.transferTo(new File(uploadDir + fileName));
            flashcard.setImagePath("/uploads/" + fileName);
        }

        flashcardService.save(flashcard);
        return "redirect:/dashboard";
    }
}

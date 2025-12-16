package com.NoteSphere.controller;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.NoteSphere.model.ReadingItem;
import com.NoteSphere.model.ReadingStatus;
import com.NoteSphere.model.User;
import com.NoteSphere.service.ReadingService;
import com.NoteSphere.service.UserService;

@Controller
@RequestMapping("/reading")
public class ReadingController {

    private final ReadingService readingService;
    private final UserService userService;

    public ReadingController(ReadingService readingService,
                             UserService userService) {
        this.readingService = readingService;
        this.userService = userService;
    }

    @GetMapping
    public String readingList(Model model, Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("items", readingService.getReadingList(user));
        model.addAttribute("readingItem", new ReadingItem());

        return "reading/list";
    }

    @PostMapping("/add")
    public String addReading(@ModelAttribute ReadingItem readingItem,
                             Authentication auth) {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        readingItem.setUser(user);
        readingItem.setStatus(ReadingStatus.TO_READ);
        readingItem.setCreatedAt(LocalDate.now());

        readingService.save(readingItem);
        return "redirect:/reading";
    }
}

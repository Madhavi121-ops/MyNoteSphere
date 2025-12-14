package com.NoteSphere.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.NoteSphere.model.Journal;
import com.NoteSphere.service.FileStorageService;
import com.NoteSphere.service.JournalService;

@Controller
@RequestMapping("/journals")
public class JournalController {
	private final JournalService journalService;
	private final FileStorageService fileStorageService;
	
	public JournalController(JournalService journalService, FileStorageService fileStorageService) {
		this.journalService = journalService;
		this.fileStorageService = fileStorageService;
	}
	@GetMapping
	public String redirectToList() {
	    return "redirect:/journals/list";
	}

	@GetMapping("/list")
	public String listJournals(Model model) {
		model.addAttribute("journals", journalService.getAllUserJournals());
		return "journals/list";
	}
	
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("journal", new Journal());
		
		return "journals/create-edit";
	}
	
	@PostMapping("/new")
	public String createJournal(@ModelAttribute Journal journal,
			RedirectAttributes redirectAttributes) {
		journalService.createJournal(journal.getTitle(),
		journal.getContent());
		redirectAttributes.addFlashAttribute("message", "Journal entry created succesfully!");
		
		return "redirect:/journals/list";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model,
			RedirectAttributes redirectAttributes) {
		Optional<Journal> journal = journalService.getJournalById(id);
		if(journal.isPresent()) {
			model.addAttribute("journal", journal.get());
			return "journals/create-edit";
		}else {
			redirectAttributes.addFlashAttribute("error", "Journal not found or you don't have permission.");
			return "redirect:/journals/list";
		}
	}
	
	@PostMapping("/edit/{id}")
	public String updateJournal(@PathVariable Long id, @ModelAttribute Journal journal, RedirectAttributes redirectAttributes) {
		Optional<Journal> updatedJournal = journalService.updateJournal(id, journal.getTitle(), journal.getContent());
		if(updatedJournal.isPresent()) {
			redirectAttributes.addFlashAttribute("message", "Journal entry updated successfully!");
		}
		else {
			redirectAttributes.addFlashAttribute("error", "Failed to update journal or you dont have permission");
		}
		return "redirect:/journals/list";
	}
	
	@PostMapping("/delete/{id}")
    public String deleteJournal(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (journalService.deleteJournal(id)) {
            redirectAttributes.addFlashAttribute("message", "Journal entry deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete journal or you don't have permission.");
        }
        return "redirect:/journals/list";
    }
	
	@GetMapping("/{id}")
    public String viewJournal(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Journal> journal = journalService.getJournalById(id);
        if (journal.isPresent()) {
            model.addAttribute("journal", journal.get());
            return "journals/view"; // Template for viewing a single journal
        } else {
            redirectAttributes.addFlashAttribute("error", "Journal not found or you don't have permission.");
            return "redirect:/journals/list";
        }
    }	
}

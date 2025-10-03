package com.ss.breg.controller;

import com.ss.breg.exception.ApplicationException;
import com.ss.breg.model.AllInfo;
import com.ss.breg.service.AllInfoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/register")
@SessionAttributes("allInfo")   // keeps AllInfo object across multiple steps
public class RegistrationController {

    private final AllInfoService service;

    public RegistrationController(AllInfoService service) {
        this.service = service;
    }

    // Initialize session-scoped object
    @ModelAttribute("allInfo")
    public AllInfo createAllInfo() {
        return new AllInfo();
    }

    // Step 1: Personal Info
    @GetMapping("/personal")
    public String showPersonal(@ModelAttribute("allInfo") AllInfo info) {
        return "PersonalInfo";
    }

    @PostMapping("/personal")
    public String handlePersonal(@ModelAttribute("allInfo") @Valid AllInfo info,
                                 BindingResult br, Model model) {
        if (br.hasErrors() || isEmpty(info.getFname()) || isEmpty(info.getLname())) {
            model.addAttribute("error", "First and Last name are required");
            return "PersonalInfo";
        }
        return "redirect:/register/contact";
    }

    // Step 2: Contact Info
    @GetMapping("/contact")
    public String showContact(@ModelAttribute("allInfo") AllInfo info) {
        return "ContactInfo";
    }

    @PostMapping("/contact")
    public String handleContact(@ModelAttribute("allInfo") @Valid AllInfo info,
                                BindingResult br, Model model) {
        if (br.hasErrors()
            || isEmpty(info.getAddress())
            || isEmpty(info.getCity())
            || isEmpty(info.getState())
            || isEmpty(info.getCountry())
            || isEmpty(info.getPhno())) {
            model.addAttribute("error", "All contact fields are required");
            return "ContactInfo";
        }
        return "redirect:/register/bank";
    }

    // Step 3: Bank Info
    @GetMapping("/bank")
    public String showBank(@ModelAttribute("allInfo") AllInfo info) {
        return "BankInfo";
    }

    @PostMapping("/bank")
    public String handleBank(@ModelAttribute("allInfo") @Valid AllInfo info,
                             BindingResult br, Model model) {
        if (br.hasErrors() || isEmpty(info.getBname()) || isEmpty(info.getAcnum())) {
            model.addAttribute("error", "Bank Name and Account Number are required");
            return "BankInfo";
        }

        try {
            boolean saved = service.registerUser(info);
            model.addAttribute("message", saved ? "Registration Successful!" : "Registration Failed!");
        } catch (ApplicationException e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }

        model.addAttribute("savedInfo", info);

      
        return "Success";
    }

    // Small helper
    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}

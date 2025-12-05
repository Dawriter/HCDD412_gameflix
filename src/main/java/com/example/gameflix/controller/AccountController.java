package com.example.gameflix.controller;

import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.validation.*;

import com.example.gameflix.model.Account;
import com.example.gameflix.service.AccountService;

@Controller

public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    //display list of accounts
    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    @GetMapping("/showNewAccountForm")
    public String showNewAccountForm(Model model) {
        //Create model attribute to bind form data
        Account account = new Account();
        model.addAttribute("account", account);
        return "new_account";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        //Create model attribute to bind form data
        Account account = new Account();
        model.addAttribute("account", account);
        return "login";
    }

    @PostMapping("/login")
    public String showLoginForm(Account account) {
        boolean isValidAccount = accountService.validateUser(account);
        if(isValidAccount) {
            return "welcome";
        }
        else{
            return "login_failure";
        }
    }

    @GetMapping("logout")
    public String logout(Model model) {
        return "redirect:/";
    }

    @PostMapping("/saveAccount")
    public String saveAccount(Account account) {
        //Encode password before saving the account
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        //Check for Duplicate Account Details
        if(!accountService.validateUser(account)) {
            return "no_duplicate_new_account";
        }
        // Save account to database
        accountService.saveAccount(account);
        return "redirect:/";
    }
    @GetMapping("/showFromForUpdate/{id}")
    public String showFromForUpdate(@PathVariable("id") Long id, Model model) {

        // get account from the service
        Account account = accountService.getAccountById(id);

        // set account as a model attribute to prepopulate the form
        model.addAttribute("account", account);
        return "update_account";
    }

    @GetMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable("id") Long id) {

        // call delet account method
        this.accountService.deleteAccountById(id);
        return "redirect:/";
    }

    @GetMapping("/page/{PageNo}")
    public String viewPage(@PathVariable("PageNo") int PageNo,
                           @RequestParam("sortField") String sortField,
                           @RequestParam("sortDir") String sortDir,
                           Model model) {
        int pageSize = 10;

        Page<Account> page = accountService.findPaginated(PageNo, pageSize, sortField, sortDir);
        List<Account> listAccounts = page.getContent();

        model.addAttribute("currentPage", PageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listAccounts", listAccounts);
        return "index";
    }
}

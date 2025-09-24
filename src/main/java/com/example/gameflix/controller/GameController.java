package com.example.gameflix.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.gameflix.model.Account;
import com.example.gameflix.model.Game;
import com.example.gameflix.repository.AccountRepository;
import com.example.gameflix.repository.GameRepository;
import com.example.gameflix.service.AccountService;
import com.example.gameflix.service.GameService;

public class GameController {
    private static final Logger log =  LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private GameService gameService;

    @GetMapping("/gameList")
    public String viewGamePage(Model model) {
        model.addAttribute("listGames", gameRepository.findAll());

        return "game_List";
    }

    @GetMapping("/showNewGameForm")
    public String showNewGameForm(Model model) {
        // Create model attribute to bind form data
        Game game = new Game();
        model.addAttribute("game", game);
        return "new_game";
    }
    @PostMapping("/saveGame")
    public String saveGame(@ModelAttribute("game") Game game) {
        // save game to database
        gameRepository.save(game);
        return "redirect:/gameList";
    }

    @RequestMapping(value = "/get/{sid}")
    public String getGameId(@PathVariable("sid") Long sid, Model model) {

        Optional<Game> findGameId = gameRepository.findById(sid);

        model.addAttribute("game", findGameId.get());

        log.info("getGameId() : " +sid);
        return "redirect:/gameList";
    }

    @GetMapping("/showGameFormForUpdate/{sid}")
    public String showGameFormForUpdate(@PathVariable("sid") Long sid, Model model) {

        // get game from the service
        Game game = gameService.getGameById(sid);
        List<Game> allGames = gameService.getAllGames();
        model.addAttribute("allGame", allGames);
        // set game as a model attribute to pre-populate the form
        model.addAttribute("game", game);

        return "update_game";
    }

    @GetMapping("/deleteGame/{sid}")
    public String deleteGame(@PathVariable("sid") Long sid) {

        // call delete game method
        this.gameRepository.deleteById(sid);
        return "redirect:/gameList";
    }

}

package com.green.blackjack;

import com.green.blackjack.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public Game startGame(@RequestParam(value = "reset", defaultValue = "false") boolean reset) {
        return gameService.startGame(reset);
    }

    @PostMapping("/hit")
    public Game hit() {
        return gameService.hit();
    }

    @PostMapping("/stand")
    public Game stand() {
        return gameService.stand();
    }
}

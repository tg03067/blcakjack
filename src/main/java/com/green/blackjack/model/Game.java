package com.green.blackjack.model;

import com.green.blackjack.model.Player;
import com.green.blackjack.model.Card;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Game {
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private int playerScore;
    private int dealerScore;
    private String message;
    private int playerMoney;
    private int dealerMoney;
    private int betAmount;
    private boolean gameOver;
    private List<Player> playersList;

    // 기본 생성자
    public Game() {
        this.playersList = new ArrayList<>();
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }

    // 필요한 모든 인자를 받는 생성자
    public Game(List<Card> playerHand, List<Card> dealerHand, int playerScore, int dealerScore, String message, int playerMoney, int dealerMoney, int betAmount, boolean gameOver) {
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.playerScore = playerScore;
        this.dealerScore = dealerScore;
        this.message = message;
        this.playerMoney = playerMoney;
        this.dealerMoney = dealerMoney;
        this.betAmount = betAmount;
        this.gameOver = gameOver;
        this.playersList = new ArrayList<>();
    }

    public Game(List<Card> playerHand, List<Card> dealerHand, int playerScore, int dealerScore, String message, int playerMoney, int dealerMoney, int betAmount, boolean gameOver, List<Player> playersList) {
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.playerScore = playerScore;
        this.dealerScore = dealerScore;
        this.message = message;
        this.playerMoney = playerMoney;
        this.dealerMoney = dealerMoney;
        this.betAmount = betAmount;
        this.gameOver = gameOver;
        this.playersList = playersList;
    }



    // Getter 및 Setter
    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public List<Card> getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(List<Card> dealerHand) {
        this.dealerHand = dealerHand;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

    public void setDealerScore(int dealerScore) {
        this.dealerScore = dealerScore;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;
    }

    public int getDealerMoney() {
        return dealerMoney;
    }

    public void setDealerMoney(int dealerMoney) {
        this.dealerMoney = dealerMoney;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public List<Player> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }
}

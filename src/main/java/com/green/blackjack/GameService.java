package com.green.blackjack;
import com.green.blackjack.*;
import com.green.blackjack.model.*;
import com.green.blackjack.model.Game;
import com.green.blackjack.model.Rank;
import com.green.blackjack.model.Suit;
import com.green.blackjack.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    private List<Card> deck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private boolean gameOver;
    private int playerMoney = 100;
    private int dealerMoney = 100;
    private int betAmount = 20;
    private List<Player> playersList;

    public Game startGame(boolean reset) {
        if (reset) {
            playerMoney = 100;
            dealerMoney = 100;
        }

        if (playerMoney <= 0) {
            return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Player is out of money. Dealer wins!", playerMoney, dealerMoney, betAmount, false, playersList);
        } else if (dealerMoney <= 0) {
            return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Dealer is out of money. Player wins!", playerMoney, dealerMoney, betAmount, false, playersList);
        }

        deck = createDeck();
        Collections.shuffle(deck);

        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        playersList = new ArrayList<>();

        playerHand.add(deck.remove(0));
        playerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));
        dealerHand.add(deck.remove(0));

        gameOver = false;

        playerMoney -= betAmount;
        dealerMoney -= betAmount;

        Player player = new Player("player1", playerHand); // Example player, modify as needed
        playersList.add(player);

        if (calculateHandValue(playerHand) == 21) {
            gameOver = true;
            playerMoney += betAmount * 2;
            return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Blackjack! Player wins!", playerMoney, dealerMoney, betAmount, true, playersList);
        }

        return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Game started. Hit or Stand?", playerMoney, dealerMoney, betAmount * 2, false, playersList);
    }

    public Game hit() {
        if (gameOver) {
            return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Game is over. Start a new game.", playerMoney, dealerMoney, betAmount, false, playersList);
        }

        playerHand.add(deck.remove(0));

        if (calculateHandValue(playerHand) > 21) {
            gameOver = true;
            dealerMoney += betAmount * 2;
            return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Player busts! Dealer wins.", playerMoney, dealerMoney, betAmount, true, playersList);
        }

        return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Hit or Stand?", playerMoney, dealerMoney, betAmount, false, playersList);
    }

    public Game stand() {
        if (gameOver) {
            return new Game(playerHand, dealerHand, calculateHandValue(playerHand), calculateHandValue(dealerHand), "Game is over. Start a new game.", playerMoney, dealerMoney, betAmount, true, playersList);
        }

        while (calculateHandValue(dealerHand) < 17) {
            dealerHand.add(deck.remove(0));
        }

        return determineWinner();
    }

    private Game determineWinner() {
        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (dealerValue > 21 || playerValue > dealerValue) {
            gameOver = true;
            playerMoney += betAmount * 2;
            return new Game(playerHand, dealerHand, playerValue, dealerValue, "Player wins!", playerMoney, dealerMoney, betAmount, true, playersList);
        } else if (playerValue < dealerValue) {
            gameOver = true;
            dealerMoney += betAmount * 2;
            return new Game(playerHand, dealerHand, playerValue, dealerValue, "Dealer wins!", playerMoney, dealerMoney, betAmount, true, playersList);
        } else {
            gameOver = true;
            playerMoney += betAmount;
            dealerMoney += betAmount;
            return new Game(playerHand, dealerHand, playerValue, dealerValue, "It's a tie!", playerMoney, dealerMoney, betAmount, true, playersList);
        }
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    private int calculateHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }
}

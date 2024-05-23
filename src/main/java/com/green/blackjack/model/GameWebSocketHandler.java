package com.green.blackjack.model;

import com.green.blackjack.GameService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Game> games = new HashMap<>();
    private final List<WebSocketSession> sessions = new ArrayList<>();

    private final GameService gameService;

    @Autowired
    public GameWebSocketHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        session.sendMessage(new TextMessage("Welcome to Blackjack!"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        GameMessage gameMessage = objectMapper.readValue(payload, GameMessage.class);

        switch (gameMessage.getType()) {
            case "JOIN":
                handleJoin(session, gameMessage);
                break;
            case "START":
                handleStart(session, gameMessage);
                break;
            case "HIT":
                handleHit(session, gameMessage);
                break;
            case "STAND":
                handleStand(session, gameMessage);
                break;
        }
    }

    private void handleJoin(WebSocketSession session, GameMessage gameMessage) throws Exception {
        String gameId = gameMessage.getGameId();
        Game game = games.getOrDefault(gameId, new Game());
        Player player = new Player(session.getId(), new ArrayList<>());
        game.getPlayersList().add(player);
        games.put(gameId, game);
        broadcastGameState(game);
    }

    private void handleStart(WebSocketSession session, GameMessage gameMessage) throws Exception {
        String gameId = gameMessage.getGameId();
        Game game = gameService.startGame(false);
        games.put(gameId, game);
        broadcastGameState(game);
    }

    private void handleHit(WebSocketSession session, GameMessage gameMessage) throws Exception {
        String gameId = gameMessage.getGameId();
        Game game = games.get(gameId);
        if (game != null) {
            game = gameService.hit();
            games.put(gameId, game);
        }
        broadcastGameState(game);
    }

    private void handleStand(WebSocketSession session, GameMessage gameMessage) throws Exception {
        String gameId = gameMessage.getGameId();
        Game game = games.get(gameId);
        if (game != null) {
            game = gameService.stand();
            games.put(gameId, game);
        }
        broadcastGameState(game);
    }

    private void broadcastGameState(Game game) throws Exception {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(game)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        games.values().forEach(game -> game.getPlayersList().removeIf(player -> player.getSessionId().equals(session.getId())));
    }
}

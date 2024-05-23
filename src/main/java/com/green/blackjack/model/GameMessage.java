package com.green.blackjack.model;

public class GameMessage {
    private String type;
    private String gameId;

    // 기본 생성자
    public GameMessage() {}

    // 파라미터가 있는 생성자
    public GameMessage(String type, String gameId) {
        this.type = type;
        this.gameId = gameId;
    }

    // Getter 및 Setter
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}

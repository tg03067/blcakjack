package com.green.blackjack.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Data
@Slf4j
public class Player {
    private String sessionId;
    private List<Card> hand;
    private boolean standing;
    private boolean busted;

    public Player(String sessionId, List<Card> hand) {
        log.info("sessionId: {}", sessionId);
        this.sessionId = sessionId;
        this.hand = hand;
        this.standing = false;
        this.busted = false;
    }

    public String getSessionId() {
        return sessionId;
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean isStanding() {
        return standing;
    }

    public void setStanding(boolean standing) {
        this.standing = standing;
    }

    public boolean isBusted() {
        return busted;
    }

    public void setBusted(boolean busted) {
        this.busted = busted;
    }
}

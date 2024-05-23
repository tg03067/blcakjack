let socket;
let gameId = "blackjack-game";

document.getElementById('joinGame').addEventListener('click', function() {
    socket = new WebSocket('ws://localhost:8080/game');

    socket.onopen = function() {
        socket.send(JSON.stringify({ type: "JOIN", gameId: gameId }));
        document.getElementById('joinGame').style.display = 'none';
        document.getElementById('startGame').style.display = 'inline';
    };

    socket.onmessage = function(event) {
        try {
            const game = JSON.parse(event.data);
            updateGameUI(game);
        } catch (e) {
            console.warn("Non-JSON message received:", event.data);
        }
    };
});

document.getElementById('startGame').addEventListener('click', function() {
    socket.send(JSON.stringify({ type: "START", gameId: gameId }));
    document.getElementById('startGame').style.display = 'none';
    document.getElementById('hit').style.display = 'inline';
    document.getElementById('stand').style.display = 'inline';
});

document.getElementById('hit').addEventListener('click', function() {
    socket.send(JSON.stringify({ type: "HIT", gameId: gameId }));
});

document.getElementById('stand').addEventListener('click', function() {
    socket.send(JSON.stringify({ type: "STAND", gameId: gameId }));
});

function getCardClass(card) {
    const rank = card.rank.toLowerCase();
    const suit = card.suit.toLowerCase();
    return `card-${rank}-of-${suit}`;
}

function updateGameUI(game) {
    document.getElementById('resultMessage').textContent = game.message;

    // Ensure playersList is defined and valid
    if (!game.playersList || !Array.isArray(game.playersList)) {
        console.error("Invalid playersList:", game.playersList);
        return;
    }

    // Find the current player using the session ID
    const player = game.playersList.find(p => p.sessionId === socket.id);

    if (player) {
        document.getElementById('playerHand').innerHTML = player.hand.map(card => `<div class="card ${getCardClass(card)}"></div>`).join('');
        document.getElementById('playerScore').textContent = 'Player Score: ' + calculateHandValue(player.hand);
    } else {
        console.error("Player not found in game:", game);
    }

    // Ensure dealerHand is defined and valid
    if (game.dealerHand && Array.isArray(game.dealerHand)) {
        document.getElementById('dealerHand').innerHTML = game.dealerHand.map(card => `<div class="card ${getCardClass(card)}"></div>`).join('');
        document.getElementById('dealerScore').textContent = 'Dealer Score: ' + calculateHandValue(game.dealerHand);
    } else {
        console.error("Invalid dealerHand:", game.dealerHand);
    }
}

function calculateHandValue(hand) {
    let value = 0;
    let aceCount = 0;

    for (const card of hand) {
        if (card.rank === 'ACE') {
            aceCount++;
            value += 11;
        } else if (['KING', 'QUEEN', 'JACK'].includes(card.rank)) {
            value += 10;
        } else {
            value += parseInt(card.rank);
        }
    }

    while (value > 21 && aceCount > 0) {
        value -= 10;
        aceCount--;
    }

    return value;
}

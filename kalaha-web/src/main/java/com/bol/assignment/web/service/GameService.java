package com.bol.assignment.web.service;

import com.bol.assignment.data.Game;

public interface GameService {
    Game initGame(int stones);

    Game play(String gameId, int pitIndex);
}

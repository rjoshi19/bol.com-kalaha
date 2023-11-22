package com.bol.assignment.web.service;

import com.bol.assignment.data.Game;
import com.bol.assignment.data.Pit;

public interface GameEngine {
    void play(Game game, Pit pit);
}

package com.bol.assignment.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Game {
    private final String id;
    private final Board board;

    @Setter
    private Player winner;
    @Setter
    private int activePlayerOrGameState;
    @Setter
    private Long updatedAt;
}

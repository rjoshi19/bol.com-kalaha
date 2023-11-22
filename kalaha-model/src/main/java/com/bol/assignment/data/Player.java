package com.bol.assignment.data;


public final record Player (int index, String name, House house){
    public static final int NONE = 0;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    public  static final int GAME_OVER = 3;
}

package com.bol.assignment.data;


public record Player (int index, String name, House house){
    public static int NONE = 0;
    public static int PLAYER1 = 1;
    public static int PLAYER2 = 2;
    public  static int GAME_OVER = 3;
}

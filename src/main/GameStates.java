package main;

public enum GameStates {
    PLAYING,
    LEVELSELECT,
    MENU,
    EDIT;

    public static GameStates gameState = MENU;

    public static void setGameState(GameStates state){
        gameState = state;
    }
}

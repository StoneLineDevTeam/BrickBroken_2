package net.sldt_team.bb2.input;

import net.sldt_team.bb2.screen.Game;
import net.sldt_team.gameEngine.input.mouse.IMouseHandler;
import net.sldt_team.gameEngine.input.mouse.MouseHelper;

public class GameMouse implements IMouseHandler {

    private Game gameScreen;

    public GameMouse(Game game){
        gameScreen = game;
    }

    public void mousePressed(int x, int y, MouseHelper helper) {
    }

    public void mouseClicked(int x, int y, MouseHelper helper) {
        if (!gameScreen.isGamePaused){
            gameScreen.startBall();
        }
    }

    public void mouseMoved(int x, int y) {
        if (!gameScreen.isGamePaused){
            gameScreen.stickX = x;
        }
    }

    public void mouseWheelMoved(boolean up, boolean down, int amount) {
    }
}

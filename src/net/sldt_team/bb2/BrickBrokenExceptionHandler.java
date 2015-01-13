package net.sldt_team.bb2;

import net.sldt_team.gameEngine.IExceptionHandler;
import net.sldt_team.gameEngine.exception.GameException;

public class BrickBrokenExceptionHandler implements IExceptionHandler{
    public void handleException(GameException e) {
        e.printStackTrace();
        System.exit(0);
    }
}

package net.sldt_team.bb2;

import net.sldt_team.gameEngine.ExceptionHandler;
import net.sldt_team.gameEngine.exception.GameException;

public class BrickBrokenExceptionHandler implements ExceptionHandler{
    public void handleException(GameException e) {
        e.printStackTrace();
        System.exit(0);
    }
}

package fr.bb2;

import fr.sldt.gameEngine.ExceptionHandler;
import fr.sldt.gameEngine.exception.GameException;

public class BrickBrokenExceptionHandler implements ExceptionHandler{
    public void handleException(GameException e) {
        e.printStackTrace();
        System.exit(0);
    }
}

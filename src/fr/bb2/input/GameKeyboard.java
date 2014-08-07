package fr.bb2.input;

import fr.bb2.BrickBroken2;
import fr.bb2.screen.Game;
import fr.bb2.screen.GuiIngameMenu;
import fr.sldt.gameEngine.GameApplication;
import fr.sldt.gameEngine.input.keyboard.KeyboardHandler;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.nio.ByteBuffer;

public class GameKeyboard implements KeyboardHandler {

    private Game gameScreen;

    public GameKeyboard(Game game){
        gameScreen = game;
    }

    public void keyTyped(char c, int key) {
        if (key == Keyboard.KEY_ESCAPE) {
            if (gameScreen.isGamePaused) {
                gameScreen.clearGui();
                gameScreen.showCursor = false;
                gameScreen.isGamePaused = false;
            } else {
                gameScreen.displayGui(new GuiIngameMenu());
                gameScreen.showCursor = true;
                gameScreen.isGamePaused = true;
            }
        } else if (key == Keyboard.KEY_F2){
            ByteBuffer buffer = BrickBroken2.captureScreen(0, 0, GameApplication.getScreenWidth(), GameApplication.getScreenHeight());
            BrickBroken2.saveScreenShot(buffer, GameApplication.getScreenWidth(), GameApplication.getScreenHeight(), new File(GameApplication.getGameDir() + File.separator));
        }
    }

    public void keyPressed(char c, int key) {
    }
}

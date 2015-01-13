package net.sldt_team.bb2.gui;

import net.sldt_team.bb2.screen.Game;
import net.sldt_team.bb2.screen.MainMenu;
import net.sldt_team.gameEngine.GameApplication;
import net.sldt_team.gameEngine.gui.Gui;
import net.sldt_team.gameEngine.gui.GuiButton;
import net.sldt_team.gameEngine.renderengine.FontRenderer;
import net.sldt_team.gameEngine.renderengine.RenderEngine;

public class GuiIngameMenu extends Gui{
    private GuiButton button;
    private GuiButton mainMenuButton;

    public GuiIngameMenu() {
        super(null);
    }

    public void renderGui(RenderEngine renderEngine, FontRenderer fontRenderer) {
        button.render(renderEngine, fontRenderer);
        mainMenuButton.render(renderEngine, fontRenderer);
    }

    public void onGuiInit() {
        button = new GuiButton("Back to Game", 128, 64, theGame.renderEngine.loadTexture("buttons/normal"), (GameApplication.getScreenWidth() / 2) - 128, GameApplication.getScreenHeight() / 2 - 64, 256, 64);
        mainMenuButton = new GuiButton("Back to MainMenu", 128, 64, theGame.renderEngine.loadTexture("buttons/normal"), (GameApplication.getScreenWidth() / 2) - 128, GameApplication.getScreenHeight() / 2 - 178, 256, 64);
        button.setAction(new Runnable() {
            public void run() {
                if (!(theGame.getCurrentFrame() instanceof Game)){
                    GameApplication.log.severe("Program received invalid command : exiting !");
                    System.exit(0);
                    return;
                }
                Game screen = (Game) theGame.getCurrentFrame();
                screen.clearGui();
                screen.isGamePaused = false;
                screen.showCursor = false;
            }
        });
        mainMenuButton.setAction(new Runnable() {
            public void run() {
                theGame.displayScreen(new MainMenu());
            }
        });
    }

    public void updateGui() {
        button.update();
        mainMenuButton.update();
    }
}

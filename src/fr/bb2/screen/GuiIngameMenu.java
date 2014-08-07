package fr.bb2.screen;

import fr.sldt.gameEngine.GameApplication;
import fr.sldt.gameEngine.controls.ComponentAction;
import fr.sldt.gameEngine.gui.Gui;
import fr.sldt.gameEngine.gui.GuiButton;
import fr.sldt.gameEngine.renderengine.FontRenderer;
import fr.sldt.gameEngine.renderengine.RenderEngine;

public class GuiIngameMenu extends Gui{
    private GuiButton button;
    private GuiButton mainMenuButton;

    public void renderGui(RenderEngine renderEngine, FontRenderer fontRenderer) {
        button.render(renderEngine, fontRenderer);
        mainMenuButton.render(renderEngine, fontRenderer);
    }

    public void onGuiInit() {
        button = new GuiButton("Back to Game", theGame.renderEngine.loadTexture("buttons/normal.png"), theGame.renderEngine.loadTexture("buttons/normal_click.png"), (GameApplication.getScreenWidth() / 2) - 128, GameApplication.getScreenHeight() / 2 - 64, 256, 64);
        mainMenuButton = new GuiButton("Back to MainMenu", theGame.renderEngine.loadTexture("buttons/normal.png"), theGame.renderEngine.loadTexture("buttons/normal_click.png"), (GameApplication.getScreenWidth() / 2) - 128, GameApplication.getScreenHeight() / 2 - 178, 256, 64);
        button.setAction(new ComponentAction() {
            public void actionPerformed() {
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
        mainMenuButton.setAction(new ComponentAction() {
            public void actionPerformed() {
                theGame.displayScreen(new MainMenu());
            }
        });
    }

    public void updateGui() {
        button.update();
        mainMenuButton.update();
    }
}

package net.sldt_team.bb2.gui;

import net.sldt_team.bb2.screen.MainMenu;
import net.sldt_team.gameEngine.AppSettings;
import net.sldt_team.gameEngine.GameApplication;
import net.sldt_team.gameEngine.gui.Gui;
import net.sldt_team.gameEngine.gui.GuiButton;
import net.sldt_team.gameEngine.renderengine.FontRenderer;
import net.sldt_team.gameEngine.renderengine.RenderEngine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.ArrayList;
import java.util.List;

public class GuiResolution extends Gui {
    private List<GuiButton> buttons = new ArrayList<GuiButton>();
    private MainMenu parentFrame;

    public GuiResolution(MainMenu menu){
        super(null);
        parentFrame = menu;
    }

    public void renderGui(RenderEngine renderEngine, FontRenderer fontRenderer) {
        for (GuiButton b : buttons){
            b.render(renderEngine, fontRenderer);
        }
    }

    public void onGuiInit() {
        try {
            int count = 1;
            int x = 1;
            for (final DisplayMode mode : Display.getAvailableDisplayModes()){
                if (mode.getWidth() >= 1600 && mode.getHeight() >= 900){
                    GuiButton button = new GuiButton(mode.getWidth() + "x" + mode.getHeight(), 128, 64, theGame.renderEngine.loadTexture("buttons/normal"), (GameApplication.getScreenWidth() / 2 - 256) +  (x * 256), count * 64, 256, 64);
                    button.setAction(new Runnable() {
                        public void run() {
                            theGame.updateGameDisplay(new AppSettings(mode.getWidth(), mode.getHeight(), false));
                            parentFrame.clearGui();
                        }
                    });
                    buttons.add(button);
                    count++;
                    if (count * 64 >= GameApplication.getScreenHeight() - 512){
                        count = 1;
                        x++;
                    }
                }
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void updateGui() {
        for (GuiButton b : buttons){
            b.update();
        }
    }
}

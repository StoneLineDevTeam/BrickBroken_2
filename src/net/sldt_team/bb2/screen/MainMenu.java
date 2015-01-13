package net.sldt_team.bb2.screen;

import net.sldt_team.bb2.gui.GuiResolution;
import net.sldt_team.gameEngine.AppSettings;
import net.sldt_team.gameEngine.GameApplication;
import net.sldt_team.gameEngine.screen.components.IScreenComponent;
import net.sldt_team.gameEngine.ext.Translator;
import net.sldt_team.gameEngine.renderengine.FontRenderer;
import net.sldt_team.gameEngine.renderengine.RenderEngine;
import net.sldt_team.gameEngine.renderengine.animation.Animation;
import net.sldt_team.gameEngine.renderengine.helper.ColorHelper;
import net.sldt_team.gameEngine.screen.GuiScreen;
import org.lwjgl.opengl.Display;

public class MainMenu extends GuiScreen {

    private int ticks;
    private int textSize;
    private boolean canIncrease;

    private Animation testAnim;

    public void updateScreen() {
        ticks++;
        if (ticks >= 2){
            ticks = 0;
            if (textSize <= 64 && canIncrease){
                textSize += 2;
            } else if (textSize >= 64 && canIncrease){
                textSize = 64;
                canIncrease = false;
            } else if (!canIncrease && textSize > 16){
                textSize -= 2;
            } else {
                canIncrease = true;
            }
        }

        testAnim.update();
    }

    public void renderScreen(RenderEngine renderEngine, FontRenderer fontRenderer) {
        fontRenderer.setRenderingColor(ColorHelper.RED);
        fontRenderer.setRotationLevel(45F);
        fontRenderer.renderEffectString("OpenSource !!", GameApplication.getScreenWidth() - (fontRenderer.calculateWidthForEffectString("OpenSource !!", textSize)), -370, textSize);

        fontRenderer.setRenderingColor(new ColorHelper(82, 177, 255));
        fontRenderer.setRenderingSize(5);
        fontRenderer.setFontSublined(true);
        fontRenderer.renderShadowedString("BrickBroken[2] - Breakout game using SLDT's GameEngine !", 96, 64, 1.04F);
        fontRenderer.setFontSublined(false);

        testAnim.render(renderEngine, 16, 32, 64, 64);
    }

    public void initScreen() {
        testAnim = theGame.renderEngine.loadAnimation("fire");

        AnimatedButton but = new AnimatedButton(Translator.instance.translate("screen.play"), 32, (GameApplication.getScreenHeight() / 2) - 64, 256, 64, theGame);
        addComponentToScreen(but);

        AnimatedButton but1 = new AnimatedButton(Translator.instance.translate("screen.size"), 32, (GameApplication.getScreenHeight() / 2) - 128, 256, 64, theGame);
        addComponentToScreen(but1);

        AnimatedButton but2 = new AnimatedButton(Translator.instance.translate("screen.fullscreen"), 32, (GameApplication.getScreenHeight() / 2), 256, 64, theGame);
        addComponentToScreen(but2);

        addQuitButton();
    }

    public void onExitingScreen() {
    }

    public void actionPerformed(int i, IScreenComponent iScreenComponent) {
        switch (i){
            case -1:
                theGame.displayScreen(new Game());
                break;
            case 0:
                displayGui(new GuiResolution(MainMenu.this));
                break;
            case 1:
                int width = Display.getDisplayMode().getWidth();
                int height = Display.getDisplayMode().getHeight();
                theGame.updateGameDisplay(new AppSettings(width, height, !Display.isFullscreen()));
                break;
        }
    }
}

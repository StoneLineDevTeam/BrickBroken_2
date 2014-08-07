package fr.bb2.screen;

import fr.sldt.gameEngine.GameApplication;
import fr.sldt.gameEngine.controls.ComponentAction;
import fr.sldt.gameEngine.controls.NormalButton;
import fr.sldt.gameEngine.ext.Translator;
import fr.sldt.gameEngine.renderengine.ColorRenderer;
import fr.sldt.gameEngine.renderengine.FontRenderer;
import fr.sldt.gameEngine.renderengine.RenderEngine;
import fr.sldt.gameEngine.renderengine.anim.AnimationFile;
import fr.sldt.gameEngine.screen.Screen;

public class MainMenu extends Screen{

    private int ticks;
    private int textSize;
    private boolean canIncrease;

    private AnimationFile testAnim;

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

        testAnim.updateAnimation();
    }

    public void renderScreen(RenderEngine renderEngine, FontRenderer fontRenderer) {
        fontRenderer.setRenderingColor(ColorRenderer.RED);
        fontRenderer.setRotationLevel(45F);
        fontRenderer.renderEffectString("OpenSource !!", GameApplication.getScreenWidth() - (fontRenderer.calculateWidthForEffectString("OpenSource !!", textSize)), -370, textSize);

        fontRenderer.setRenderingColor(new ColorRenderer(82, 177, 255));
        fontRenderer.setRenderingSize(5);
        fontRenderer.renderShadowedString("BrickBroken[2] - Breakout game using SLDT's GameEngine !", 96, 64, 1.04F);

        testAnim.renderAnimation(renderEngine, 16, 32, 64, 64);
    }

    public void initScreen() {
        testAnim = new AnimationFile("test");
        NormalButton but = new NormalButton(Translator.instance.translate("screen.play"), 32, (GameApplication.getScreenHeight() / 2) - 64, 128, 64, theGame.renderEngine, theGame.particleManager, theGame.gameSettings);
        but.setButtonAction(new ComponentAction() {
            public void actionPerformed() {
                theGame.displayScreen(new Game());
            }
        });
        addComponentToWindow(but);
        addQuitButton();
    }

    public void onExitingScreen() {
    }
}

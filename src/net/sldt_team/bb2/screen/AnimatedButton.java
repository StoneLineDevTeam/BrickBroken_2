package net.sldt_team.bb2.screen;

import net.sldt_team.gameEngine.GameApplication;
import net.sldt_team.gameEngine.screen.components.NormalButton;
import net.sldt_team.gameEngine.renderengine.FontRenderer;
import net.sldt_team.gameEngine.renderengine.RenderEngine;
import net.sldt_team.gameEngine.renderengine.animation.Animation;
import net.sldt_team.gameEngine.renderengine.helper.ColorHelper;

public class AnimatedButton extends NormalButton {

    private Animation animFile;
    private int w;
    private int h;

    private float butScale;
    private int scaleTicks;

    public AnimatedButton(String str, int x, int y, int width, int height, GameApplication theGame) {
        super(str, x, y, width, height, theGame);

        animFile = theGame.renderEngine.loadAnimation("button");
        w = width;
        h = height;
    }

    public void renderComponent(RenderEngine renderEngine, FontRenderer fontRenderer) {
        if (!isMouseOver) {
            animFile.render(renderEngine, getX(), getY(), w, h);

            fontRenderer.setRenderingSize(6);
            fontRenderer.setRenderingColor(new ColorHelper(0, 255, 255));
            fontRenderer.renderString(buttonName, (getX() + w / 2) - (fontRenderer.getStringWidth(buttonName) / 2), (getY() + h / 2) - (fontRenderer.getCharWidth() / 2));
        } else {
            renderEngine.bindTexture(renderEngine.loadTexture("buttons/animated/hover"));
            renderEngine.setScaleLevel(butScale);
            renderEngine.addTranslationMatrix(getX(), getY());
            renderEngine.renderQuad(0, 0, w, h);

            fontRenderer.setRenderingSize(6);
            fontRenderer.setRenderingColor(new ColorHelper(255, 255, 0));
            fontRenderer.renderString(buttonName, (getX() + w / 2) - (fontRenderer.getStringWidth(buttonName) / 2), (getY() + h / 2) - (fontRenderer.getCharWidth() / 2));
        }
    }

    public void updateComponent() {
        super.updateComponent();

        if (!isMouseOver) {
            animFile.update();
            butScale = 1;
            scaleTicks = 0;
        } else {
            scaleTicks++;
            if (scaleTicks >= 4 && butScale < 2.1F){
                butScale += 0.1F;
                scaleTicks = 0;
            }
        }
    }
}

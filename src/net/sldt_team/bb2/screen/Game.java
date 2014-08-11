package net.sldt_team.bb2.screen;

import net.sldt_team.bb2.*;
import net.sldt_team.bb2.input.GameKeyboard;
import net.sldt_team.bb2.input.GameMouse;
import net.sldt_team.gameEngine.GameApplication;
import net.sldt_team.gameEngine.input.KeyboardInput;
import net.sldt_team.gameEngine.input.MouseInput;
import net.sldt_team.gameEngine.renderengine.ColorRenderer;
import net.sldt_team.gameEngine.renderengine.FontRenderer;
import net.sldt_team.gameEngine.renderengine.RenderEngine;
import net.sldt_team.gameEngine.screen.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class Game extends GuiScreen {

    //Inputs
    private KeyboardInput keyInput = new KeyboardInput(new GameKeyboard(this));
    private MouseInput mouseInput = new MouseInput(new GameMouse(this));

    //Game control
    public boolean isGamePaused = false;
    public int stickX = 0;
    private boolean canStartBall = true;

    //Game
    private List<Ball> balls = new ArrayList<Ball>();
    private Brick[] bricks = new Brick[512];
    private int brickCount = 0;
    private String levelName;


    public void startBall() {
        if (canStartBall) {
            for (Ball b : balls) {
                b.speedY = -5F;
            }
            canStartBall = false;
        }
    }

    public void resetBricks(int levelID) {
        levelName = Levels.levelNames[levelID];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 16; j++) {
                if (Levels.levelsData[levelID][i][j] != -1) {
                    int scrW = (GameApplication.getScreenWidth() / 2) - ((16 * 78) / 2);
                    bricks[brickCount] = new Brick(scrW + (j * 78), 10 + (i * 32), 78, 32, Levels.levelsData[levelID][i][j]);
                    brickCount++;
                }
            }
        }
    }

    public void updateScreen() {
        keyInput.updateInput();
        mouseInput.updateInput();

        if (!isGamePaused) {
            //Ball update
            for (int i = 0; i < balls.size(); i++) {
                if (canStartBall) {
                    balls.get(i).posX = stickX + 64;
                    balls.get(i).posY = GameApplication.getScreenHeight() - 128;
                    continue;
                }
                Ball b = balls.get(i);
                balls.get(i).posX += balls.get(i).speedX;
                balls.get(i).posY += balls.get(i).speedY;

                if (b.posY < 0) {
                    balls.get(i).speedY = 7.5F;
                }
                if (b.posY > GameApplication.getScreenHeight() - 64) {
                    balls.remove(i);
                    if (balls.size() == 0) {
                        createBall();
                        canStartBall = true;
                        return;
                    }
                }
                if (b.posX < 0) {
                    balls.get(i).speedX = 7.5F;
                }
                if (b.posX > GameApplication.getScreenWidth()) {
                    balls.get(i).speedX = -7.5F;
                }

                //Stick colision check
                if (b.posX >= stickX && b.posX <= (stickX + 128)) {
                    if (b.posY >= (GameApplication.getScreenHeight() - 128)) {
                        float x = b.posX;
                        if (x >= (stickX + 59) && x <= (stickX + 69)) {
                            balls.get(i).speedY = -7.5F;
                            balls.get(i).speedX = 0;
                            return;
                        }
                        if (x >= stickX && x <= (stickX + 64)) {
                            float factor = (stickX + 128) - b.posX;
                            float speedX = (float) ((factor * 7.5) / 128);
                            balls.get(i).speedY = -7.5F;
                            balls.get(i).speedX = (speedX * -1);
                        } else if (x >= (stickX + 64) && x <= (stickX + 128)) {
                            float factor = (stickX + 128) - b.posX;
                            float speedX = (float) ((factor * 7.5) / 128);
                            balls.get(i).speedY = -7.5F;
                            balls.get(i).speedX = speedX;
                        }
                    }
                }

                //Bricks colision check
                checkBrickColisions(b);
            }
        }
    }

    private void checkBrickColisions(Ball ball) {
        for (int i = 0; i < 512; i++) {
            if (bricks[i] != null) {
                Brick b = bricks[i];
                if (ball.posX >= b.posX && ball.posX <= (b.posX + 78)) {
                    if (ball.posY >= b.posY && ball.posY <= (b.posY + 32)) {
                        float x = ball.posX;
                        if (ball.posY  >= (b.posY + 27)) {
                            if (x >= b.posX && x <= (b.posX + 39)) {
                                float factor = (stickX + 128) - b.posX;
                                float speedX = (float) ((factor * 7.5) / 256);
                                ball.speedY = 7.5F;
                                ball.speedX = speedX * -1;
                                brickCount--;
                                bricks[i] = null;
                            } else if (x >= (b.posX + 39) && x <= (b.posX + 78)) {
                                float factor = (stickX + 128) - b.posX;
                                float speedX = (float) ((factor * 7.5) / 256);
                                ball.speedY = 7.5F;
                                ball.speedX = speedX;
                                brickCount--;
                                bricks[i] = null;
                            }
                        } else {
                            if (x >= b.posX && x <= (b.posX + 39)) {
                                float factor = (stickX + 128) - b.posX;
                                float speedX = (float) ((factor * 7.5) / 256);
                                ball.speedY = -7.5F;
                                ball.speedX = speedX * -1;
                                brickCount--;
                                bricks[i] = null;
                            } else if (x >= (b.posX + 39) && x <= (b.posX + 78)) {
                                float factor = (stickX + 128) - b.posX;
                                float speedX = (float) ((factor * 7.5) / 256);
                                ball.speedY = -7.5F;
                                ball.speedX = speedX;
                                brickCount--;
                                bricks[i] = null;
                            }
                        }
                    }
                }
            }
        }
    }

    public void renderScreen(RenderEngine renderEngine, FontRenderer fontRenderer) {
        renderEngine.bindColor(ColorRenderer.BLACK);
        renderEngine.renderQuad(stickX, GameApplication.getScreenHeight() - 128, 128, 16);

        for (Ball b : balls) {
            renderEngine.bindColor(ColorRenderer.RED);
            renderEngine.renderCircle(b.posX, b.posY, 4);
        }

        for (Brick b : bricks) {
            if (b != null) {
                renderEngine.bindColor(ColorRenderer.BLUE);
                renderEngine.renderQuad(b.posX, b.posY, b.width, b.height);
            }
        }

        fontRenderer.unbindColor();
        fontRenderer.setRenderingColor(ColorRenderer.WHITE);
        fontRenderer.setRenderingSize(5);
        fontRenderer.renderShadowedString(levelName, GameApplication.getScreenWidth() - (fontRenderer.getStringWidth(levelName) * 2), GameApplication.getScreenHeight() - 256, 0F);
    }

    public void initScreen() {
        addQuitButton();
        showCursor = false;
        createBall();
        resetBricks(0);
    }

    public void createBall() {
        balls.add(new Ball());
        balls.add(new Ball());
    }

    public void onExitingScreen() {
    }
}

package net.sldt_team.bb2;

import net.sldt_team.bb2.screen.MainMenu;
import net.sldt_team.gameEngine.AppSettings;
import net.sldt_team.gameEngine.EngineConstants;
import net.sldt_team.gameEngine.GameApplication;
import net.sldt_team.gameEngine.IPreloadModifier;
import net.sldt_team.gameEngine.ext.Translator;
import net.sldt_team.gameEngine.renderengine.Texture;
import net.sldt_team.gameEngine.renderengine.assetSystem.AssetType;
import net.sldt_team.gameEngine.renderengine.assetSystem.Type;
import net.sldt_team.gameEngine.renderengine.helper.ColorHelper;
import net.sldt_team.gameEngine.util.GuiUtilities;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class BrickBroken2 extends GameApplication implements IPreloadModifier {

    //Game bg
    private float bgScale;
    private int bgScaleTiks;

    public BrickBroken2(){
        super("BrickBroken", null, 64F, "#ROOT_FOLDER#", new BrickBrokenExceptionHandler(), "BrickBroken[2]", null);

        setDefaultLang("lang");
        setHasCustomCursor();
    }

    public void renderGameOverlay() {
        if (GuiUtilities.mousePressed()){
            renderEngine.bindColor(new ColorHelper(0, 0, 255, 200));
        } else {
            renderEngine.bindColor(new ColorHelper(133, 133, 133, 200));
        }
        int[] msPos = GuiUtilities.getMousePosition();
        renderEngine.setRotationLevel(45);
        renderEngine.addTranslationMatrix(msPos[0], msPos[1]);
        renderEngine.renderTriangle(0, 0, 16, 32,  0, true);
    }

    public void updateGameOverlay() {
    }

    public static ByteBuffer captureScreen(int x, int y, int width, int height)
    {
        GL11.glReadBuffer(GL11.GL_FRONT);
        ByteBuffer screenBuffer = BufferUtils.createByteBuffer(width * height * 4);

        GL11.glReadPixels(x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, screenBuffer);
        return screenBuffer;
    }

    public static void saveScreenShot(ByteBuffer screenBuffer, int width, int height, File dir){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                int i = y * width * 4 + x * 4;
                int r = screenBuffer.get(i) & 0xFF;
                int g = screenBuffer.get(i + 1) & 0xFF;
                int b = screenBuffer.get(i + 2) & 0xFF;
                image.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);

        try {
            ImageIO.write(image, "PNG", new File(dir, "screen_" + Sys.getTime() + ".png"));
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    public void initGame() {
        new Translator("lang");
        gameSettings.showFPS = true;
        gameSettings.isParticlesActivated = true;
        displayScreen(new MainMenu());
        bgScale = 0.0F;
    }

    public void exitGame() {
    }

    public String getIconPackage() {
        return null;
    }

    public AssetType getAssetsFileType() {
        return new AssetType(Type.GAF_FILE);
    }

    public static void main(String[] args){
        AppSettings settings = new AppSettings(1600, 900, false);
        setupThread(new BrickBroken2(), "BrickBroken2", settings);
        if (!EngineConstants.ENGINE_VERSION.equals("V10")){
            System.err.println("Engine version changed ; incompatible version detected, game closed.");
            System.exit(0);
        }
    }

    public void renderPreLoadScreen() {
        Texture i = renderEngine.loadTexture("backgrounds/title");
        renderEngine.bindTexture(i);
        renderEngine.setScaleLevel(1 - bgScale);
        renderEngine.renderQuad(0, 0, getScreenWidth(), getScreenHeight());
    }

    public void updatePreLoadScreen() {
        bgScaleTiks++;
        if (bgScaleTiks >= 10) {
            bgScale += 0.001F;
            bgScaleTiks = 0;
        }
    }
}

package _08final.images;

import _08final.mvc.model.Enemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class easily loads the textures for the sprites in the game
 *
 * @version  1.0
 * @author Lamont Samuels
 * @since  11-13-16
 */

public class SpriteTexLoader {

    private static SpriteTexLoader sInstance = new SpriteTexLoader();

    public enum SpriteTex {
        PLAYER,
        ENEMY,
        BOSS,
        BULLET,
        BANG,
        BACKGROUND,
        GAMEOVER,
        SPACE,
        SCORETABLE
    }

    private SpriteTexLoader() {}
    /**
     * Retrieves the file path for the a Sprite texture in the images file
     * @param sprite - the sprite file path to retrieve
     */
    private static String getSpriteFile(SpriteTex sprite) {

        String file = "";
        switch (sprite) {
            case PLAYER:
                file = "player.png";
                break;
            case ENEMY:
                file = "enemy.png";
                break;
            case BOSS:
                file = "boss.png";
                break;
            case BULLET:
                file = "bullet.png";
                break;
            case BANG:
                file = "bang.png";
                break;
            case BACKGROUND:
                file = "background.png";
                break;
            case GAMEOVER:
                file = "gameover.png";
                break;
            case SPACE:
                file = "space.png";
                break;
            case SCORETABLE:
                file = "scoretable.png";
                break;


        }
        return file;
    }

    /**
     * Returns a buffered image from the images directory of a particular sprite
     * @param sprite - the sprite texture to load
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static BufferedImage load(SpriteTex sprite)  throws IllegalArgumentException {

        if (sprite == null){
            throw new IllegalArgumentException("Sprite texture parameter must not be null");
        }
        BufferedImage img = null;
        try {
            String file = getSpriteFile(sprite);
            img = ImageIO.read(sInstance.getClass().getResource(file));
        }catch (IOException e){
            System.out.print("Could not open texture :" + sprite.toString());
            System.exit(1);
        }
        return img;
    }

}


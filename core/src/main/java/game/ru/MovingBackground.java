package game.ru;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingBackground {
    private Texture texture;
    private float x1, x2;
    private float speed;

    public MovingBackground(String texturePath) {
        texture = new Texture(texturePath);
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        x1 = 0;
        x2 = GameSettings.SCREEN_WIDTH;
        speed = GameSettings.GAME_SPEED;
    }

    public void update(float delta) {
        x1 -= speed * delta;
        x2 -= speed * delta;

        if (x1 + GameSettings.SCREEN_WIDTH <= 0) {
            x1 = x2 + GameSettings.SCREEN_WIDTH;
        }
        if (x2 + GameSettings.SCREEN_WIDTH <= 0) {
            x2 = x1 + GameSettings.SCREEN_WIDTH;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x1, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        batch.draw(texture, x2, 0, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }
}

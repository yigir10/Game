package game.ru;

import com.badlogic.gdx.physics.box2d.World;

public class BulletObject extends GameObject {
    public boolean active = true;

    public BulletObject(int x, int y, int width, int height, String texturePath, float spread, World world) {
        // isSensor = true, scale = 0.5 (маленькая пуля), maskBits = 0 (никуда не толкает)
        super(texturePath, x, y, width, height, GameSettings.BULLET_BIT, world, true, 0.5f, 0.5f, (short) 0);
        body.setGravityScale(0);
        body.setBullet(true);
        body.setLinearVelocity(spread, -GameSettings.BULLET_VELOCITY);
    }

    @Override
    public void hit() {
        active = false;
    }

    public void update() {
        if (getY() > GameSettings.SCREEN_HEIGHT + 100 || getY() < -100 || !active) {
            active = false;
        }
    }
}

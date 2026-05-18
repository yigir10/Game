package game.ru;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class LaserObject extends GameObject {
    public boolean active = true;

    public LaserObject(int x, int y, int width, int height, String texturePath, World world) {
        // scaleX = 0.3f (узкий по горизонтали), scaleY = 0.8f (длинный по вертикали)
        super(texturePath, x, y, width, height, GameSettings.LASER_BIT, world, true, 0.3f, 0.8f, GameSettings.JETPACK_BIT);

        // Кинематическое тело: движется по координатам, игнорирует физические силы
        body.setType(BodyDef.BodyType.KinematicBody);
    }

    public void update(float delta) {
        // Двигаем лазер влево со скоростью игры
        float x = body.getPosition().x - GameSettings.GAME_SPEED * delta * GameSettings.SCALE;
        body.setTransform(x, body.getPosition().y, 0);

        if (getX() < -width) {
            active = false;
        }
    }

    @Override
    public void hit() {
        // Логика при столкновении
    }
}

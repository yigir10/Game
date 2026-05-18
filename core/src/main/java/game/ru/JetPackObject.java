package game.ru;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

public class JetPackObject extends GameObject {
    public JetPackObject(int x, int y, int width, int height, String texturePath, World world) {
        // categoryBits = JETPACK_BIT
        // isSensor = false (персонаж твердый)
        // scaleX = 0.5f (узкий хитбокс, чтобы не задевать края)
        // scaleY = 1.0f (полная высота для касания пола)
        // maskBits = BOUNDS_BIT | LASER_BIT (сталкиваемся с полом/потолком и регистрируем лазеры)
        super(texturePath, x, y, width, height, GameSettings.JETPACK_BIT, world, false, 0.5f, 1.0f, (short)(GameSettings.BOUNDS_BIT | GameSettings.LASER_BIT));
        body.setGravityScale(1);
        body.setLinearDamping(1f);
    }

    public void fly() {
        float vY = body.getLinearVelocity().y + GameSettings.JUMP_FORCE;
        body.setLinearVelocity(0, vY);
    }

    public void update() {
        // Блокируем горизонтальное смещение
        body.setLinearVelocity(0, body.getLinearVelocity().y);

        int currentY = getY();
        int halfHeight = height / 2;

        // Ограничение: Пол
        if (currentY - halfHeight < 0) {
            setY(halfHeight);
            if (body.getLinearVelocity().y < 0) body.setLinearVelocity(0, 0);
        }

        // Ограничение: Потолок
        if (currentY + halfHeight > GameSettings.SCREEN_HEIGHT) {
            setY(GameSettings.SCREEN_HEIGHT - halfHeight);
            if (body.getLinearVelocity().y > 0) body.setLinearVelocity(0, 0);
        }
    }

    @Override
    public void hit() {
        // Логика при столкновении с лазером
        System.out.println("JetPackObject hit!");
    }
}

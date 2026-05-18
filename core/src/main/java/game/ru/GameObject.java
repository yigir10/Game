package game.ru;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class GameObject {
    Texture texture;
    public Body body;
    int width, height;
    public short cBits;

    // Конструктор по умолчанию
    GameObject(String texturePath, int x, int y, int width, int height, short cBits, World world) {
        this(texturePath, x, y, width, height, cBits, world, false, 1f, 1f, (short) -1);
    }

    // Расширенный конструктор для точной настройки физики
    GameObject(String texturePath, int x, int y, int width, int height, short cBits, World world, boolean isSensor, float scaleX, float scaleY, short maskBits) {
        this.width = width;
        this.height = height;
        this.cBits = cBits;
        this.texture = new Texture(texturePath);

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.position.set(x * GameSettings.SCALE, y * GameSettings.SCALE);
        body = world.createBody(def);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((width * scaleX * GameSettings.SCALE) / 2f, (height * scaleY * GameSettings.SCALE) / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.filter.categoryBits = cBits;
        fixtureDef.filter.maskBits = maskBits; // С чем этот объект может сталкиваться

        body.createFixture(fixtureDef).setUserData(this);
        boxShape.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }

    public void hit() {}

    public int getX() { return (int) (body.getPosition().x / GameSettings.SCALE); }
    public int getY() { return (int) (body.getPosition().y / GameSettings.SCALE); }
    public void setY(int y) { body.setTransform(body.getPosition().x, y * GameSettings.SCALE, 0); }
}

package game.ru;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    JetPackObject jetPackObject;
    MovingBackground movingBackground;
    GameSession gameSession;
    ArrayList<BulletObject> bullets;
    ArrayList<LaserObject> lasers;

    float shootTimer = 0;
    float shootInterval = 0.2f;

    float laserTimer = 0;
    float laserSpawnInterval = 2.0f;

    public GameScreen(MyGdxGame myGdxGame) {
        gameSession = new GameSession();
        this.myGdxGame = myGdxGame;
        bullets = new ArrayList<>();
        lasers = new ArrayList<>();
        jetPackObject = new JetPackObject(GameSettings.SCREEN_WIDTH / 2, 640, GameSettings.JETPACK_WIDTH, GameSettings.JETPACK_HEIGHT, GameResources.JETPACK_IMG_PATH, myGdxGame.world);
        movingBackground = new MovingBackground(GameResources.BACKGROUND_IMG_PATH);
        new ContactManager(myGdxGame.world);

        createBounds();
    }

    private void createBounds() {
        float scale = GameSettings.SCALE;
        float width = GameSettings.SCREEN_WIDTH * scale;
        float height = GameSettings.SCREEN_HEIGHT * scale;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        EdgeShape edge = new EdgeShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edge;
        fixtureDef.filter.categoryBits = GameSettings.BOUNDS_BIT;
        fixtureDef.filter.maskBits = GameSettings.JETPACK_BIT;

        // Пол
        Body floor = myGdxGame.world.createBody(bodyDef);
        edge.set(0, 0, width, 0);
        floor.createFixture(fixtureDef);

        // Потолок
        Body ceiling = myGdxGame.world.createBody(bodyDef);
        edge.set(0, height, width, height);
        ceiling.createFixture(fixtureDef);

        edge.dispose();
    }

    @Override
    public void render(float delta) {
        handleInput(delta);
        movingBackground.update(delta);
        jetPackObject.update();

        laserTimer += delta;
        if (laserTimer >= laserSpawnInterval) {
            spawnLaser();
            laserTimer = 0;
        }

        Iterator<BulletObject> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            BulletObject bullet = bulletIterator.next();
            bullet.update();
            if (!bullet.active) {
                myGdxGame.world.destroyBody(bullet.body);
                bulletIterator.remove();
            }
        }

        Iterator<LaserObject> laserIterator = lasers.iterator();
        while (laserIterator.hasNext()) {
            LaserObject laser = laserIterator.next();
            laser.update(delta);
            if (!laser.active) {
                myGdxGame.world.destroyBody(laser.body);
                laserIterator.remove();
            }
        }

        myGdxGame.stepWorld();
        draw();
    }

    private void spawnLaser() {
        int x = GameSettings.SCREEN_WIDTH + GameSettings.LASER_WIDTH;
        int y = MathUtils.random(200, GameSettings.SCREEN_HEIGHT - 200);
        LaserObject laser = new LaserObject(x, y, GameSettings.LASER_WIDTH, GameSettings.LASER_HEIGHT, GameResources.LASER_IMG_PATH, myGdxGame.world);
        lasers.add(laser);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isTouched()) {
            jetPackObject.fly();
            shootTimer += delta;
            if (shootTimer >= shootInterval) {
                shoot();
                shootTimer = 0;
            }
        } else {
            shootTimer = shootInterval;
        }
    }

    private void shoot() {
        float spread = MathUtils.random(-1f, 1f);
        BulletObject bullet = new BulletObject(
                jetPackObject.getX(),
                jetPackObject.getY(),
                GameSettings.BULLET_WIDTH,
                GameSettings.BULLET_HEIGHT,
                GameResources.BULLET_IMG_PATH,
                spread,
                myGdxGame.world
        );
        bullets.add(bullet);
    }

    private void draw() {
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        movingBackground.draw(myGdxGame.batch);

        for (LaserObject laser : lasers) {
            laser.draw(myGdxGame.batch);
        }

        jetPackObject.draw(myGdxGame.batch);

        for (BulletObject bullet : bullets) {
            bullet.draw(myGdxGame.batch);
        }
        myGdxGame.batch.end();
    }

    @Override
    public void dispose() {
        movingBackground.dispose();
    }
}

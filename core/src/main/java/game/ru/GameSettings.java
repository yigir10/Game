package game.ru;

public class GameSettings {
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    public static final int JETPACK_HEIGHT = 300;
    public static final int JETPACK_WIDTH = 200;
    public static final short JETPACK_BIT = 2;

    public static final int BULLET_HEIGHT = 40;
    public static final int BULLET_WIDTH = 40;
    public static final short BULLET_BIT = 4;
    public static final float BULLET_VELOCITY = 20f;

    public static final float GRAVITY = -30f;
    public static final float JUMP_FORCE = 0.8f;

    public static final float GAME_SPEED = 200f;

    public static final int LASER_HEIGHT = 250;
    public static final int LASER_WIDTH = 50;
    public static final short LASER_BIT = 8;

    public static final short BOUNDS_BIT = 1;
}

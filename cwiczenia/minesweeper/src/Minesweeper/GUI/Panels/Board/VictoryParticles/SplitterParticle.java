package Minesweeper.GUI.Panels.Board.VictoryParticles;

import java.util.Random;

public class SplitterParticle {

    private long t0 = System.currentTimeMillis();

    private Vector2 velocity;
    private Vector2 acceleration = new Vector2(0, 1.5f);

    public Vector2 position = new Vector2(0.5f, 0.5f);

    public float r,g,b;

    public SplitterParticle(Vector2 velocity) {
        this.velocity = velocity;

        this.r = generateColorValue();
        this.g = generateColorValue();
        this.b = generateColorValue();

    }

    private float generateColorValue() {
        Random generator = new Random();
        return (float) Math.floor(generator.nextFloat() + 0.5f);
    }

    public void update() {
        long t1 = System.currentTimeMillis();
        float secondsElapsed = (t1 - t0) / 1000.0f;
        t0 = t1;
        velocity.x += secondsElapsed * acceleration.x;
        velocity.y += secondsElapsed * acceleration.y;
        position.x += secondsElapsed * velocity.x;
        position.y += secondsElapsed * velocity.y;
    }

    public float getAlpha() {
        return Math.max((float) (1 - 0.65 * position.length()), 0.0f);
    }
}

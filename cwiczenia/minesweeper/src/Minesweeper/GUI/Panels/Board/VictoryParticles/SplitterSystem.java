package Minesweeper.GUI.Panels.Board.VictoryParticles;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SplitterSystem {

    private static final float MAX_SPEED = 0.5f;
    private static final int NOF_PARTICLES = 200;

    public List<SplitterParticle> particles = new LinkedList<>();

    public SplitterSystem() {
        initParticles();
    }

    public void update() {

        if (particles.isEmpty()) {
            initParticles();
        }
        for (ListIterator<SplitterParticle> i = particles.listIterator(); i.hasNext(); ) {
            SplitterParticle particle = i.next();
            if (particle.position.x >= 1.0 || particle.position.y >= 1.0) {
                i.set(createParticle());

            } else {
                particle.update();
            }
        }
    }

    public void initParticles() {
        for (int i = 0; i < NOF_PARTICLES; i++) {
            particles.add(createParticle());
        }
    }
    public boolean isFinished() {
        return particles.isEmpty();
    }

    private SplitterParticle createParticle() {
        Vector2 randomDirection = new Vector2((float) Math.random() - 0.5f, (float) Math.random() - 0.5f);
        randomDirection.normalize();
        randomDirection.mult((float) Math.random() * MAX_SPEED);
        return new SplitterParticle(randomDirection);
    }
}

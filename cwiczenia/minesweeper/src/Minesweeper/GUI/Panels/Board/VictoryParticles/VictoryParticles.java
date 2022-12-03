package Minesweeper.GUI.Panels.Board.VictoryParticles;


import javax.swing.*;
import java.awt.*;

public class VictoryParticles extends JPanel {
    private SplitterSystem splitterSystem;
    private boolean inProgress = false;

    public VictoryParticles(SplitterSystem splitterSystem) {
        this.splitterSystem = splitterSystem;
        this.setOpaque(false);
    }

    public void update() {
        splitterSystem.update();
        repaint();
    }

    public void startAnimation() {
        if(this.inProgress) {
            return;
        }
        this.inProgress = true;
        splitterSystem.initParticles();
        new Thread(() -> {
            while (!splitterSystem.isFinished()) {
                long startTime = System.currentTimeMillis();

                this.update();

                try {
                    Thread.sleep(Math.max(startTime + 16 - System.currentTimeMillis(), 0));
                } catch (InterruptedException ignored) {
                }
            }
            this.inProgress = false;

        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(!inProgress) {
            return;
        }

        splitterSystem.particles.forEach(particle -> {
            int x = (int) (particle.position.x * getWidth());
            int y = (int) (particle.position.y * getHeight());
            g.setColor(new Color(particle.r, particle.g, particle.b, particle.getAlpha()));
            g.fillOval(x, y, 8, 8);

        });
    }
}

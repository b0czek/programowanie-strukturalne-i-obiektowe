package GUI.Panels.Board.VictoryParticles;

public class Vector2 {

    // Mutable for performance: we don't want to create extraneous objects
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 that) {
        this.x = that.x;
        this.y = that.y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        float len = length();
        x = x / len;
        y = y / len;
    }

    public void mult(float c) {
        x *= c;
        y *= c;
    }
}

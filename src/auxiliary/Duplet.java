package auxiliary;

import java.util.Objects;

public class Duplet {
    int x;
    int y;

    static int X_LOWER_LIMIT = 0;
    static int X_UPPER_LIMIT = 7;
    static int Y_LOWER_LIMIT = 0;
    static int Y_UPPER_LIMIT = 7;

    public Duplet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isInsideLimits() {
        return (x >= X_LOWER_LIMIT && x <= X_UPPER_LIMIT) && (y >= Y_LOWER_LIMIT && y <= Y_UPPER_LIMIT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duplet duplet = (Duplet) o;
        return x == duplet.x &&
                y == duplet.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

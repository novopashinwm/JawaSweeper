package sweeper;

/**
 * Created by Владимир on 31.12.2017.
 */
public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coord))
            return super.equals(obj);
        Coord to = (Coord) obj;
        return to.x == this.x && to.y == this.y;
    }
}

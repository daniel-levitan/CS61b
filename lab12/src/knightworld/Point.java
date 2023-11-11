package knightworld;

import tileengine.TETile;

public class Point {
    private int x;
    private int y;
//    TETile tileType;

//    public Point (int x, int y, TETile tileType) {
    public Point (int x, int y) {
        this.x = x;
        this.y = y;
//        this.tileType = tileType;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

//    public TETile getTileType() { return tileType; }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")");
    }
}

package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;
    // TODO: Add additional instance variables here
    private int holeSize;

    public KnightWorld(int width, int height, int holeSize) {
        // TODO: Fill in this constructor and class, adding helper methods and/or classes as necessary to draw the
        //  specified pattern of the given hole size for a window of size width x height. If you're stuck on how to
        //  begin, look at the provided demo code!
        this.holeSize = holeSize;
        tiles = new TETile[width][height];
    }

    public void fillUpBack() {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
//                tiles[x][y] = Tileset.LOCKED_DOOR;
                drawFigure(new Point(x, y), Tileset.LOCKED_DOOR);
            }
        }
    }

    public ArrayList<Point> getPossibleMoves(Point p) {
        int height = tiles[0].length;
        int width = tiles.length;
        ArrayList<Point> result = new ArrayList();
        int x_c = p.getX();
        int y_c = p.getY();

        result.add(p);

//        if (x_c + 1 < width && y_c + 2 < height)
//            result.add(new Point(x_c + 1, y_c + 2));
//        if (x_c + 2 < width && y_c - 1 >= 0)
//            result.add(new Point(x_c + 2, y_c - 1));
//        if (x_c - 2 >= 0 && y_c + 1 < height)
//            result.add(new Point(x_c - 2, y_c + 1));
//        if (x_c - 1 >= 0 && y_c - 2 >= 0)
//            result.add(new Point(x_c - 1, y_c - 2));

        if (x_c + 1 * holeSize < width && y_c + 2 * holeSize < height)
            result.add(new Point(x_c + 1 * holeSize, y_c + 2 * holeSize ));
        if (x_c + 2 * holeSize < width && y_c - 1 * holeSize >= 0)
            result.add(new Point(x_c + 2 * holeSize, y_c - 1 * holeSize));
        if (x_c - 2 * holeSize >= 0 && y_c + 1 * holeSize < height)
            result.add(new Point(x_c - 2 * holeSize, y_c + 1 * holeSize));
        if (x_c - 1 * holeSize >= 0 && y_c - 2 * holeSize >= 0)
            result.add(new Point(x_c - 1 * holeSize, y_c - 2 * holeSize));
        return result;
    }

    public void fillUpHolesHelper(Point p, boolean[][] visited) {
        ArrayList<Point> points = getPossibleMoves(p);
//        visited[p.getX()][p.getY()] = true;
        markVisited(p, visited);

        if (points != null) {
            for (Point p_ : points) {
                drawFigure(p_, Tileset.NOTHING);

                if (!visited[p_.getX()][p_.getY()])
                    fillUpHolesHelper(p_, visited);
            }
        }
    }

    public void fillUpHoles(Point p) {
        int height = tiles[0].length;
        int width = tiles.length;

        boolean[][] visited = new boolean[width][height];
        for(int i = 0; i < visited.length; i++)
            for(int j = 0; j < visited[i].length; j++)
                visited[i][j] = false;

        fillUpHolesHelper(p, visited);
    }

    public boolean withinTable(Point p) {
        int height = tiles[0].length;
        int width = tiles.length;

        if (p.getX() < width && p.getY() < height)
            return false;

        return true;
    }

    public void markVisited(Point p, boolean[][] visited) {
        // I can mark only the bottom left corner since it is the only reference
        // I use to start drawing squares
//        visited[p.getX()][p.getY()] = true;

        // Or I can mark all other points as visited
        int height = tiles[0].length;
        int width = tiles.length;

        for (int i = 0; i < holeSize; i++) {
            for (int j = 0; j < holeSize; j++) {
                if (p.getX() + i < width && p.getY() + j < height)
                    visited[p.getX() + i][p.getY() + j] = true;
            }
        }
    }

    public void drawFigure(Point p, TETile type) {
        int height = tiles[0].length;
        int width = tiles.length;

        for (int i = 0; i < holeSize; i++) {
            for (int j = 0; j < holeSize; j++) {
                if (p.getX() + i < width && p.getY() + j < height)
//                if (withinTable(new Point(p.getX() + i, p.getY() + j)))
                    tiles[p.getX() + i][p.getY() + j] = type;
            }
        }
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 50;
        int height = 30;
        int holeSize = 4;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        knightWorld.fillUpBack();
        knightWorld.fillUpHoles(new Point(width / 2, height / 2));
//        knightWorld.drawFigure(new Point(width / 2, height / 2), Tileset.MOUNTAIN);

        ter.renderFrame(knightWorld.getTiles());
    }
}

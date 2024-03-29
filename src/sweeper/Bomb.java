package sweeper;

/**
 * Created by Владимир on 01.01.2018.
 */
class Bomb
{
    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++)
            placeBomb();
    }

    Box get (Coord coord) {
        return bombMap.get(coord);
    }

    int getTotalBombs()
    {
        return totalBombs;
    }

    private  void fixBombsCount() {
        int maxBombs = Ranges.getSquare()/2;
        if (totalBombs>maxBombs)
            totalBombs = maxBombs;
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, Box.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }

    }

    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around,bombMap.get(around).nextNumberBox());
    }
}

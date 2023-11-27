package model;

public class Move
{
    private final int X;
    private final int y;
    private boolean isSquareOccupied;

    public Move(int x, int y)
    {
        this.X = x;
        this.y = y;
    }

    public Move(int x, int y, boolean isSquareOccupied)
    {
        this.X = x;
        this.y = y;
        this.isSquareOccupied = isSquareOccupied;
    }

    public int getX()
    {
        return X;
    }

    public int getY()
    {
        return y;
    }

    public boolean isSquareOccupied()
    {
        return isSquareOccupied;
    }

}

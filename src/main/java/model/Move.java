package model;

public class Move
{
    private int x;
    private int y;
    private boolean isSquareOccupied;

    public Move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Move(int x, int y, boolean isSquareOccupied)
    {
        this.x = x;
        this.y = y;
        this.isSquareOccupied = isSquareOccupied;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean isSquareOccupied()
    {
        return isSquareOccupied;
    }

    public void setX(int xIndex)
    {
        this.x = xIndex;
    }

    public void setY(int yIndex)
    {
        this.y = yIndex;
    }
}

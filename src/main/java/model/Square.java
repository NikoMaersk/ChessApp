package model;

import model.chesspieces.Piece;
import javafx.scene.paint.Color;

public class Square
{
    private double squareSize;
    private int x;
    private int y;
    private Piece piece;
    private Color color;

    public Square(double squareSize, int x, int y, Color color)
    {
        this.squareSize = squareSize;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Square(double squareSize, int x, int y, Piece piece, Color color)
    {
        this.squareSize = squareSize;
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.color = color;
    }

    //region getter/setter
    public double getXPos()
    {
        return x * squareSize;
    }

    public void setXPos(int xPos)
    {
        this.x = xPos;
    }

    public double getYPos()
    {
        return y * squareSize;
    }

    public void setYPos(int yPos)
    {
        this.y = yPos;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

    public double getSquareSize()
    {
        return squareSize;
    }

    public void setSquareSize(double squareSize)
    {
        this.squareSize = squareSize;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public int getXIndex()
    {
        return x;
    }

    public int getYIndex()
    {
        return y;
    }

    //endregion
}

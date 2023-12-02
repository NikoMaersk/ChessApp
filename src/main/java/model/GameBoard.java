package model;

import model.chesspieces.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A conceptual class representing the game of chess
 *
 * <p>Contains method {@code getSquares} needed for drawing the chessboard
 */

public class GameBoard
{
    private double squareSize;
    private boolean isWhite;
    private final int ROWS = 8;
    private final int COLUMNS = 8;

    private final Square[][] squares = new Square[COLUMNS][ROWS];

    /**
     * Creates a new GameBoard
     * @param boardSize size of the chessboard as a square.
     * @param isWhite determines which side each color is located. If true white at bottom,
     *                if false black at bottom
     */
    public GameBoard(double boardSize, boolean isWhite)
    {
        this.isWhite = isWhite;
        squareSize = boardSize / (double)ROWS;
        PlaceSquares();
        placePieces();
    }

    /**
     * Draws a chessboard
     * @deprecated
     * replaced by {@link GameBoard#getSquares()}
     *
     * @param gc GraphicsContext instance to draw on canvas
     */
    public void drawBoard(GraphicsContext gc)
    {
        boolean isGreen = isWhite;
        Color customColor = Color.color(0.505882353f, 0.71372549f, 0.298039216f);

        for (int i = 0; i < ROWS; i++)
        {
            isGreen = !isGreen;
            for (int j = 0; j < COLUMNS; j++)
            {
                isGreen = !isGreen;
                double xPos = i * squareSize;
                double yPos = j * squareSize;
                gc.setFill(isGreen ? Color.BEIGE : customColor);
                gc.fillText(""+ (j+1), 5, yPos + 15);
                gc.fillRect(xPos, yPos, squareSize, squareSize);
            }
        }
    }

    /**
     * Fills 2D array instance variable with Square type representing chessboard squares
     */
    private void PlaceSquares()
    {
        boolean isGreen = true;
        Color customColor = Color.color(0.3647058823529412f, 0.7137254901960784f, 0.2980392156862745f);

        for (int i = 0; i < ROWS; i++)
        {
            isGreen = !isGreen;
            for (int j = 0; j < COLUMNS; j++)
            {
                isGreen = !isGreen;
                Color color = isGreen ? Color.BEIGE : customColor;
                squares[i][j] = new Square(squareSize, i, j, color);
            }
        }
    }

    /**
     * Sets the starting position for the chess pieces on the Square object in the squares 2D array
     */
    private void placePieces()
    {
        int column = 0;

        // opponent pieces
        squares[column++][0].setPiece(new Rook(!isWhite));
        squares[column++][0].setPiece(new Knight(!isWhite));
        squares[column++][0].setPiece(new Bishop(!isWhite));
        squares[column++][0].setPiece(new Queen(!isWhite));
        squares[column++][0].setPiece(new King(!isWhite));
        squares[column++][0].setPiece(new Bishop(!isWhite));
        squares[column++][0].setPiece(new Knight(!isWhite));
        squares[column][0].setPiece(new Rook(!isWhite));

        column = 0;

        while (column < ROWS)
        {
            squares[column++][1].setPiece(new Pawn(!isWhite));
        }

        // player pieces
        column = 0;

        while (column < ROWS)
        {
            squares[column++][6].setPiece(new Pawn(isWhite));
        }

        column = 0;

        squares[column++][7].setPiece(new Rook(isWhite));
        squares[column++][7].setPiece(new Knight(isWhite));
        squares[column++][7].setPiece(new Bishop(isWhite));
        squares[column++][7].setPiece(new Queen(isWhite));
        squares[column++][7].setPiece(new King(isWhite));
        squares[column++][7].setPiece(new Bishop(isWhite));
        squares[column++][7].setPiece(new Knight(isWhite));
        squares[column][7].setPiece(new Rook(isWhite));
    }

    /**
     * Gets the Piece at Square index coordinates
     * @param x column on the chessboard
     * @param y row on the chessboard
     * @return the Piece at the Square index coordinates
     */
    public Piece getPieceAt(int x, int y)
    {
        if (isValidCoordinate(x, y))
        {
            return squares[x][y].getPiece();
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets the Square which hold a specific Piece
     * @param piece the Piece to be located on a Square
     * @return the Square holding the Piece if a match is found else null
     */
    public Square getSquareHoldingPiece(Piece piece)
    {
        for (Square[] row : squares)
        {
            for (Square square : row)
            {
                if (square.getPiece() == piece)
                {
                    return square;
                }
            }
        }
        return null;
    }


    public void move(Square origin, Square target)
    {
        Piece piece = origin.getPiece();

        origin.setPiece(null);

        if (target.getPiece() == null)
        {
            target.setPiece(piece);
            if (piece instanceof Pawn)
            {
                ((Pawn) piece).setFirstMove(false);
            }
        }
    }


    public void capture(Square origin, Square target)
    {
        if (target.getPiece() != null)
        {
            target.setPiece(origin.getPiece());
            origin.setPiece(null);
        }
    }


    public boolean canMoveTo(Square origin, Square target)
    {
        Piece piece = origin.getPiece();

        return piece.getLegalMoves().stream().anyMatch(m -> m.getX() == target.getXIndex() && m.getY() == target.getYIndex());
    }


    public boolean canCapture(Square origin, Square target)
    {
        Piece attacker = origin.getPiece();
        Piece targetPiece = target.getPiece();

        boolean canCapture;

        for (Move move : attacker.getLegalMoves())
        {
            int x = move.getX();
            int y = move.getY();
            int targetX = target.getXIndex();
            int targetY = target.getYIndex();

            if (x == targetX && y == targetY && targetPiece != null && targetPiece.getColor() != attacker.getColor())
            {
                return true;
            }
        }

        return false;
    }


    /**
     * Tests if x- and y-coordinates are within chessboard bounds
     * @param x column on the chessboard
     * @param y row on the chessboard
     * @return true if coordinates are within bounds else false
     */
    public boolean isValidCoordinate(int x, int y)
    {
        return x >= 0 && x < squares.length && y >= 0 && y < squares[x].length;
    }

    /**
     * Gets an array containing all Piece objects on the chessboard
     * @return array with Piece
     */
    public Piece[] getPieces()
    {
        int index = 0;
        Piece[] pieces = new Piece[32];
        Square[][] squares = getSquaresAs2D();

        for (int i = 0; i < squares.length; i++)
        {
            for (int j = 0; j < squares[i].length; j++)
            {
                pieces[index++] = squares[i][j].getPiece();
            }

            if (i == 1)
            {
                i = 5;
            }
        }
        return pieces;
    }

    /**
     * gets an array with all Square objects
     * @return array with Square objects
     */
    public Square[] getSquares()
    {
        Square[] allSquares = new Square[ROWS * COLUMNS];
        int index = 0;

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLUMNS; j++)
            {
                allSquares[index++] = squares[i][j];
            }
        }
        return allSquares;
    }

    /**
     * Gets a 2D array with Square objects
     * @return 2D array Square[][]
     */
    public Square[][] getSquaresAs2D()
    {
        return squares;
    }

    /**
     * gets the size of Square objects
     * @return the size of the Square objects
     */
    public double getSquareSize()
    {
        return squareSize;
    }


    public boolean isWhite()
    {
        return isWhite;
    }

    public void setSquareSize(double squareSize)
    {
        this.squareSize = squareSize;
    }

    public void setWhite(boolean white)
    {
        isWhite = white;
    }
}

package model.chesspieces;

import model.ChessColorEnum;
import model.GameBoard;
import model.Move;
import model.Square;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece
{
    private final ChessColorEnum color;
    private final Image image;
    private final String pieceName;
    private final int value;
    protected List<Move> legalMoves;

    public Piece(String imageURL, String name, int value, boolean isWhite)
    {
        this.color = isWhite ? ChessColorEnum.WHITE : ChessColorEnum.BLACK;
        this.image = new Image(imageURL);
        this.pieceName = name;
        this.value = value;
        this.legalMoves = new ArrayList<>();
    }

    public abstract void computeLegalMoves(GameBoard gameBoard);

    public boolean isValidMove(int row, int column, int size)
    {
        return row >= 0 && row < size && column >= 0 && column < size;
    }

    void fillWithPossibleMoves(int[][] possibleMoves, List<Move> moveList, GameBoard gameBoard)
    {
        Square square = gameBoard.getSquareHoldingPiece(this);

        int x = square.getXIndex();
        int y = square.getYIndex();

        for (int[] possibleMove : possibleMoves)
        {
            int targetX = x + possibleMove[0];
            int targetY = y + possibleMove[1];

            if (isValidMove(targetX, targetY, 8))
            {
                Piece targetedPiece = gameBoard.getPieceAt(targetX, targetY);
                if (targetedPiece == null)
                {
                    moveList.add(new Move(targetX, targetY));
                }
                else
                {
                    if (targetedPiece.getColor() != this.color)
                    {
                        moveList.add(new Move(targetX, targetY, true));
                    }
                }
            }
        }
    }

    void getBishopMoves(List<Move> moveList, GameBoard gameBoard)
    {
        int[][] directions = {
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        validateTargetSquares(directions, moveList, gameBoard);
    }

    void getRookMoves(List<Move> moveList, GameBoard gameBoard)
    {
        int[][] directions = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };
        validateTargetSquares(directions, moveList, gameBoard);
    }

    private void validateTargetSquares(int[][] directions, List<Move> moveList, GameBoard gameBoard)
    {
        Square square = gameBoard.getSquareHoldingPiece(this);
        int x = square.getXIndex();
        int y = square.getYIndex();

        for (int[] direction : directions)
        {
            int dx = direction[0];
            int dy = direction[1];

            for (int j = 1; j <= 8; j++)
            {
                int targetX = x + j * dx;
                int targetY = y + j * dy;

                if (isValidMove(targetX, targetY, 8))
                {
                    Piece targetedPiece = gameBoard.getPieceAt(targetX, targetY);
                    if (targetedPiece == null)
                    {
                        moveList.add(new Move(targetX, targetY));
                    }
                    else
                    {
                        if (targetedPiece.getColor() != this.color)
                        {
                            moveList.add(new Move(targetX, targetY, true));
                        }
                        break;
                    }
                }
                else
                {
                    break;
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return color.toString().toLowerCase() + " " + pieceName + " " + value;
    }

    public Image getImage()
    {
        return image;
    }

    public ChessColorEnum getColor()
    {
        return color;
    }

    public String getPieceName()
    {
        return pieceName;
    }

    public List<Move> getLegalMoves()
    {
        return legalMoves;
    }
}

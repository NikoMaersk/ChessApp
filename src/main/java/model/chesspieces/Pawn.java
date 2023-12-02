package model.chesspieces;

import model.ChessColorEnum;
import model.GameBoard;
import model.Move;
import model.Square;

import java.io.File;

public class Pawn extends Piece
{
    private boolean firstMove = true;

    public Pawn(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wp.png").getAbsolutePath() : new File("src/main/resources/images/bp.png").getAbsolutePath(), "pawn", isWhite);
    }

    @Override
    public void computeLegalMoves(GameBoard gameBoard)
    {
        super.legalMoves.clear();
        Square square = gameBoard.getSquareHoldingPiece(this);
        int x = square.getXIndex();
        int y = square.getYIndex();

        int targetY = y;

        if (gameBoard.isWhite())
        {
            switch (this.getColor())
            {
                case WHITE -> --targetY;
                case BLACK -> ++targetY;
            }

            if (firstMove)
            {
                int offset = (this.getColor() == ChessColorEnum.WHITE) ? -2 : 2;

                if (gameBoard.getSquaresAs2D()[x][targetY].getPiece() == null && gameBoard.getSquaresAs2D()[x][y + offset].getPiece() == null)
                {
                    super.legalMoves.add(new Move(x, y + offset));
                }
            }
        }
        else
        {
            switch (this.getColor())
            {
                case WHITE -> ++targetY;
                case BLACK -> --targetY;
            }

            if (firstMove)
            {
                int offset = (this.getColor() == ChessColorEnum.WHITE) ? 2 : -2;

                if (gameBoard.getSquaresAs2D()[x][targetY].getPiece() == null && gameBoard.getSquaresAs2D()[x][y + offset].getPiece() == null)
                {
                    super.legalMoves.add(new Move(x, y + offset));
                }
            }
        }

        if (gameBoard.isValidCoordinate(x, targetY) && gameBoard.getSquaresAs2D()[x][targetY].getPiece() == null)
        {
            super.legalMoves.add(new Move(x, targetY));
        }


        if (canCapture(gameBoard, x - 1, targetY))
            super.legalMoves.add(new Move(x - 1, targetY, true));
        if (canCapture(gameBoard, x + 1, targetY))
            super.legalMoves.add(new Move(x + 1, targetY, true));
    }

    private boolean canCapture(GameBoard gameBoard, int x, int y)
    {
        if (!gameBoard.isValidCoordinate(x, y))
            return false;

        Piece targetPiece = gameBoard.getSquaresAs2D()[x][y].getPiece();
        return targetPiece != null && targetPiece.getColor() != this.getColor();
    }

    public boolean isFirstMove()
    {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove)
    {
        this.firstMove = firstMove;
    }
}

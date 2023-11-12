package model.chesspieces;

import model.ChessColorEnum;
import model.GameBoard;
import model.Move;
import model.Square;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece
{
    private boolean firstMove = true;

    public Pawn(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wp.png").getAbsolutePath() : new File("src/main/resources/images/bp.png").getAbsolutePath(), "pawn", isWhite);
    }

    @Override
    public List<Move> getLegalMoves(GameBoard gameBoard)
    {
        List<Move> moveList = new ArrayList<>();
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
                moveList.add(new Move(x, y + offset));
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
                moveList.add(new Move(x, y + offset));
            }
        }

        moveList.add(new Move(x, targetY));

        return moveList;
    }
}

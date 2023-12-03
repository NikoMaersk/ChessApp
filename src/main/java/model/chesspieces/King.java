package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.List;

public class King extends Piece
{
    public King(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wk.png").getAbsolutePath() : new File("src/main/resources/images/bk.png").getAbsolutePath(), "king", 100, isWhite);
    }

    @Override
    public void computeLegalMoves(GameBoard gameBoard)
    {
        super.legalMoves.clear();

        int[][] possibleMoves = {
                {1, 0},
                {-1, 0},
                {0, -1},
                {0, 1},
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1}
        };

        super.fillWithPossibleMoves(possibleMoves, super.legalMoves, gameBoard);
    }
}

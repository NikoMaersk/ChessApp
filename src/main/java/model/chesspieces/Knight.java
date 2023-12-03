package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece
{
    public Knight(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wn.png").getAbsolutePath() : new File("src/main/resources/images/bn.png").getAbsolutePath(),
                "knight", 3, isWhite);
    }

    @Override
    public void computeLegalMoves(GameBoard gameBoard)
    {
        super.legalMoves.clear();

        int[][] possibleMoves = {
                {2, 1},
                {-2, 1},
                {2, -1},
                {-2, -1},
                {1, 2},
                {-1, 2},
                {1, -2},
                {-1, -2}
        };

        super.fillWithPossibleMoves(possibleMoves, super.legalMoves, gameBoard);
    }
}

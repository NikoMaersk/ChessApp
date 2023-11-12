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
                "knight", isWhite);
    }

    @Override
    public List<Move> getLegalMoves(GameBoard gameBoard)
    {
        List<Move> moveList = new ArrayList<>();

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

        super.fillWithPossibleMoves(possibleMoves, moveList, gameBoard);

        return moveList;
    }
}

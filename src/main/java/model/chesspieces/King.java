package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece
{
    public King(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wk.png").getAbsolutePath() : new File("src/main/resources/images/bk.png").getAbsolutePath(), "king", isWhite);
    }

    @Override
    public List<Move> getLegalMoves(GameBoard gameBoard)
    {
        List<Move> moveList = new ArrayList<>();

        int[][] possibleMoves = {
                {1, 0},
                {-1, 0},
                {0, -1},
                {0, 1},
        };

        fillWithPossibleMoves(possibleMoves, moveList, gameBoard);

        return moveList;
    }
}

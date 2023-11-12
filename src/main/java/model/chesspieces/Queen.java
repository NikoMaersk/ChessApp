package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece
{
    public Queen(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wq.png").getAbsolutePath() : new File("src/main/resources/images/bq.png").getAbsolutePath(), "queen", isWhite);
    }

    @Override
    public List<Move> getLegalMoves(GameBoard gameBoard)
    {
        List<Move> moveList = new ArrayList<>();

        getBishopMoves(moveList, gameBoard);
        getRookMoves(moveList, gameBoard);

        return moveList;
    }
}

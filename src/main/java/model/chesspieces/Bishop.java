package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wb.png").getAbsolutePath() : new File("src/main/resources/images/bb.png").getAbsolutePath(), "bishop", isWhite);
    }

    @Override
    public List<Move> getLegalMoves(GameBoard gameBoard)
    {
        List<Move> moveList = new ArrayList<>();
        getBishopMoves(moveList, gameBoard);

        return moveList;
    }
}

package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece
{
    public Rook(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wr.png").getAbsolutePath() : new File("src/main/resources/images/br.png").getAbsolutePath(), "rook", isWhite);
    }

    @Override
    public List<Move> getLegalMoves(GameBoard gameBoard)
    {
        List<Move> moveList = new ArrayList<>();

        getRookMoves(moveList, gameBoard);

        return moveList;
    }
}

package model.chesspieces;

import model.GameBoard;
import model.Move;

import java.io.File;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(boolean isWhite)
    {
        super(isWhite ?
                new File("src/main/resources/images/wb.png").getAbsolutePath() : new File("src/main/resources/images/bb.png").getAbsolutePath(),
                "bishop", isWhite);
    }

    @Override
    public void computeLegalMoves(GameBoard gameBoard)
    {
        super.legalMoves.clear();
        getBishopMoves(super.legalMoves, gameBoard);
    }
}

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
        super(isWhite ? new File("src/main/resources/images/wq.png").getAbsolutePath() : new File("src/main/resources/images/bq.png").getAbsolutePath(), "queen", 9, isWhite);
    }

    @Override
    public void computeLegalMoves(GameBoard gameBoard)
    {
        super.legalMoves.clear();

        getBishopMoves(super.legalMoves, gameBoard);
        getRookMoves(super.legalMoves, gameBoard);
    }
}

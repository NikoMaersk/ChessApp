package model.chesspieces;

import model.GameBoard;

import java.io.File;

public class Rook extends Piece
{
    public Rook(boolean isWhite)
    {
        super(isWhite ? new File("src/main/resources/images/wr.png").getAbsolutePath() : new File("src/main/resources/images/br.png").getAbsolutePath(), "rook", isWhite);
    }

    @Override
    public void computeLegalMoves(GameBoard gameBoard)
    {
        super.legalMoves.clear();

        getRookMoves(super.legalMoves, gameBoard);
    }
}

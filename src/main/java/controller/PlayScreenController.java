package controller;

import javafx.scene.input.MouseEvent;
import model.*;
import model.chesspieces.Piece;
import service.ObserverPlayer;
import com.example.chessmobile.Scenes;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.List;

public class PlayScreenController
{
    @FXML
    Canvas background, foreground;

    @FXML
    Label playerLabel, playerTimeLabel, opponentLabel, opponentTimeLabel;

    boolean isPlayerTurn = true;
    GameBoard gameBoard;
    Player player;
    TurnTimer playerTurnTimer;
    TurnTimer opponentTurnTimer;
    Piece selectedPiece;
    Square selectedSquare;

    public void initialize()
    {
        ObserverPlayer observer = ObserverPlayer.getObserver();
        observer.subscribe(Scenes.SETTING_SCREEN, this::setPlayer);
        foreground.setOnMousePressed(this::handleMousePress);
        /*
        foreground.setOnMouseDragged(e ->
        {
            if (selectedSquare != null && selectedPiece != null)
            {
                clearForeground();
                drawBoard();
                drawMoves(selectedPiece.getLegalMoves());
                drawPieceXY(selectedSquare, e.getX(), e.getY());
            }
        });
        */
    }

    private void handleMousePress(MouseEvent e)
    {
        // This stops the player from making a move if not their turn
        /*
        if (!isPlayerTurn)
        {
            return;
        }
        */

        if (selectedSquare != null
                && selectedPiece != null
                && gameBoard.canMoveTo(selectedSquare, getSelectedSquare(e.getX(), e.getY()))
                && selectedPiece.getColor() == player.getColor())
        {
            handleValidMove(e);
        }
        else if (selectedSquare != null
                && selectedPiece != null
                && selectedPiece == getSelectedSquare(e.getX(), e.getY()).getPiece())
        {
            clearLegalMoves(selectedPiece.getLegalMoves());
            selectedPiece = null;
            selectedSquare = null;
        }
        else
        {
            handleInvalidMove(e);
            System.out.println("Invalid move");
        }
    }

    public void handleValidMove(MouseEvent e)
    {
        gameBoard.move(selectedSquare, getSelectedSquare(e.getX(), e.getY()));
        clearSelectedSquare();
        clearLegalMoves(selectedPiece.getLegalMoves());
        drawBoard();
        selectedPiece = null;
        selectedSquare = null;
        changeTurn();
    }

    public void handleInvalidMove(MouseEvent e)
    {
        if (selectedPiece != null)
        {
            clearLegalMoves(selectedPiece.getLegalMoves());
        }

        selectedSquare = getSelectedSquare(e.getX(), e.getY());
        selectedPiece = selectedSquare.getPiece();

        if (selectedPiece != null && selectedPiece.getColor() == player.getColor())
        {
            System.out.println(selectedPiece);
            selectedPiece.computeLegalMoves(gameBoard);
            drawMoves(selectedPiece.getLegalMoves());
            System.out.println(gameBoard.isKingInCheck(player.getColor()));
        }
    }


    private void drawBoard()
    {
        for (Square square : gameBoard.getSquares())
        {
            drawSquare(square);
            if (square.getPiece() != null)
            {
                drawPiece(square);
            }
        }
    }

    private void ComputeAllLegalMoves() {
        for (Square square : gameBoard.getSquares()) {
            if (square.getPiece() != null) {
                square.getPiece().computeLegalMoves(gameBoard);
            }
        }
    }

    private void drawSquare(Square square)
    {
        GraphicsContext gc = background.getGraphicsContext2D();
        gc.setFill(square.getColor());
        gc.fillRect(square.getXPos(), square.getYPos(), square.getSquareSize(), square.getSquareSize());
    }


    private void drawPiece(Square square)
    {
        Piece piece = square.getPiece();
        GraphicsContext gc = foreground.getGraphicsContext2D();
        gc.drawImage(piece.getImage(), square.getXPos(), square.getYPos(), square.getSquareSize(), square.getSquareSize());
    }


    public void drawPieceXY(Square square, double x, double y)
    {
        Piece piece = square.getPiece();
        double size = square.getSquareSize();
        GraphicsContext gc = foreground.getGraphicsContext2D();

        gc.drawImage(piece.getImage(), x - (size / 2), y - (size / 2), size, size);
    }

    private void drawSquareAndPiece(Square square)
    {
        drawSquare(square);
        if (square.getPiece() != null)
        {
            drawPiece(square);
        }
    }


    private void clearSquare(double x, double y, double squareSize)
    {
        foreground.getGraphicsContext2D().clearRect(x, y, squareSize, squareSize);
    }

    private void clearSelectedSquare()
    {
        foreground.getGraphicsContext2D()
                .clearRect(selectedSquare.getXPos(),
                selectedSquare.getYPos(),
                selectedSquare.getSquareSize(),
                selectedSquare.getSquareSize());
    }

    private void clearBackground()
    {
        GraphicsContext gc = background.getGraphicsContext2D();
        gc.clearRect(0,0, background.getHeight(), background.getWidth());
    }

    private void clearForeground()
    {
        GraphicsContext gc = foreground.getGraphicsContext2D();
        gc.clearRect(0,0, foreground.getHeight(), foreground.getWidth());
    }


    private Square getSelectedSquare(double xPos, double yPos)
    {
        int x = (int)(xPos / (foreground.getWidth() / 8));
        int y = (int)(yPos / (foreground.getHeight() / 8));
        System.out.printf("x index: %d - y index: %d%n",x, y);

        return gameBoard.getSquaresAs2D()[x][y];
    }

    private void setPlayer(Player player)
    {
        this.player = player;
        if (this.player != null)
        {
            playerTurnTimer = this.player.getTimer();
            opponentTurnTimer = new TurnTimer(playerTurnTimer.getTurnTimeMinutes(), playerTurnTimer.getTurnTimeSeconds());
            setTimeLabels();
            setPlayerLabels();
            gameBoard = new GameBoard(background.getWidth(), this.player.isWhite());
            drawBoard();
        }
    }

    private void setTimeLabels()
    {
        playerTimeLabel.textProperty().bind(Bindings.format("%d:%02d", playerTurnTimer.getTurnTimeMinutesProperty(), playerTurnTimer.getTurnTimeSecondsProperty()));
        opponentTimeLabel.textProperty().bind(Bindings.format("%d:%02d", opponentTurnTimer.getTurnTimeMinutesProperty(), opponentTurnTimer.getTurnTimeSecondsProperty()));
    }

    private void setPlayerLabels()
    {
        playerLabel.setText(this.player.getUserName());
        opponentLabel.setText("Opponent");
    }

    private void startPlayerTurn()
    {
        playerTurnTimer.startTurn();
        opponentTurnTimer.endTurn();
        isPlayerTurn = true;
    }

    private void endPlayerTurn()
    {
        playerTurnTimer.endTurn();
        opponentTurnTimer.startTurn();
        isPlayerTurn = false;
    }

    private void changeTurn()
    {
        if (isPlayerTurn)
        {
            endPlayerTurn();
        }
        else
        {
            startPlayerTurn();
        }
    }

    private void drawMoves(List<Move> moveList)
    {
        GraphicsContext gc = foreground.getGraphicsContext2D();
        Color opaqueBlack = new Color(0, 0, 0, 0.3f);
        gc.setFill(opaqueBlack);
        gc.setStroke(opaqueBlack);

        int lineWidth = 4;
        gc.setLineWidth(lineWidth);

        double squareSize = gameBoard.getSquareSize();
        double offset = squareSize / 3;

        for (Move moves : moveList)
        {
            if (moves.isSquareOccupied())
            {
                gc.strokeOval((moves.getX() * squareSize + (lineWidth / 2.0)), moves.getY() * squareSize + (lineWidth / 2.0), squareSize - lineWidth, squareSize - lineWidth);
            }
            else
            {
                gc.fillOval(moves.getX() * squareSize + offset, moves.getY() * squareSize + offset, offset, offset);
            }
        }
    }

    private void clearLegalMoves(List<Move> moveList)
    {
        GraphicsContext gc = foreground.getGraphicsContext2D();
        double squareSize = gameBoard.getSquareSize();

        for (Move move : moveList)
        {
            gc.clearRect(move.getX() * squareSize, move.getY() * squareSize, squareSize, squareSize);
            Square square = gameBoard.getSquaresAs2D()[move.getX()][move.getY()];
            drawSquareAndPiece(square);
        }
    }
}

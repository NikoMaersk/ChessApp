package controller;

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

    GameBoard gameBoard;
    Piece selectedPiece;
    Player player;
    TurnTimer playerTurnTimer;
    TurnTimer opponentTurnTimer;


    public void initialize()
    {
        ObserverPlayer observer = ObserverPlayer.getObserver();
        observer.subscribe(Scenes.SETTING_SCREEN, this::setPlayer);

        // to be deleted
        foreground.setOnMousePressed(e ->
        {
            selectedPiece = getPiece(e.getX(), e.getY());
            if (selectedPiece != null)
            {
                System.out.println(selectedPiece);
                drawMoves(selectedPiece.getLegalMoves(gameBoard));
            }
        });
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

    private void clearSquare(double x, double y, double squareSize)
    {
        foreground.getGraphicsContext2D().clearRect(x, y, squareSize, squareSize);
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

    private Piece getPiece(double x, double y)
    {
        for (Square square : gameBoard.getSquares())
        {
            double squareX = square.getXPos();
            double squareY = square.getYPos();
            double squareSize = square.getSquareSize();

            if (x >= squareX && x < squareX + squareSize)
            {
                if (y >= squareY && y < squareY + squareSize)
                {
                    return square.getPiece();
                }
            }
        }
        return null;
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
    }

    private void endPlayerTurn()
    {
        playerTurnTimer.endTurn();
        opponentTurnTimer.startTurn();
    }

    private void changeTurn()
    {
        if (playerTurnTimer.isRunning())
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
}

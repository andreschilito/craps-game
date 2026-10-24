package com.example.crapsgame262.controllers;

import com.example.crapsgame262.models.AlertBox;
import com.example.crapsgame262.models.Game;
import com.example.crapsgame262.models.GameStatus;
import com.example.crapsgame262.models.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameController {

    private Player currentPlayer;

    private final Game game = new Game();
    private final AlertBox alertBox = new AlertBox();

    @FXML
    private ImageView dice1ImageView;

    @FXML
    private ImageView dice2ImageView;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label rollLabel;

    @FXML
    private Label pointLabel;

    @FXML
    private TextArea resultTextArea;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        nicknameLabel.setText(this.currentPlayer.getNickname());

        rollLabel.setText("0");
        pointLabel.setText("-");
        resultTextArea.clear();
        appendMessage("¡Hola " + this.currentPlayer.getNickname()
                + "! Pulsa \"Lanzar\" para hacer tu tiro de salida.");
    }

    @FXML
    void onMouseClickedDropButton(MouseEvent event) {
        // Si la ronda ya terminó, hay que esperar a que el jugador decida
        // si juega otra (lo pregunta finishRound()).
        if (game.isFinished()) {
            return;
        }

        int total = game.roll();

        showDice();
        rollLabel.setText(String.valueOf(total));
        pointLabel.setText(game.getPoint() == 0 ? "-" : String.valueOf(game.getPoint()));

        showRollResult(total);

        if (game.isFinished()) {
            finishRound();
        }
    }

    @FXML
    void onMouseClickedHelpButton(MouseEvent event) {
        alertBox.showAlertBox(
                "Craps Game - Reglas",
                "Reglas del Craps",
                "1. Tiro de salida: si sacas 7 u 11 ganas; si sacas 2, 3 o 12 pierdes.\n"
                        + "2. Cualquier otro total (4, 5, 6, 8, 9, 10) fija el punto.\n"
                        + "3. Repite el punto antes de sacar un 7 para ganar.");
    }

    private void showDice() {
        dice1ImageView.setImage(new Image(
                getClass().getResourceAsStream(game.getDice1().getImagePath())));
        dice2ImageView.setImage(new Image(
                getClass().getResourceAsStream(game.getDice2().getImagePath())));
    }

    private void showRollResult(int total) {
        switch (game.getStatus()) {
            case WON:
                if (total == 7 || total == 11) {
                    appendMessage("Tiro de salida: " + total + ". ¡Natural, ganaste!");
                } else {
                    appendMessage("Sacaste el punto " + game.getPoint() + " otra vez. ¡Ganaste!");
                }
                break;
            case LOST:
                if (total == 2 || total == 3 || total == 12) {
                    appendMessage("Tiro de salida: " + total + ". ¡Craps, perdiste!");
                } else {
                    appendMessage("Sacaste 7 antes del punto " + game.getPoint() + ". Perdiste.");
                }
                break;
            case POINT:
                if (total == game.getPoint()) {
                    appendMessage("Se fijó el punto " + game.getPoint() + ". ¡Sigue lanzando!");
                } else {
                    appendMessage("Total " + total + ". No es el punto (" + game.getPoint()
                            + ") ni 7. ¡Sigue lanzando!");
                }
                break;
            default:
                break;
        }
    }

    private void finishRound() {
        boolean playerWon = game.getStatus() == GameStatus.WON;
        String result = playerWon ? "¡Ganaste!" : "¡Perdiste!";

        appendMessage("Fin de la ronda. " + result);

        boolean playAgain = alertBox.showConfirmBox(
                "Craps Game",
                result,
                "¿Quieres jugar otra ronda?");

        if (playAgain) {
            startNewRound();
        } else {
            Platform.exit();
        }
    }

    private void startNewRound() {
        game.reset();

        rollLabel.setText("0");
        pointLabel.setText("-");
        dice1ImageView.setImage(null);
        dice2ImageView.setImage(null);

        appendMessage("Nueva ronda. ¡Lanza los dados!");
    }

    private void appendMessage(String message) {
        resultTextArea.appendText(message + "\n");
    }
}

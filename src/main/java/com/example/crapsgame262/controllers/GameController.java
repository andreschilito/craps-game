package com.example.crapsgame262.controllers;

import com.example.crapsgame262.models.AlertBox;
import com.example.crapsgame262.models.Game;
import com.example.crapsgame262.models.GameStatus;
import com.example.crapsgame262.models.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.example.crapsgame262.models.AlertBoxInterface;

/**
 * Controller for the Craps game view.
 *
 * <p>Handles the dice rolls, updates the dice images and displays the
 * current roll, the established point and the win/loss summary.</p>
 *
 * @author Jhonathan Delgado
 * @version 1.0
 * @since 1.0
 */
public class GameController {

    /** Player whose name is displayed in the view. */
    private Player currentPlayer;

    private final Game game = new Game();
    private final AlertBoxInterface alertBox = new AlertBox();

    /** Image view that shows the first die. */
    @FXML
    private ImageView dice1ImageView;

    /** Image view that shows the second die. */
    @FXML
    private ImageView dice2ImageView;

    /** Label that shows the player's nickname. */
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

    /**
     * Sets the player, starts a new game and refreshes the view.
     *
     * @param currentPlayer player that starts the game
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        nicknameLabel.setText(this.currentPlayer.getNickname());

        rollLabel.setText("0");
        pointLabel.setText("-");
        resultTextArea.clear();
        appendMessage("¡Hola " + this.currentPlayer.getNickname()
                + "! Pulsa \"Lanzar\" para hacer tu tiro de salida.");
    }

    /**
     * Handles the click on the roll button.
     *
     * <p>If the previous round has finished, a new round is started.
     * Otherwise, both dice are rolled, the game is updated and the
     * view is refreshed.</p>
     *
     * @param event mouse event fired by the roll button
     */
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
        appendMessage("Juegos ganados: " + game.getGamesWon()
            + " | Juegos perdidos: " + game.getGamesLost());

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
    @FXML
    private void onHelpClick() {
        alertBox.showAlertBox(
            "Craps Game - Instructions",
            "How to Play Craps",
            "1. Roll two dice to start.\n"
                    + "2. A first roll of 7 or 11 wins immediately.\n"
                    + "3. A first roll of 2, 3, or 12 loses immediately.\n"
                    + "4. Any other first roll establishes the point.\n"
                    + "5. Roll the point again before rolling a 7 to win.");
    }

    /**
     * Changes the text of the roll button.
     *
     * @param event mouse event whose source is the roll button
     * @param text new button text
     */
    private void setRollButtonText(MouseEvent event, String text) {
        if (event.getSource() instanceof Button) {
            ((Button) event.getSource()).setText(text);
        }
    }
}

package com.example.crapsgame262.controllers;

import com.example.crapsgame262.models.Dice;
import com.example.crapsgame262.models.Game;
import com.example.crapsgame262.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

    /** First die used during the game. */
    private final Dice dice1 = new Dice();

    /** Second die used during the game. */
    private final Dice dice2 = new Dice();

    /** Current game state and scoreboard. */
    private Game game = new Game();

    /** Player whose name is displayed in the view. */
    private Player currentPlayer;

    /** Image view that shows the first die. */
    @FXML
    private ImageView dice1ImageView;

    /** Image view that shows the second die. */
    @FXML
    private ImageView dice2ImageView;

    /** Label that shows the player's nickname. */
    @FXML
    private Label nicknameLabel;

    /** Label that shows the total obtained in the last roll. */
    @FXML
    private Label dropLabel;

    /** Label that shows the current point. */
    @FXML
    private Label pointLabel;

    /** Text area that shows the number of games won and lost. */
    @FXML
    private TextArea summaryTextArea;

    /**
     * Returns the player associated with this controller.
     *
     * @return the current player
     */
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
        this.game = new Game();
        this.nicknameLabel.setText(this.currentPlayer.getNickname());
        updateGameView();
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
        if (game.isGameOver()) {
            startNewRound(event);
            return;
        }

        int value1 = dice1.roll();
        int value2 = dice2.roll();

        setDiceImage(dice1ImageView, dice1);
        setDiceImage(dice2ImageView, dice2);

        game.play(value1, value2);
        updateGameView();

        if (game.isGameOver()) {
            setRollButtonText(event, "Nuevo juego");
        }
    }

    /**
     * Starts a new round and restores the roll button text.
     *
     * @param event mouse event fired by the roll button
     */
    private void startNewRound(MouseEvent event) {
        game.newRound();
        updateGameView();
        setRollButtonText(event, "Lanzar");
    }

    /**
     * Updates the roll, point and scoreboard labels.
     */
    private void updateGameView() {
        int lastRoll = game.getLastRoll();
        int point = game.getPoint();

        dropLabel.setText(lastRoll == 0 ? "-" : String.valueOf(lastRoll));
        pointLabel.setText(point == 0 ? "-" : String.valueOf(point));
        summaryTextArea.setText(
                "Juegos ganados: " + game.getGamesWon()
                        + System.lineSeparator()
                        + "Juegos perdidos: " + game.getGamesLost()
        );
    }

    /**
     * Loads and displays the image that corresponds to the current die value.
     *
     * @param imageView image view where the dice image is displayed
     * @param dice die whose image is loaded
     */
    private void setDiceImage(ImageView imageView, Dice dice) {
        imageView.setImage(
                new Image(
                        getClass().getResourceAsStream(dice.getImagePath())
                )
        );
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

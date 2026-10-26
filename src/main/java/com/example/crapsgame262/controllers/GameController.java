package com.example.crapsgame262.controllers;

import com.example.crapsgame262.models.Dice;
import com.example.crapsgame262.models.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameController {

    private Player currentPlayer;

    @FXML
    private ImageView dice1ImageView;

    @FXML
    private ImageView dice2ImageView;

    @FXML
    private Label nicknameLabel;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        nicknameLabel.setText(this.currentPlayer.getNickname());
    }

    @FXML
    void onMouseClickedDropButton(MouseEvent event) {
        Dice dice1 = new Dice();
        dice1.roll();

        dice1ImageView.setImage(
                new Image(
                        getClass().getResourceAsStream(
                                dice1.getImagePath()
                        )
                )
        );

        Dice dice2 = new Dice();
        dice2.roll();

        dice2ImageView.setImage(
                new Image(
                        getClass().getResourceAsStream(
                                dice2.getImagePath()
                        )
                )
        );
    }
    @FXML
    private void onHelpClick() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones del Juego - Craps");
        alert.setHeaderText("¿Cómo jugar a Craps?");
        alert.setContentText(
                "1. Realiza tu tiro inicial tirando los dados.\n" +
                        "2. Si en tu primer tiro sacas un 7 o un 11: ¡Ganas automáticamente!\n" +
                        "3. Si sacas un 2, 3 o 12: Es 'Craps' y pierdes.\n" +
                        "4. Si sacas 4, 5, 6, 8, 9 o 10: Ese valor se convierte en tu 'Punto'.\n" +
                        "5. Con el 'Punto' establecido, sigue tirando: Si vuelves a sacar ese mismo número antes que un 7, ¡ganas! Si sacas un 7 antes del punto, pierdes."
        );
        alert.showAndWait();
    }
}

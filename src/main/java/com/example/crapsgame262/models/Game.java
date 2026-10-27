package com.example.crapsgame262.models;

/**
 * Modelo con la lógica del juego de Craps.
 *
 * No conoce JavaFX ni la interfaz: solo lanza los dados, aplica las reglas
 * y expone el estado de la partida. El {@code GameController} se encarga
 * de leer este estado y mostrarlo en pantalla.
 */
public class Game {

    private final Dice dice1;
    private final Dice dice2;

    private GameStatus status;
    private int point;
    private int lastTotal;
    private int gamesWon;
    private int gamesLost;

    public Game() {
        dice1 = new Dice();
        dice2 = new Dice();
        reset();
    }

    /**
     * Lanza los dos dados y resuelve la jugada según la fase actual.
     *
     * @return el total obtenido en los dados.
     */
    public int roll() {
        lastTotal = dice1.roll() + dice2.roll();

        if (status == GameStatus.COME_OUT) {
            resolveComeOut(lastTotal);
        } else if (status == GameStatus.POINT) {
            resolvePoint(lastTotal);
        }

        return lastTotal;
    }

    /**
     * Tiro de salida:
     * 7 u 11 gana, 2/3/12 pierde (craps) y cualquier otro total fija el punto.
     */
    private void resolveComeOut(int total) {
        if (total == 7 || total == 11) {
            status = GameStatus.WON;
            gamesWon++;
        } else if (total == 2 || total == 3 || total == 12) {
            status = GameStatus.LOST;
            gamesLost++;
        } else {
            point = total;
            status = GameStatus.POINT;
        }
    }

    /**
     * Fase de punto: repetir el punto gana; sacar 7 antes pierde.
     * Cualquier otro total no cambia nada y se sigue lanzando.
     */
    private void resolvePoint(int total) {
        if (total == point) {
            status = GameStatus.WON;
            gamesWon++;
        } else if (total == 7) {
            status = GameStatus.LOST;
            gamesLost++;
        }
    }

    /** Deja la partida lista para una nueva ronda. */
    public void reset() {
        status = GameStatus.COME_OUT;
        point = 0;
        lastTotal = 0;
    }

    public boolean isFinished() {
        return status == GameStatus.WON || status == GameStatus.LOST;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getPoint() {
        return point;
    }

    public int getLastTotal() {
        return lastTotal;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public Dice getDice1() {
        return dice1;
    }

    public Dice getDice2() {
        return dice2;
    }
}

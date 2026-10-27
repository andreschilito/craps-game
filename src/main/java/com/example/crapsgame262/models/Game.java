package com.example.crapsgame262.models;

/**
 * Represents the state and rules of a Craps game.
 *
 * <p>This class is currently a skeleton. The game rules and scoreboard
 * updates will be implemented in later iterations.</p>
 *
 * @author Jhonathan Delgado
 * @version 1.0
 * @since 1.0
 */
public class Game {

    /**
     * Plays one round using the values of the two dice.
     *
     * @param die1 value of the first die
     * @param die2 value of the second die
     */
    public void play(int die1, int die2) {
        // TODO: implement the Craps rules
    }

    /**
     * Starts a new round while keeping the accumulated score.
     */
    public void newRound() {
        // TODO: reset the point and the round result
    }

    /**
     * Returns the current point.
     *
     * @return the point, or {@code 0} if no point has been established
     */
    public int getPoint() {
        return 0;
    }

    /**
     * Returns the total obtained in the last roll.
     *
     * @return the sum of the last two dice
     */
    public int getLastRoll() {
        return 0;
    }

    /**
     * Returns the number of games won.
     *
     * @return the accumulated number of wins
     */
    public int getGamesWon() {
        return 0;
    }

    /**
     * Returns the number of games lost.
     *
     * @return the accumulated number of losses
     */
    public int getGamesLost() {
        return 0;
    }

    /**
     * Indicates whether the current round has finished.
     *
     * @return {@code true} if the round is over; {@code false} otherwise
     */
    public boolean isGameOver() {
        return false;
    }
}

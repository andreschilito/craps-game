package com.example.crapsgame262.models;

/**
 * Estados posibles de una partida de Craps.
 *
 * COME_OUT -> tiro de salida. POINT -> hay punto fijado.
 * WON y LOST son estados finales.
 */
public enum GameStatus {
    COME_OUT,
    POINT,
    WON,
    LOST
}

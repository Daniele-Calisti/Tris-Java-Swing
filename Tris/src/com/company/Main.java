package com.company;

import gameEngine.game;
import gui.gui;
import utility.Dimensioni;
import utility.Punto;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JButton[] griglia;
    public static JPanel p;
    public static JPanel all;
    public static JFrame finestra;
    public static int listener;//0 se 1 vs 1, 1 se 1 vs CPU

    public static void main(String[] args) {

        game.startNewGame(listener);

    }

}

package gameEngine;

import com.company.Main;
import gui.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import Listener.*;
import utility.Dimensioni;
import utility.Punto;


import static Listener.buttonListener.mat;
import static Listener.buttonListener.row;
import static Listener.buttonListener.col;
import static com.company.Main.*;

public class game
{
    public static int turno = 0;

    public static void startNewGame(int listener)
    {
        int n;
        int click = 0;
        do {
            Object[] options = {"1 vs 1",
                    "1 vs CPU"};
            n = JOptionPane.showOptionDialog(finestra,
                    "A quale modalità vuoi giocare?",
                    "Scelta modalità",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button title
            click++;
            if(click > 5)
            {
                n = -1;
                break;
            }

        }while(n == -1);

        //n --> 0 --> 1 vs 1
        //n --> 1 --> 1 vs CPU
        //n --> -1 --> finestra chiusa e quindi la ripropongo

       if(n == 0)
       {
           JOptionPane.showMessageDialog(finestra,
                   "Semplici regole prima di iniziare\n-Clicca su un bottone per fare la tua mossa\n-Se pensi di aver sbalgiato non preoccuparti, potrai cambiare la tua mossa premendo di nuovo sul bottone\n-Attento! Il programma sa se imbrogli, non scambiare le pedine dell'aversario con le tue.\n\n\t\t\tBUON DIVERTIMENTO!" );
           Main.listener = 0;
       }
       else if (n ==1)
       {
           JOptionPane.showMessageDialog(finestra,
                   "Semplici regole prima di iniziare\n-Clicca su un bottone per fare la tua mossa\n-Una volta effettuata la tua mossa non potrai più cambiare\n-La CPU è bravina in questo gioco, stai in guardia ;)\n\n\t\t\tBUON DIVERTIMENTO!" );
           Main.listener = 1;
       }else
       {
           System.exit(0);
       }


        gui g = new gui();

        //Creo il frame
        finestra = g.creaFrame("TRIS",new Dimensioni(900,900),new Punto(400,50));
        all = g.creaPanel(new BorderLayout());
        p = g.creaPanel(new GridLayout(3,3));

        JTextField info = g.creaTextField(finestra);

        griglia = g.getGriglia();

        p = g.creaGriglia(griglia, p);

        griglia = g.setListener(griglia,info,finestra,Main.listener);

        all.add(info,BorderLayout.NORTH);
        all.add(p,BorderLayout.CENTER);

        finestra.add(all);
    }

    public static int updateTurno(int turno)
    {
        if(turno == 0)
            return 1;
        if(turno == 1)
            return 2;

        return 0;
    }

    public static ImageIcon getPlayer()
    {
        ImageIcon i;
        if(turno == 0)
        {
            URL imgURL = gui.class.getResource("../resources/X.png");
            i = new ImageIcon(imgURL,"X");
        }else if(turno == 1)
        {
            URL imgURL = gui.class.getResource("../resources/O.png");
            i = new ImageIcon(imgURL,"O");
        }else
        {
            i = new ImageIcon("../invisible.png","null");
        }

        return i;
    }

    public static String getPlayerTurno()
    {
        String turnoS = "";
        if(turno == 0 || turno == 2)
            turnoS = "X";
        if(turno == 1)
            turnoS = "O";

        return turnoS;
    }

    /**
     * Funzione ricorsiva per calcolare il punteggio per riga ,per colonna o per diagonale
     * @param i --> indice della riga della matrice
     * @param j --> indice della colonna della matrice
     * @param controllo --> cosa controllare 0 per le righe, 1 per le colonne, 2 per la diagonale in alto a sx, 3 per la diagonale in alto a dx
     * @param mat --> matrice
     * @return ret --> punteggio della riga  o colonna
     */
    public static int checkMat(int i,int j, int[][] mat, int controllo)
    {
        int ret;
        int ans = -1;

        //Caso base per far terminare la ricorsione
        if(i == 3 || j == 3)
            return 0;

        if(controllo == 0)
            ans = mat[i][j] + checkMat(i,j+1,mat,controllo);
        else if (controllo == 1)
            ans = mat[i][j] + checkMat(i+1,j,mat,controllo);
        else if (controllo == 2)
            ans = mat[i][j] + checkMat(i+1,j+1,mat,controllo);
        else if (controllo == 3)
            ans = mat[i][j] + checkMat(i+1,j-1,mat,controllo);

        return ans;
    }

    /**
     * Funzione per colorare la riga/colonna/diagonale del vincente
     * @param i
     * @param j
     * @param controllo
     */
    public static void coloraVincitore(int i, int j,int controllo)
    {
        if(i == -2 && j == -2 && controllo == -2)
        {
            for(int x=0;x<9;x++)
            {
                griglia[x].setBackground(Color.yellow);
            }
        }else {
            if (i == 3 || j == 3)
                return;

            int index;

            if (i == 0) {
                index = j;
            } else if (i == 1) {
                index = 2 + i + j;
            } else {
                index = 3 * i + j;
            }

            griglia[index].setBackground(Color.green);

            if (controllo == 0)
                coloraVincitore(i, j + 1, controllo);
            else if (controllo == 1)
                coloraVincitore(i + 1, j, controllo);
            else if (controllo == 2)
                coloraVincitore(i + 1, j + 1, controllo);
            else if (controllo == 3)
                coloraVincitore(i + 1, j - 1, controllo);
        }

    }


    public static char checkWin(int score)
    {
        char win = 'a';

        if(score == 0)
            win = 'X';
        else if(score == 3)
            win = 'O';

        return win;
    }

    public static void showWinner(char v, JFrame frame)
    {
        if(v == 'O' || v == 'X')
            JOptionPane.showMessageDialog(frame,
                "Il giocatore " + v+" ha vinto!");
        else if(v == 'a')
        {
            JOptionPane.showMessageDialog(frame,
                    "Pareggio!");
        }else
        {
            JOptionPane.showMessageDialog(frame,
                    "La partita non è stata vinta da nessuno perchè qualcuno ha barato!",
                    "Gioco sleale!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Funzione per aggiornare la matrice da cui poi estrarre i dati per determinare il vincitore
     * @param appo --> Bottone cliccato
     * @param desc --> icona del bottone cliccato
     */
    public static void updateMat(String appo,String desc)
    {
        switch(appo)
        {
            case "1":
                if(desc.equals("X"))
                    mat[0][0] = 0;
                else if(desc.equals("O"))
                    mat[0][0] = 1;
                else
                    mat[0][0] = -10;
                row = 0;
                col = 0;
                break;

            case "2":
                if(desc.equals("X"))
                    mat[0][1] = 0;
                else if(desc.equals("O"))
                    mat[0][1] = 1;
                else
                    mat[0][1] = -10;
                buttonListener.row = 0;
                buttonListener.col = 1;
                break;

            case "3":
                if(desc.equals("X"))
                    mat[0][2] = 0;
                else if(desc.equals("O"))
                    mat[0][2] = 1;
                else
                    mat[0][2] = -10;
                row = 0;
                col = 2;
                break;

            case "4":
                if(desc.equals("X"))
                    mat[1][0] = 0;
                else if(desc.equals("O"))
                    mat[1][0] = 1;
                else
                    mat[1][0] = -10;
                row = 1;
                col = 0;
                break;

            case "5":
                if(desc.equals("X"))
                    mat[1][1] = 0;
                else if(desc.equals("O"))
                    mat[1][1] = 1;
                else
                    mat[1][1] = -10;
                row = 1;
                col = 1;
                break;

            case "6":
                if(desc.equals("X"))
                    mat[1][2] = 0;
                else if(desc.equals("O"))
                    mat[1][2] = 1;
                else
                    mat[1][2] = -10;
                row = 1;
                col = 2;
                break;

            case "7":
                if(desc.equals("X"))
                    mat[2][0] = 0;
                else if(desc.equals("O"))
                    mat[2][0] = 1;
                else
                    mat[2][0] = -10;
                row = 2;
                col = 0;
                break;

            case "8":
                if(desc.equals("X"))
                    mat[2][1] = 0;
                else if(desc.equals("O"))
                    mat[2][1] = 1;
                else
                    mat[2][1] = -10;
                row = 2;
                col = 1;
                break;

            case "9":
                if(desc.equals("X"))
                    mat[2][2] = 0;
                else if(desc.equals("O"))
                    mat[2][2] = 1;
                else
                    mat[2][2] = -10;
                row = 2;
                col = 2;
                break;
        }

    }

    /**
     * Funzione per resettare sia la matrice, sia la gui, per prepararsi ad una nuova giocata
     */
    public static void reset()
    {
        //Resetto la matrice
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                mat[i][j] = -10;
            }
        }

        //Resetto la finestra
        finestra.dispose();
        startNewGame(listener);

        //Porto il turno a 0
        game.turno = 0;

    }

    /**
     * Funzione per vedere se a fine partita, il punteggio è pari
     * @return
     */
    public static boolean isPari()
    {
        for (int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(mat[i][j] == -10)
                    return false;
            }
        }

        return true;
    }

    /**
     * Funzione per la scelta di giocare un' altra partita
     * @param frame --> finestra di gioco
     */
    public static void altraPartita(JFrame frame)
    {
        int n = JOptionPane.showConfirmDialog(
                frame,
                "Cominciare un'altra partita?",
                "Rigioca",
                JOptionPane.YES_NO_OPTION);

        if(n != 0)
            System.exit(0);
        else
            reset();
    }

    /**
     * Funzione per controllare se nella modalità 1 vs 1 un utente ha barato
     * @return true se l'utete ha barato, false altrimenti
     */
    public static boolean isCheating()
    {
        boolean cheat = false;
        int x=0,o=0;

        for(int i=0;i<3;i++)
        {
            for(int j = 0;j<3;j++)
            {
                if(mat[i][j] == 0)
                    x++;
                else if(mat[i][j] == 1)
                    o++;
            }
        }

        if( (x > o+1) || (o > x))
            cheat = true;

        return cheat;
    }

    /**
     *
     * @param row riga della matrice aggiornata
     * @param col colonna della matrice aggiornata
     * @return indice del boottone dell'array griglia corrispondente
     */
    public static int updateButtonAi(int row,int col)
    {
        int ret= 0;

        switch (row)
        {
            case 0:
                ret = col;
                break;
            case 1:
                ret = 2 + row + col;
            case 2:
                ret = 3 * row + col;
        }


        buttonListenerAi.row = row;
        buttonListenerAi.col = col;

        return ret;
    }

    /**
     * Funzione per controllare se il gioco è finito
     * @return true se è finito, false altrimenti
     */
    public static boolean isFinished()
    {
        boolean ok = true;

        for(int i=0;i<3 && ok;i++)
        {
            for(int j=0;j<3 && ok;j++)
            {
                if(mat[i][j] == -10)
                    ok = false;
            }
        }

        return ok;
    }
}

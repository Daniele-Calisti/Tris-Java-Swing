package Listener;

import AI.minimax;
import com.company.Main;
import gameEngine.game;
import utility.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Listener.buttonListener.mat;

public class buttonListenerAi implements ActionListener {
    private JButton button;
    public static JButton prv = new JButton();
    public static String descPrv = "null";
    private JTextField info;
    private JFrame frame;
    public static int row = -1,col = -1;

    public buttonListenerAi(JButton button, JTextField info, JFrame frame) {
        this.button = button;
        this.info = info;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String desc = null;

        char winner= 'a';

        String appo = e.getActionCommand();

        button.setIcon(game.getPlayer());

        desc = ((ImageIcon)button.getIcon()).getDescription();   //So quale icona ho settato

        game.updateMat(appo,desc);

        game.turno = game.updateTurno(game.turno);

        if(game.turno == 2)
            game.turno = 0;

        info.setText("Turno di: "+game.getPlayerTurno());

        button.setEnabled(false);

        boolean diagonali = false;  //Mi serve per sapere se sono andato nella if o nella else
        boolean vincitore = false;

        //Una voolta che l'utente fa la sua mossa tocca alla cpu
        if(!game.isFinished())
        {
            minimax.Mossa bestMove = minimax.findBestMove();
            mat[bestMove.row][bestMove.col] = 1;

            if(bestMove.col != -1 && bestMove.row != -1)
            {
                Main.griglia[game.updateButtonAi(bestMove.row,bestMove.col)].setIcon(game.getPlayer());
                Main.griglia[game.updateButtonAi(bestMove.row,bestMove.col)].setEnabled(false);
            }

            row = bestMove.row;
            col = bestMove.col;
            game.turno = 0;

            utlity.stampaMat();

            //Caselle dove mi interessa controllare soolo la riga e la colonna
            if( (row == 0 && col == 1) || (row == 1 && col == 2) || (row == 2 && col == 1) || (row == 1 && col == 0) )
            {
                int score;

                //Controllo la riga
                score = game.checkMat(row,0,mat,0);

                winner = game.checkWin(score);

                //Se la riga non risulta vincente controllo la colonna
                if(winner == 'a')
                {
                    score = game.checkMat(0,col,mat,1); //Controllo la colonna
                    winner = game.checkWin(score);
                }
                else if(!vincitore)
                {
                    vincitore = true;
                    game.coloraVincitore(row,0,0);
                }

            }else   //Caselle dove mi interessa controllare anche le diagonali
            {
                int score;

                //Controllo la riga
                score = game.checkMat(row, 0, mat, 0); //Riga

                winner = game.checkWin(score);

                //Se la riga non risulta vincente controllo la colonna
                if (winner == 'a') {
                    score = game.checkMat(0, col, mat, 1); //Controllo la colonna
                    winner = game.checkWin(score);
                } else if(!vincitore){
                    vincitore = true;
                    game.coloraVincitore(row, 0, 0);
                }


                //Se la colonna non risulta vincente provo la diagonale in alto a sx
                if (winner == 'a') {
                    score = game.checkMat(0, 0, mat, 2); //Controllo la diagonale partendo da sinistra
                    winner = game.checkWin(score);
                } else if(!vincitore){
                    vincitore = true;
                    game.coloraVincitore(0, col, 1);
                }


                if (winner == 'a') {
                    score = game.checkMat(0, 2, mat, 3); //Controllo la diagonale partendo da destra
                    winner = game.checkWin(score);
                } else if(!vincitore){
                    vincitore = true;
                    game.coloraVincitore(0, 0, 2);
                }

                diagonali = true;
            }

            if(winner != 'a')
            {
                if(!vincitore)
                {
                    if(!diagonali)
                        game.coloraVincitore(0,col,1);
                    else
                        game.coloraVincitore(0,2,3);
                }

                game.showWinner(winner, frame);

                game.altraPartita(frame);

            }

        }else
        {
            if(game.isPari())
            {
                game.coloraVincitore(-2,-2,-2);
                game.showWinner(winner,frame);
                game.altraPartita(frame);
            }
        }

    }
}

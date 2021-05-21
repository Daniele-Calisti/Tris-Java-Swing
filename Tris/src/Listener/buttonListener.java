package Listener;

import javax.swing.*;
import gameEngine.game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utility.*;

public class buttonListener implements ActionListener {

    private JButton button;
    public static JButton prv = new JButton();
    public static String descPrv = "null";
    private JTextField info;
    private JFrame frame;
    public static int mat[][] = {{-10,-10,-10},{-10,-10,-10},{-10,-10,-10}};
    public static int row = -1,col = -1;

    public buttonListener(JButton button, JTextField info, JFrame frame) {
        this.button = button;
        this.info = info;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Vado a bloccare il bottone che ho cliccato precedentemente
        if(prv != button && !(descPrv.equals("null")))
            prv.setEnabled(false);

        String appo = e.getActionCommand();

        char winner;

        String desc = ((ImageIcon)button.getIcon()).getDescription();   //So quale icona ho settato

        /*
        * Ho cliccato il button 3 volte, sicuramente il prossimo turno è della x
        * */
        if(desc.equals("null"))
        {
            if(game.turno == 2)
                game.turno = 0;
        }

        /*
            Se non ho mai cliccato quel bottone
         */
        if(desc.equals("vuoto"))
        {
            if(game.turno == 2)
                game.turno = 0;

        }

        button.setIcon(game.getPlayer());

        desc = ((ImageIcon)button.getIcon()).getDescription();   //So quale icona ho settato

        game.updateMat(appo,desc);

        game.turno = game.updateTurno(game.turno);

        info.setText("Turno di: "+game.getPlayerTurno());

        //utlity.stampaMat();

        boolean diagonali = false;  //Mi serve per sapere se sono andato nella if o nella else
        boolean vincitore = false;
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
            score = game.checkMat(row,0,mat,0); //Riga

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



            //Se la colonna non risulta vincente provo la diagonale in alto a sx
            if(winner == 'a')
            {
                score = game.checkMat(0,0,mat,2); //Controllo la diagonale partendo da sinistra
                winner = game.checkWin(score);
            }
            else if(!vincitore)
            {
                vincitore = true;
                game.coloraVincitore(0,col,1);
            }



            if(winner == 'a')
            {
                score = game.checkMat(0,2,mat,3); //Controllo la diagonale partendo da destra
                winner = game.checkWin(score);
            }
            else if(!vincitore)
            {
                vincitore = true;
                game.coloraVincitore(0,0,2);
            }

            diagonali = true;
        }

        if(winner != 'a')
        {
            if(game.isCheating())
                game.showWinner('b',frame);
            else
            {
                if(!vincitore)
                {
                    if(!diagonali)
                        game.coloraVincitore(0,col,1);
                    else
                        game.coloraVincitore(0,2,3);
                }

                game.showWinner(winner, frame);
            }

            game.altraPartita(frame);

        }else
        {
            if(game.isPari())
            {
                game.coloraVincitore(-2,-2,-2);
                game.showWinner(winner,frame);
                game.altraPartita(frame);
            }
        }

        prv = button;
        descPrv = desc;

    }
}

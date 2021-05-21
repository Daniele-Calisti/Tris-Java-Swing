package gui;

import Listener.buttonListener;
import Listener.buttonListenerAi;
import utility.Dimensioni;
import utility.Punto;

import javax.swing.*;
import java.awt.*;
import gameEngine.*;
import java.net.URL;

public class gui {

    public gui() {}

    /**
     *
     * @param title -> titolo della finestra
     * @param d --> dimensioni della finestra
     * @param p --> posizione della finestra
     * @return f --> finestra finale
     */
    public JFrame creaFrame(String title, Dimensioni d, Punto p)
    {
        JFrame f = new JFrame();
        f.setSize(d.getLarghezza(),d.getAltezza());
        f.setLocation(p.getX(),p.getY());
        f.setVisible(true);

        return f;
    }

    /**
     *
     * @return p --> panel finale
     */
    public JPanel creaPanel(LayoutManager l)
    {
        JPanel p = new JPanel();
        p.setLayout(l);

        return p;
    }

    /**
     *
     * @return griglia --> array[9] di JButton
     */
    public static JButton[] getGriglia()
    {
        JButton[] griglia = new JButton[9];

        for(int i=0;i<9;i++)
        {
            griglia[i] = new JButton();
            griglia[i].setActionCommand(String.valueOf(i+1));
            griglia[i].setIcon(new ImageIcon("../invisible.png","vuoto"));
        }

        return griglia;
    }

    /**
     *
     * @param griglia --> aray di JButton
     * @param p --> pannello in cui inserire i bottoni
     * @return p --> pannello finale
     */
    public static JPanel creaGriglia(JButton[] griglia, JPanel p)
    {
        for (JButton b: griglia)
        {
            p.add(b);
        }

        return p;
    }

    /**
     *
     * @param griglia -> array di button
     * @return button con listener
     */
    public JButton[] setListener(JButton[] griglia, JTextField info,JFrame f, int listener)
    {
        if(listener == 0)
        {
            for(JButton b: griglia)
            {
                b.addActionListener(new buttonListener(b,info,f));
            }
        }else
        {
            for(JButton b: griglia)
            {
                b.addActionListener(new buttonListenerAi(b,info,f));
            }
        }


        return griglia;
    }

    public JTextField creaTextField(JFrame f)
    {
        JTextField info = new JTextField("Turno di: " + game.getPlayerTurno());
        info.setEditable(false);
        info.setSize(f.getWidth(),20);

        return info;
    }
}

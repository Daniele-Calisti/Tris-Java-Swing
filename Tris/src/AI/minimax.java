package AI;
import Listener.*;

import static Listener.buttonListener.*;

public class minimax {

    /**
     * Mi serve perchè ritornerò un oggetto di classe mossa
     * contenente la riga e la colonna della matrice
     * dove il pc posizionerà la sua pedina
     */
    public static class Mossa
    {
        public int row;
        public int col;
    };


    /**
     * Controllo se ci sono ancora mosse disponibili
     * */
    public static boolean mossaRimasta()
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (mat[i][j] == -10)
                    return true;
        return false;
    }

    /**
     *
     * @return
     */
    public static int calcolaMossa()
    {
        /**
        *   Calcolo la mossa del pc e del giocatore per le righe
         *   Se il giocatore piazza la pedina e fa tris il punteggio è -10
         *   altrimenti se tris lo fa il pc, il punteggio +10
         */
        for (int row = 0; row < 3; row++)
        {
            if (mat[row][0] == mat[row][1] &&
                    mat[row][1] == mat[row][2])
            {
                if (mat[row][0] == 1)
                    return +10;
                else if (mat[row][0] == 0)
                    return -10;
            }
        }

        /**
         *   Calcolo la mossa del pc e del giocatore per le colonne
         *   Se il giocatore piazza la pedina e fa tris il punteggio è -10
         *   altrimenti se tris lo fa il pc, il punteggio +10
         */
        for (int col = 0; col < 3; col++)
        {
            if (mat[0][col] == mat[1][col] &&
                    mat[1][col] == mat[2][col])
            {
                if (mat[0][col] == 1)
                    return +10;

                else if (mat[0][col] == 0)
                    return -10;
            }
        }

        /**
         *   Calcolo la mossa del pc e del giocatore per la diagonale in alto a sinistra
         *   Se il giocatore piazza la pedina e fa tris il punteggio è -10
         *   altrimenti se tris lo fa il pc, il punteggio +10
         */
        if (mat[0][0] == mat[1][1] && mat[1][1] == mat[2][2])
        {
            if (mat[0][0] == 1)
                return +10;
            else if (mat[0][0] == 0)
                return -10;
        }

        /**
         *   Calcolo la mossa del pc e del giocatore per la diagonale in alto a destra
         *   Se il giocatore piazza la pedina e fa tris il punteggio è -10
         *   altrimenti se tris lo fa il pc, il punteggio +10
         */
        if (mat[0][2] == mat[1][1] && mat[1][1] == mat[2][0])
        {
            if (mat[0][2] == 1)
                return +10;
            else if (mat[0][2] == 0)
                return -10;
        }

        return 0;
    }

    /**
     *
     * @param depth --> profodità dell'albero delle decisioni
     * @param isMax --> vedere se sta giocando il pc o il gioocatore
     * @return
     */
    public static int minimax(int depth, Boolean isMax)
    {
        int score = calcolaMossa();

        // Se il maximizing player ha vinto ritorno il suo punteggio
        if (score == 10)
            return score;

        // Se il minimizing player ha vinto ritorno il suo punteggio
        if (score == -10)
            return score;

        //Se non ci sono più mosse disponibili, allora è un pareggio
        if (!mossaRimasta())
            return 0;

        // Se è il maximising player (il pc)
        if (isMax)
        {
            int best = -1000;

            //Mi scorro tutta la matrice
            //Il pc piazzerà la pedina in ogni cella libera
            //E per ogni cella calcolerà tutte le possibili alternative
            //Da cui poi prenderà la scelta migliore
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    //Se la cella è vuota
                    if (mat[i][j]== -10)
                    {
                        //Il pc fa la sua mossa
                        mat[i][j] = 1;

                        //Chiama la funzione minimax ricorsivamente
                        //Questa volta la variabile isMax sarà false
                        best = Math.max(best, minimax(depth + 1, !isMax));

                        //Tolgo la pedine del pc
                        mat[i][j] = -10;
                    }
                }
            }
            return best;
        }

        //Minimizing player, quindi il giocatore
        else
        {
            int best = 1000;

            //Mi scorro tutta la matrice
            //Il pc piazzerà la pedina del giocatore in ogni cella libera
            //E per ogni cella calcolerà tutte le possibili alternative
            //Da cui poi prenderà la scelta migliore
            //Quindi in base a quando è stata chiamata la funzione minimax con valore isMax false
            //Il pc calcola tutte le possibilità per quella cella e coosì via in maniera ricorsiva
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    //Se la cella è vuota
                    if (mat[i][j] == -10)
                    {
                        //Viene piazzata la pedina del giocatore
                        mat[i][j] = 0;

                        //Chiama la funzione minimax ricorsivamente
                        //Questa volta la variabile isMax sarà true
                        best = Math.min(best, minimax(depth + 1, !isMax));

                        //Tolgo la pedine del giocatore
                        mat[i][j] = -10;
                    }
                }
            }
            return best;
        }
    }

    public static Mossa findBestMove()
    {
        int bestVal = -1000;
        Mossa bestMove = new Mossa();
        bestMove.row = -1;
        bestMove.col = -1;

        // Scorro tutta la matrice, chiamo la funzione minimax
        // per tutte le celle vuote.
        //Ritorno la riga e la colonna della mossa ottimale
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (mat[i][j] == -10)
                {
                    mat[i][j] = 1;

                    //Inizio a chiamare la funzione minimax
                    int moveVal = minimax(0, false);

                    mat[i][j] = -10;

                    // Vado a calcolarmi la mossa migliore che il pc possa fare
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }


}

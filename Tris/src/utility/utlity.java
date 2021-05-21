package utility;

import Listener.*;

public class utlity
{
    public static void stampaMat()
    {
        int[][] m = buttonListener.mat;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                System.out.print(m[i][j]+" ");
            }
            System.out.println("");
        }
    }
}

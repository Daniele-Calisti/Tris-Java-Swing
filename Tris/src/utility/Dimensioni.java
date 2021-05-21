package utility;

public class Dimensioni {

    private int larghezza;
    private int altezza;

    public Dimensioni(int larghezza,int altezza)
    {
        this.larghezza = larghezza;
        this.altezza = altezza;
    }

    public int getLarghezza()
    {
        return this.larghezza;
    }

    public int getAltezza()
    {
        return this.altezza;
    }

    public void setLarghezza(int larghezza)
    {
        this.larghezza = larghezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }


}

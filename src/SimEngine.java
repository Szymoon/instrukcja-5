public class SimEngine extends Vector2D{

    private double m, k, c, l0;
    private Vector2D rm, rv, rutw, g;

    //konstruktory
    SimEngine (double m, double k, double c, double l0, Vector2D rm, Vector2D rv, Vector2D rutw, Vector2D g){

        this.m=m;         //masa
        this.k=k;         //współczynnik sprężystości
        this.c=c;         //współczynnik tłumienia
        this.l0=l0;       //długość swobodnna sprężyny

        this.rm=rm;       //wektor położenia masy
        this.rv=rv;       //wektor prędkości
        this.rutw=rutw;   //wektor położenia utwierdzenia
        this.g=g;         //wektor przyspieszenia ziemskego
    }


    public double getM () {//akcesor masy
        return m;
    }
    public void setM (double m){//modyfikator masy
        this.m=m;
    }
    public double getK () {
        return k;
    }
    public void setK (double k) {
        this.k=k;
    }
    public double getC () {
        return c;
    }
    public void setC (double c) {
        this.c=c;
    }
    public double getL0 () {
        return l0;
    }
    public void setL0 (double l0) {
        this.l0=l0;
    }


    public Vector2D getRm () {//akcesor wektora położenia masy
        return rm;
    }
    public void setRm (Vector2D newpozM) {//modyfikator wektora położenia masy
        rm=newpozM;
    }
    public Vector2D getRv () {
        return rv;
    }
    public void setRv (Vector2D newpredM) {
        rv=newpredM;
    }
    public Vector2D getRutw () {
        return rutw;
    }
    public void setRutw (Vector2D newpozUtw) {
        rutw=newpozUtw;
    }
    public Vector2D getG () {
        return g;
    }
    public void setG (Vector2D newG) {
        g=newG;
    }

    public void sim (double t) {

        Vector2D L0 = getRm().Różnica(getRutw()).Normalizuj().Iloczyn(l0); //wektor dlugości swobodnej sprężyny
        Vector2D r = getRm().Różnica(getRutw()).Różnica(L0); //wektor przemieszczenia

        Vector2D Fw = r.Iloczyn(-k).Suma(getRv().Iloczyn(-c)).Suma(getG().Iloczyn(m));//siła wypadkowa działająca na mase

        Vector2D a = Fw.Iloczyn(1/getM());//przyspieszenie masy
        setRv(getRv().Suma(a.Iloczyn(t)));//nowy wektor predkosci w chwili t
        setRm(getRm().Suma(getRv().Iloczyn(t).Suma(a.Iloczyn(t*t/2))));//nowy wektor położenia masy w chwili t
    }
    public void reset () { //metoda resetująca prędkość masy
        Vector2D reset = new Vector2D();
        setRv(reset);
    }
}


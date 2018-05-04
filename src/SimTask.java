import java.util.TimerTask;

public class SimTask extends TimerTask {

    //pole przechowywujące obiekt klasy SpringApplet
    private SpringApplet SA;
    //pole przechowywujące obiekt klasy SimEngine
    private SimEngine SE;
    //pole przechowywujące odstęp czasowy
    private double t;

    public SimTask(SpringApplet SA, SimEngine SE, double t) {
        this.SA=SA;
        this.SE=SE;
        this.t=t;
    }
    public void setT(double t) {
        this.t=t;
    }
    public double getT() {
        return t;
    }

    public void run() {
        SE.sim(t);
        SA.repaint();
    }
}

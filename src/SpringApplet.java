import javax.swing.JApplet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.util.Timer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;

public class SpringApplet extends JApplet implements MouseListener, MouseMotionListener, ActionListener {

    int s=600, w=500; //szerokości i wysokość okna

    private SimEngine SE;//stworzenie pola klasy SimEngine
    private SimTask ST;  //stworzenie pola klasy SimTask
    private Timer T;     //stworzenie pola klasy Timer

    private Image buf;
    private Graphics bufG;

    private boolean MouseDragged; // zmienna od przeciągania myszy
    private TextField Mtxt, Ktxt, Ctxt, Gtxt, L0txt;
    private Label label, Mlab, Klab, Clab, Glab, L0lab;
    private Button resetButton;

    public void init() {

        double m=10, k=5, c=2, l0=100, t=0.01, g=9.81;
        setSize(s,w);

        //inicjalizacja obiektów
        Vector2D PozM = new Vector2D(350, 400);
        Vector2D PozUtw = new Vector2D(300,150);
        Vector2D PredM = new Vector2D();
        Vector2D G = new Vector2D(0, g);

        SE=new SimEngine(m,k,c,l0,PozM,PredM,PozUtw,G);
        ST=new SimTask(this, SE, t);
        T=new Timer();
        //wywołanie metody scheduleAtFixedRate
        T.scheduleAtFixedRate(ST,0,(long)(t*100));

        //obsługa myszki
        MouseDragged=false;
        addMouseListener(this);       //"nasłuchiwacz"  myszy do appletu
        addMouseMotionListener(this); //"nasłuchiwacz" ruchu myszy do appletu

        //wpisywanie wartości
        Mtxt = new TextField(String.valueOf(m));
        Ktxt = new TextField(String.valueOf(k));
        Ctxt = new TextField(String.valueOf(c));
        L0txt = new TextField(String.valueOf(l0));
        Gtxt = new TextField(String.valueOf(g));

        //reset
        resetButton = new Button  ("  Reset  ");
        resetButton.addActionListener(this);

        //etykiety
        label = new Label("Zmienne:");
        Mlab = new Label("m:  ");
        Klab = new Label("k:  ");
        Clab = new Label("c:  ");
        L0lab = new Label("l0: ");
        Glab = new Label("g:  ");

        //dodanie GUI
        add(resetButton);
        add(Mtxt);
        add(Ktxt);
        add(Ctxt);
        add(L0txt);
        add(Gtxt);
        add(label);
        add(Mlab);
        add(Klab);
        add(Clab);
        add(L0lab);
        add(Glab);
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }
    @Override
    public void mouseMoved(MouseEvent event) {
    }
    @Override
    public void mouseExited(MouseEvent event) {
    }
    @Override
    public void mouseClicked(MouseEvent event) {
    }

    //Sterowanie myszą - początek
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == resetButton)
        {
            //zatrzymanie timera i zresetowanie symulacji
            T.cancel();
            SE.reset();

            //sczytanie nowych parametrow symulacji
            double M = Double.parseDouble(Mtxt.getText());
            double K = Double.parseDouble(Ktxt.getText());
            double C = Double.parseDouble(Ctxt.getText());
            double L0 = Double.parseDouble(L0txt.getText());
            double G = Double.parseDouble(Gtxt.getText());

            //ustawienie nowych parametrow symulacji
            SE.setM(M);
            SE.setK(K);
            SE.setC(C);
            SE.setL0(L0);
            SE.setG(new Vector2D(0, G));
            SE.setRm(new Vector2D(300, 250));

            this.repaint();
        }
    }
    @Override
    public void mousePressed(MouseEvent event) {

        //Sprawdzanie czy kursor znajduje się w polu kulki

        double x=SE.getRm().x-event.getX(); // odległość x środka masy od kurosra
        double y=SE.getRm().y-event.getY(); // odległość y środka masy od kursora

        //kulka ma promień 10, więc sprawdzamy, czy spełniona jest nierówność x^2+y^2<=10^2
        if(x*x+y*y<=100) {

            T.cancel();
            SE.reset();
            MouseDragged = true;
        }
        event.consume();
    }
    @Override
    public void mouseReleased(MouseEvent event) {

        if(MouseDragged) {

            ST=new SimTask(this, SE, 0.01);
            T=new Timer();
            T.scheduleAtFixedRate(ST,0,(long)(1));
            MouseDragged=false;
        }
        event.consume();
    }
    @Override
    public void mouseDragged(MouseEvent event) {

        if(MouseDragged) {

            Vector2D m = new Vector2D(event.getX(), event.getY());
            SE.setRm(m);
            this.repaint();
        }
        event.consume();
    }
    //koniec

    public void paint(Graphics g) {

        buf = createImage(s,w);
        bufG = buf.getGraphics();

        paintComponent(bufG);
        g.drawImage(buf,0,0,null);
    }

    public void paintComponent(Graphics g) {

        // czyszczenie ekranu
        g.clearRect(0, 0, s, w);

        //etykiety
        label.setBounds(15, 5, 55, 20);
        Mlab.setBounds(15, 30, 20, 20);
        Klab.setBounds(105, 30, 20, 20);
        Clab.setBounds(105, 55, 20, 20);
        Glab.setBounds(15, 55, 20, 20);
        L0lab.setBounds(15, 80, 20, 20);

        //pola tekstowe i przyisk reset
        Mtxt.setBounds(40, 30, 50, 20);
        Ktxt.setBounds(130, 30, 50, 20);
        Ctxt.setBounds(130, 55, 50, 20);
        Gtxt.setBounds(40, 55, 50, 20);
        L0txt.setBounds(40, 80, 50, 20);
        resetButton.setBounds(15, 110, 60, 20);

        g.setColor(Color.LIGHT_GRAY); //tło
        g.fillRect(0, 0, s, w);

        g.setColor(Color.DARK_GRAY);//sprężyna i utwierdzenie
        g.fillRect((int)SE.getRutw().x-20, (int)SE.getRutw().y-10, 40, 20);
        g.drawLine((int)SE.getRutw().x, (int)SE.getRutw().y, (int)SE.getRm().x, (int)SE.getRm().y);
        g.drawLine((int)SE.getRutw().x, (int)SE.getRutw().y, (int)SE.getRm().x, (int)SE.getRm().y);

        g.setColor(Color.RED);//masa-kulka
        g.fillOval((int)SE.getRm().x-10, (int)SE.getRm().y-10, 20, 20);
    }
}


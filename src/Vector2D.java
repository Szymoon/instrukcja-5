import static java.lang.Math.*; // biblioteka umożliwiajaca pierwiastkowanie

public class Vector2D {

    public double x,y; // prywatne zmienne x,y

    //konstruktor domyślny
    public Vector2D(){
        x = 0;
        y = 0;
    }
    //kontruktor z parametrem
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    //akcesor parametru x
    public double getX(){
        return x;
    }
    //akcesor parametru y
    public double getY(){
        return y;
    }
    //suma wektorów
    Vector2D Suma (Vector2D a){
        Vector2D suma = new Vector2D(x + a.x, y + a.y); // tworzenie nowego obiektu
        return suma; // zwracanie wektora
    }
    //różnica wektorow
    Vector2D Różnica (Vector2D a){
        Vector2D różnica = new Vector2D(x - a.x, y - a.y);
        return różnica;
    }
    //mnożenie przez stałą
    Vector2D Iloczyn (double a){
        Vector2D iloczyn = new Vector2D(x*a, y*a);
        return iloczyn;
    }
    //długość wektora
    double Długość () {
        double długość = sqrt(pow(x,2)+pow(y,2));
        return długość;
    }
    // metoda normalizuj
    Vector2D Normalizuj (){
        Vector2D normalizacja = new Vector2D();

        if(x == 0 && y == 0) { // kontrola wartosci
            System.out.println("Wartości x i y muszą być większe od 0!");
            return normalizacja;
        }else { // główny kod metody normalizuj
            normalizacja.x = x/Długość();
            normalizacja.y = y/Długość();
            return normalizacja;
        }
    }
}


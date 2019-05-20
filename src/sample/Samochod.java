package sample;


public class Samochod extends Thread {
    private Parking parking;
    String rejestracja;
    int jakDlugoStoje;
    boolean inwalida;
    FreeSpace gdzieJaStoje;

    public Samochod(String rejestracja, int jakDlugoStoje, boolean inwalida, Parking parking) {
        this.rejestracja = rejestracja;
        this.jakDlugoStoje = jakDlugoStoje;
        this.inwalida = inwalida;
        this.parking = parking;

    }

    public FreeSpace getGdzieJaStoje() {
        return gdzieJaStoje;
    }

    public void setGdzieJaStoje(FreeSpace gdzieJaStoje) {
        this.gdzieJaStoje = gdzieJaStoje;
    }

    @Override
    public String toString() {
        return rejestracja + " " + jakDlugoStoje + " " + inwalida;

    }

    @Override
    public void run() {
        try {

            parking.addToQueue(this);
            parking.FindFreeSpace(this);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
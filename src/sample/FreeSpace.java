package sample;


public class FreeSpace {

    private int number;
    private boolean occupied = false;
    private boolean disabled;
    private Parking parking;

    public FreeSpace(Parking parking, int number, boolean disabled) {
        this.parking = parking;
        this.number = number;
        this.disabled = disabled;
        System.out.println(disabled + " " + number);
    }

    public boolean isOccupied() {
        return occupied;
    }


    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public synchronized void entryToFreeSpace(Samochod samochod) {
        try {

            parking.getProgressBar0().setSamochod(samochod);
            parking.getProgressBar0().setInFreeSpace(true);

            isInFreeSpace(samochod);
           /* new Thread(parking.getProgressBar0()).start();

            while (parking.getProgressBar0().isInFreeSpace() == true) {

            }

*/
           Thread.sleep(samochod.jakDlugoStoje);
            leftFreeSpace(samochod);


        } catch (Exception e) {
            System.out.println("Freespace error " + number);
        }
    }

    public void isInFreeSpace(Samochod samochod) {
        setOccupied(true);
        System.out.println(">>" + number + invalida(disabled) + " " + samochod.toString());

    }

    public void leftFreeSpace(Samochod samochod) {
        System.out.println("<" + number + invalida(disabled) + " " + samochod.toString());
        setOccupied(false);
    }

    public String invalida(boolean disabled) {
        if (disabled)
            return "*";
        else return "";
    }
}



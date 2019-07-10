package Parking;


import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public class FreeSpace extends Task {

    private int number;
    private boolean occupied = false;
    private boolean disabled;
    private Samochod samochod = null;
    private Parking parking;
    private SimpleStringProperty nazwa =  new SimpleStringProperty(this, "nazwa");;
    private SimpleStringProperty rejestracja = new SimpleStringProperty(this, "rejestracja");

    public FreeSpace(Parking parking, int number, boolean disabled) {
        this.number = number;
        this.disabled = disabled;
        this.parking = parking;
        parking.setParkingSpace(parking.getParkingSpace() + 1);
        nazwa.set("Miejsce nr: ");
        if(!disabled) {
            parking.setNormalSpace(parking.getNormalSpace() + 1);
        }
    }


    public SimpleStringProperty rejestracjaProperty() {
        return rejestracja;
    }

    public void setRejestracja(String rejestracja) {
        this.rejestracja.set(rejestracja);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return nazwa;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setSamochod(Samochod samochod) {
        this.samochod = samochod;
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

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public String toString() {
        return "FreeSpace{" +
                "number=" + number +
                ", disabled=" + disabled +
                '}';
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    @Override
    protected Object call() throws Exception {
        while (true) {
            updateProgress(0, 1000);
            synchronized (parking) {
                while (!occupied) {
                    parking.wait();
                }
            }

            for (int i = 0; i < 1000; i++) {
                updateProgress(i, 1000);
                Thread.sleep(samochod.jakDlugoStoje / 1000);

            }

            synchronized (parking) {

                parking.subAvaiableSpace();
                if(!samochod.inwalida || !isDisabled()) {
                    parking.setAvailableNormalSpace(parking.getAvailableNormalSpace() - 1);
                }
                leftFreeSpace(samochod);
                parking.notifyAll();

            }
        }
    }


}




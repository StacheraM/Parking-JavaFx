package Parking;


import javafx.beans.property.SimpleStringProperty;

public class Samochod extends Thread {
    private Parking parking;
    String rejestracja;
    int jakDlugoStoje;
    boolean inwalida;
    String mark = "*";
    private SimpleStringProperty rejestracja1 = new SimpleStringProperty(this, "rejestracja1");
    int i = 0;

    public Samochod(String rejestracja, int jakDlugoStoje, boolean inwalida, Parking parking) {
        this.rejestracja = rejestracja;
        if (inwalida) {
            this.rejestracja = mark + rejestracja + mark;
        }


        this.jakDlugoStoje = jakDlugoStoje;
        this.inwalida = inwalida;
        this.parking = parking;

    }

    @Override
    public String toString() {
        return rejestracja + " " + jakDlugoStoje + " " + inwalida;

    }

    public void parkInFreeSpace(int i) {
        parking.addAvaiableSpace();
        if (!this.inwalida || (!parking.getArrayList(i).isDisabled())) {
            parking.setAvailableNormalSpace(parking.getAvailableNormalSpace() + 1);
        }
        parking.showQueue();
        parking.queueRemove();
        parking.carLenghtInQueueProperty().set(parking.queueSize());
        parking.getArrayList(i).setSamochod(this);
        parking.getArrayList(i).isInFreeSpace(this);
        parking.getArrayList(i).setRejestracja(this.rejestracja);


    }

    @Override
    public void run() {
        try {
            parking.addToQueue(this);
            synchronized (parking) {

                boolean exit = false;
                if (inwalida) {
                    while (this != parking.getFirstInQueue() || parking.getAvaiableSpace() == parking.getParkingSpace()) {
                        parking.wait();
                    }

                    for (int i = 0; i < parking.getParkingSpace(); i++) {

                        if (parking.getArrayList(i).isDisabled() && (!parking.getArrayList(i).isOccupied())) {
                            parkInFreeSpace(i);
                            parking.notifyAll();
                            i = parking.getParkingSpace();
                            exit = true;
                        }
                    }

                    while (!exit) {
                        i %= (parking.getParkingSpace());
                        if (!parking.getArrayList(i).isOccupied()) {
                            parkInFreeSpace(i);
                            parking.notifyAll();
                            exit = true;
                        }
                        i++;


                    }


                } else {

                    while (this != parking.getFirstInQueue() || parking.getAvailableNormalSpace() == parking.getNormalSpace()) {
                        parking.wait();
                    }
                    while (!exit) {
                        i %= (parking.getParkingSpace());
                        if (!parking.getArrayList(i).isDisabled() && (!parking.getArrayList(i).isOccupied())) {
                            parkInFreeSpace(i);
                            parking.notifyAll();
                            exit = true;
                        }
                        i++;
                    }
                }
            }
            if (!inwalida) {
                parking.setIncome(parking.getIncome() + parking.parkingFee(jakDlugoStoje));
            }
            parking.setSumOfCars(parking.getSumOfCars() + 1);
        } catch (
                Exception e) {
            System.out.println(e);
        }
    }
}
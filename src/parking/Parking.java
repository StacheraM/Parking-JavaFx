package Parking;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Queue;

import javafx.beans.property.SimpleStringProperty;

public class Parking {

    private int normalSpace;
    private int parkingSpace;
    private int availableSpace;
    private int availableNormalSpace;
    public ArrayList<FreeSpace> spaceList;
    private Queue<Samochod> queue;
    private SimpleIntegerProperty carLenghtInQueue = new SimpleIntegerProperty(this, "carLenghtInQueue");
    private SimpleStringProperty rejestracja = new SimpleStringProperty(this, "rejestracja");
    private  SimpleIntegerProperty income  =  new SimpleIntegerProperty(this,"income");
    private SimpleIntegerProperty sumOfCars = new SimpleIntegerProperty(this,"sumOfCars");
    public int getAvaiableSpace() {
        return availableSpace;
    }

    public int getAvailableNormalSpace() {
        return availableNormalSpace;
    }

    public void setAvailableNormalSpace(int availableNormalSpace) {
        this.availableNormalSpace = availableNormalSpace;
    }

    public void addAvaiableSpace() {
        this.availableSpace += 1;
    }

    public void subAvaiableSpace() {
        this.availableSpace -= 1;
    }

    public int getSumOfCars() {
        return sumOfCars.get();
    }

    public SimpleIntegerProperty sumOfCarsProperty() {
        return sumOfCars;
    }

    public void setSumOfCars(int sumOfCars) {
        this.sumOfCars.set(sumOfCars);
    }

    public int getIncome() {
        return income.get();
    }
    //5 000 30 000
    public int parkingFee(int stayLenght) {
        int parkingFee = 0;

        if (stayLenght <= 3000) {
            parkingFee = 6; //half hour
        }
        if (stayLenght >= 6000)
        {
            parkingFee+=8; // one hour
        }
        if (stayLenght >=12000){
            parkingFee+=10; // two hour
        }
        while (stayLenght >= 18000){
                stayLenght-=6000; //three ore more
                parkingFee+=12;
        }
            return parkingFee;
    }


    public synchronized SimpleIntegerProperty incomeProperty() {
        return income;
    }

    public synchronized void setIncome(int income) {
        this.income.set(income);
    }

    public int getNormalSpace() {
        return normalSpace;
    }

    public void setNormalSpace(int normalSpace) {
        this.normalSpace = normalSpace;
    }

    public SimpleStringProperty rejestracjaProperty() {
        return rejestracja;
    }

    public void setRejestracja(String rejestracja) {
        this.rejestracja.set(rejestracja);
    }

    public int getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(int parkingSpace) {
        this.parkingSpace = parkingSpace;
    }


    public Parking(Queue queue, ArrayList spaceList) {

        this.queue = queue;
        this.spaceList = spaceList;
        this.availableSpace = 0;
        this.parkingSpace = 0;
        this.normalSpace = 0;
        this.availableNormalSpace= 0;
        this.income.set(0);
    }

    public int queueSize() {
        return queue.size();
    }

    public void queueRemove() {
        queue.remove();
        if (!queue.isEmpty()) {
            setRejestracja(queue.peek().rejestracja);
        }

    }


    public SimpleIntegerProperty carLenghtInQueueProperty() {
        return carLenghtInQueue;
    }



    public synchronized FreeSpace getArrayList(int i) {
        return spaceList.get(i);
    }

    public void addToQueue(Samochod samochod) {

        queue.add(samochod);
        setRejestracja(queue.peek().rejestracja);

        carLenghtInQueue.set(queue.size());
    }


    public void showQueue() {
        System.out.println(queue);
    }


    public Samochod getFirstInQueue() {
        return queue.peek();
    }
}








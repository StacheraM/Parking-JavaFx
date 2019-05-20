package sample;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Queue;


public class Parking {
    private SimpleIntegerProperty normalParkingSpace = new SimpleIntegerProperty(this,"normalParkingSpace");
    private SimpleIntegerProperty disabledParkingSpace = new SimpleIntegerProperty(this,"disabledParkingSpace");
    private int parkingSpace;
    private ArrayList<FreeSpace> spaceList = new ArrayList<>();
    private Queue<Object> queue;
    private  SimpleIntegerProperty carLenghtInQueue =new SimpleIntegerProperty(this,"carLenghtInQueue");
    private sample.ProgressBar progressBar0;

    public Parking(int normalParkingSpace, int disabledParkingSpace, Queue queue,sample.ProgressBar progressBar0) {
        this.progressBar0 = progressBar0;
        this.normalParkingSpace.set(normalParkingSpace) ;
        this.disabledParkingSpace.set(disabledParkingSpace);
        parkingSpace = normalParkingSpace + disabledParkingSpace;
        this.queue = queue;
        for (int i = 1; i < normalParkingSpace + 1; i++) {
            FreeSpace freeSpace = new FreeSpace(this, i, false);
            spaceList.add(freeSpace);

        }
        for (int i = normalParkingSpace + 1; i < parkingSpace + 1; i++) {
            FreeSpace freeSpace = new FreeSpace(this, i, true);
            spaceList.add(freeSpace);
        }

    }

    public ProgressBar getProgressBar0() {
        return progressBar0;
    }

    public void addNormalParkingSpace(){
        FreeSpace freeSpace = new FreeSpace(this, parkingSpace, false);
        spaceList.add(freeSpace);
        setNormalParkingSpace(parkingSpace);
    }

    public SimpleIntegerProperty carLenghtInQueueProperty() {
        return carLenghtInQueue;
    }


    public SimpleIntegerProperty normalParkingSpaceProperty() {
        return normalParkingSpace;
    }

    public void setNormalParkingSpace(int normalParkingSpace) {
        this.normalParkingSpace.set(normalParkingSpace);
    }



    public synchronized FreeSpace getArrayList(int i) {
        try {

        } catch (Exception e) {
            System.out.println("getArrayList  Error");
        }
        return spaceList.get(i);
    }

    public  void addToQueue(Samochod samochod) {
        queue.add(samochod);
        carLenghtInQueue.set(queue.size());
    }

    public void showQueue() {
        System.out.println(queue);
    }
    public FreeSpace getFirstSpaceList(int which){
        return spaceList.get(which);

    }

    public void FindFreeSpace(Samochod samochod) throws InterruptedException {

        FreeSpace temporary = null;
        int freeSpace;
        boolean exit = false;
        synchronized (this) {
            while (samochod != queue.peek()) {
                wait();
            }
            if (samochod.inwalida) {
                freeSpace = parkingSpace;
                for (int i = normalParkingSpace.get(); i < parkingSpace; i++) {
                    temporary = spaceList.get(i);
                    if (!temporary.isOccupied()) {
                        showQueue();
                        queue.remove();
                        carLenghtInQueue.set(queue.size());

                        notifyAll();
                        i = parkingSpace;
                        exit = true;
                    }
                }
            } else freeSpace = normalParkingSpace.get();
            if (!exit) {
                for (int i = 0; i < freeSpace; i++) {
                    temporary = getArrayList(i);
                    i %= (freeSpace - 1);
                    if (!temporary.isOccupied()) {
                        showQueue();
                        queue.remove();
                        carLenghtInQueue.set(queue.size());
                        notifyAll();
                        i = freeSpace;
                    }
                }
            }
        }
        temporary.entryToFreeSpace(samochod);
    }



}








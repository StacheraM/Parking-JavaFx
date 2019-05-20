package sample;

import javafx.concurrent.Task;

public class ProgressBar extends Task {
    private Samochod samochod;
    private boolean isInFreeSpace = true;

    ProgressBar(){

    }

    public void setSamochod(Samochod samochod) {
        this.samochod = samochod;
    }

    public void setInFreeSpace(boolean inFreeSpace) {
        isInFreeSpace = inFreeSpace;
    }

    public boolean isInFreeSpace() {
        return isInFreeSpace;
    }

    public Object call() throws Exception{

        for(int i = 0 ; i < samochod.jakDlugoStoje/100;i++){
            updateProgress(i, samochod.jakDlugoStoje/100);
            Thread.sleep(100);
        }
        isInFreeSpace = false;
        updateProgress(0,1);

    return null;
    }


}

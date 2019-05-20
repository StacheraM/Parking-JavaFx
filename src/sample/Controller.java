package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.application.Platform;
import java.util.LinkedList;
import java.util.Queue;

public class Controller {
    sample.ProgressBar progressBar0 = new sample.ProgressBar();
    Queue<Object> queue = new LinkedList<>();
    Parking parking = new Parking(2, 0, queue, progressBar0);
    RandomValue randomValue = new RandomValue();


    @FXML
    private Text carInQueue = new Text();

    @FXML
    private Text normalParkingSpace = new Text();
    @FXML
    private ProgressBar progressBarN1 = new ProgressBar(0);

    @FXML
    public void initialize() {
        progressBarN1.progressProperty().bind(progressBar0.progressProperty());
        parking.setNormalParkingSpace(2);
        parking.normalParkingSpaceProperty().addListener((obs,oldStatus,newStatus)->Platform.runLater(()->normalParkingSpace.setText(newStatus.toString())));
        parking.carLenghtInQueueProperty().addListener((obs,oldStatus,newStatus)->Platform.runLater(()->carInQueue.setText(newStatus.toString())));

    }

    @FXML
    void addToQueueButton(ActionEvent event)  throws Exception {
        Samochod samochod = new Samochod(randomValue.generateRandomWords(), randomValue.generateTimeFreeSpace(), randomValue.generateInvalid(), parking);
        Thread.sleep(100);
        samochod.start();
    }
    @FXML
    void addNormalParkingSpace(ActionEvent event) {
        parking.addNormalParkingSpace();
    }

}

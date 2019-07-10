package Parking;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainController {

    Queue<Object> queue = new LinkedList<>();
    ArrayList<FreeSpace> spaceList = new ArrayList<>();
    Parking parking = new Parking(queue, spaceList);
    RandomValue randomValue = new RandomValue();
    int line = 0;
    int normalSpace = 1;
    int disabledSpace = 1;

    @FXML
    private GridPane gridPanel;

    @FXML
    private Text carInQueue = new Text();

    @FXML
    private Text queueNumber = new Text();

    @FXML
    private Text income = new Text();

    @FXML
    private Text sumOfCars = new Text();

    @FXML
    public void initialize() throws Exception {

        while (normalSpace <= 3) {
            FreeSpace freeSpace = new FreeSpace(parking, normalSpace, false);
            spaceList.add(freeSpace);
            createNewGridElement(0, line, freeSpace);
            freeSpace.setNazwa(freeSpace.getNazwa() + normalSpace);
            line++;
            normalSpace++;
        }

        while (disabledSpace <= 1) {
            FreeSpace freeSpace = new FreeSpace(parking, disabledSpace, true);
            spaceList.add(freeSpace);
            createNewGridElement(0, line, freeSpace);
            freeSpace.setNazwa("*" + freeSpace.getNazwa() + disabledSpace + "*");
            line++;
            disabledSpace++;
        }
        parking.rejestracjaProperty().addListener((obs, oldStatus, newStatus) -> Platform.runLater(() -> queueNumber.setText(newStatus)));
        parking.carLenghtInQueueProperty().addListener((obs, oldStatus, newStatus) -> Platform.runLater(() -> carInQueue.setText(newStatus.toString())));
        parking.incomeProperty().addListener((obs, oldStatus, newStatus) -> Platform.runLater(() -> income.setText(newStatus.toString())));
        parking.sumOfCarsProperty().addListener((obs, oldStatus, newStatus) -> Platform.runLater(() -> sumOfCars.setText(newStatus.toString())));
    }

    @FXML
    void addNormalParkingSpace(ActionEvent event) throws Exception {
        FreeSpace freeSpace = new FreeSpace(parking, normalSpace, false);
        spaceList.add(freeSpace);
        createNewGridElement(0, line, freeSpace);
        line++;
        freeSpace.setNazwa(freeSpace.getNazwa() + normalSpace);
        normalSpace++;
        synchronized (parking) {
            parking.notifyAll();
        }

    }

    @FXML
    void addDisableParkingSpace(ActionEvent event) throws Exception {
        FreeSpace freeSpace = new FreeSpace(parking, disabledSpace, true);
        spaceList.add(freeSpace);
        createNewGridElement(0, line, freeSpace);
        line++;
        disabledSpace++;
        freeSpace.setNazwa("*" + freeSpace.getNazwa() + disabledSpace + "*");
        synchronized (parking) {
            parking.notifyAll();
        }

    }

    private void createNewGridElement(int column, int row, FreeSpace freeSpace) throws Exception {

        OneFreeSpace controller = new OneFreeSpace(freeSpace);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OneFreeSpace.fxml"));
        fxmlLoader.setController(controller);
        GridPane newGridPane = fxmlLoader.load();
        gridPanel.add(newGridPane, column, row);

    }

    @FXML
    void addToQueueButton(ActionEvent event) throws Exception {
        Samochod samochod = new Samochod(randomValue.generateRandomWords(), randomValue.generateTimeFreeSpace(), false, parking);
        Thread.sleep(100);
        samochod.start();
    }


    @FXML
    void addToQueueDisabledButton(ActionEvent event) throws Exception {

        Samochod samochod = new Samochod(randomValue.generateRandomWords(), randomValue.generateTimeFreeSpace(), true, parking);
        Thread.sleep(100);
        samochod.start();
    }
}

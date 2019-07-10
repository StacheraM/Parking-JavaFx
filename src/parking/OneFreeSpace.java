package Parking;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.application.Platform;


public class OneFreeSpace {

    private FreeSpace freeSpace;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text name = new Text();

    @FXML
    private Text registration = new Text();

    public OneFreeSpace(FreeSpace freeSpace) {
        this.freeSpace = freeSpace;
    }

    @FXML
    public void initialize() throws Exception {
        freeSpace.rejestracjaProperty().addListener((obs, oldStatus, newStatus) -> Platform.runLater(() -> registration.setText(newStatus)));
        freeSpace.nazwaProperty().addListener((obs, oldStatus, newStatus) -> Platform.runLater(() -> name.setText(newStatus)));
        progressBar.progressProperty().bind(freeSpace.progressProperty());
        new Thread(freeSpace).start();
    }
}

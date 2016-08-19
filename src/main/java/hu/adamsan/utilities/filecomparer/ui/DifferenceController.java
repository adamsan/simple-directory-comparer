package hu.adamsan.utilities.filecomparer.ui;

import java.io.File;

import hu.adamsan.utilities.filecomparer.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

public class DifferenceController {

    @FXML
    TableColumn<MyCellData, String> firstColumn;
    @FXML
    TableColumn<MyCellData, String> secondColumn;
    @FXML
    TableColumn<MyCellData, String> differenceColumn;
    @FXML
    TableView<MyCellData> differenceTable;
    @FXML
    TableView<MyCellData> firstTable;
    @FXML
    TableView<MyCellData> secondTable;
    @FXML
    Button changeFirstBtn;
    @FXML
    Button changeSecond;
    @FXML
    Button compareBtn;

    DifferenceUIApplication mainUI;

    Pair<File> currentPair = new Pair<>();

    @FXML
    VBox historyVBox;

    @FXML TitledPane historyPane;

    @FXML
    public void mouseClick(MouseEvent event) {
        System.out.println("Mouse Clicker on application");
    }

    @FXML
    public void changeFirstClick(ActionEvent event) {
        File defaultDir = mainUI.getPreviousSelection().first;
        File file = selectDir(defaultDir, "Select First Folder");
        if (file != null) {
            currentPair.first = file;
            mainUI.compare(currentPair);
        }
    }

    @FXML
    public void changeSecondClick(ActionEvent event) {
        File defaultDir = mainUI.getPreviousSelection().second;
        File file = selectDir(defaultDir, "Select Second Folder");
        if (file != null) {
            currentPair.second = file;
            mainUI.compare(currentPair);
        }
    }

    private File selectDir(File defaultDir, String title) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(title);
        chooser.setInitialDirectory(defaultDir);
        File file = chooser.showDialog(mainUI.primaryStage);
        return file;
    }

}

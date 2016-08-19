package hu.adamsan.utilities.filecomparer.ui;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import hu.adamsan.utilities.filecomparer.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
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
    Button changeSecondBtn;
    @FXML
    Button compareBtn;

    DifferenceUIApplication mainUI;

    Pair<File> currentPair = new Pair<>();

    @FXML
    VBox historyVBox;

    @FXML
    TitledPane historyPane;

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

    @FXML
    public void copySelected(ActionEvent event) {
        MyCellData selected = differenceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("TODO: Copy selected");
            String selectedFileString = selected.getName().get();
            copy(selectedFileString);
            mainUI.compare(currentPair);
        }

    }

    private void copy(String selectedFileString) {
        File source = new File(currentPair.first, selectedFileString);
        File dest = new File(currentPair.second, selectedFileString);
        try {
            if (source.isDirectory()) {
                FileUtils.copyDirectory(source, dest);
            } else {
                FileUtils.copyFile(source, dest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentPair(Pair<File> currentPair) {
        this.currentPair = currentPair;
    }

}

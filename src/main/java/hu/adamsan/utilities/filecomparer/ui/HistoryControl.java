package hu.adamsan.utilities.filecomparer.ui;

import java.io.File;
import java.io.IOException;

import hu.adamsan.utilities.filecomparer.Pair;
import hu.adamsan.utilities.filecomparer.SearchPersistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HistoryControl extends AnchorPane {

    @FXML
    TextField first;
    @FXML
    TextField second;
    @FXML
    Button deleteBtn;

    private SearchPersistence searchPersistence;
    private Pair<File> selection;
    private DifferenceUIApplication differenceUIApplication;

    public HistoryControl(Pair<File> selection) {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistoryRow.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        first.setText(selection.first.toString());
        second.setText(selection.second.toString());
        this.selection = selection;

    }

    public void setSearchPersistence(SearchPersistence searchPersistence) {
        this.searchPersistence = searchPersistence;
    }

    @FXML
    public void delete(ActionEvent e) {
        if (confirmDelete()) {
            searchPersistence.delete(selection);
            differenceUIApplication.loadHistory();
        }
    }

    private boolean confirmDelete() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure?");
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    public void setDifferenceUIApplication(DifferenceUIApplication differenceUIApplication) {
        this.differenceUIApplication = differenceUIApplication;
    }

    @FXML
    public void load(ActionEvent e) {
        differenceUIApplication.compare(selection);
    }

}

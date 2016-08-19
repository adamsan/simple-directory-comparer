package hu.adamsan.utilities.filecomparer.ui;

import java.io.IOException;

import hu.adamsan.utilities.filecomparer.SearchPersistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HistoryControl extends AnchorPane {

    @FXML
    TextField first;
    @FXML
    TextField second;
    // @FXML
    Button deleteBtn;
    private SearchPersistence searchPersistence;

    public HistoryControl() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoryRow.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setSearchPersistence(SearchPersistence searchPersistence) {
        this.searchPersistence = searchPersistence;
    }

    // @FXML
    public void deleteFromHistory() {
        System.out.println("Delete pressed!");
    }
}

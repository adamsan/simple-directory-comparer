package hu.adamsan.utilities.filecomparer.ui;

import java.io.File;
import java.io.IOException;

import hu.adamsan.utilities.filecomparer.Pair;
import hu.adamsan.utilities.filecomparer.SearchPersistence;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

    public HistoryControl(Pair<File> selection) {
        super();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoryRow.fxml"));
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

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("TODO: delete " + selection);
                
            }
        });

    }

    public void setSearchPersistence(SearchPersistence searchPersistence) {
        this.searchPersistence = searchPersistence;
    }

    @FXML
    public void delete(MouseEvent e) {
        System.out.println("Delete pressed!");
    }
}

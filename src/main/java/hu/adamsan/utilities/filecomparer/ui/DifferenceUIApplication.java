package hu.adamsan.utilities.filecomparer.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import hu.adamsan.utilities.filecomparer.DirectoryComparator;
import hu.adamsan.utilities.filecomparer.Pair;
import hu.adamsan.utilities.filecomparer.SearchPersistence;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DifferenceUIApplication extends Application {
    private BorderPane root;

    // @FXML //does not work...
    VBox differencesVBox;

    private Scene scene;

    private DifferenceController differenceController;

    private FXMLLoader fxmlLoader;

    private ObservableList<MyCellData> data;

    Stage primaryStage;

    private SearchPersistence searchPersistence;

    private DirectoryComparator directoryComparator;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        directoryComparator = new DirectoryComparator();

        searchPersistence = new SearchPersistence();

        try {
            initializeWindow();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pair<File> getPreviousSelection() {
        return searchPersistence.loadPreviousSelection();
    }

    private void populateTable(List<String> firsts, List<String> seconds, List<String> differences) {
        TableView<MyCellData> firstTable = differenceController.firstTable;
        TableView<MyCellData> secondTable = differenceController.secondTable;
        TableView<MyCellData> differenceTable = differenceController.differenceTable;

        renderTable(firsts, firstTable);
        renderTable(seconds, secondTable);
        renderTable(differences, differenceTable);

    }

    private void renderTable(List<String> list, TableView<MyCellData> table) {
        table.getItems().clear();
        data = FXCollections.observableArrayList();
        for (String difference : list) {
            data.add(new MyCellData(difference));
        }

        TableColumn<MyCellData, String> column = (TableColumn<MyCellData, String>) table.getColumns().get(0);
        column.setCellValueFactory(cellData -> {
            return cellData.getValue().getName();
        });
        table.getItems().addAll(data);
    }

    private void initializeWindow() throws IOException {
        String rootFxmlFileName = "Difference.fxml";
        fxmlLoader = createLoaderFor(rootFxmlFileName);
        root = fxmlLoader.load();
        differenceController = fxmlLoader.getController();
        differenceController.mainUI = this;
        double height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3 * 2;
        scene = new Scene(root, 900, height);
        primaryStage.setTitle("Difference Viewer");
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        loadHistory();
    }

    private void loadHistory() {
        List<Pair<File>> selections = searchPersistence.loadpreviousSelections();
        VBox vBox = differenceController.historyVBox;
        for (Pair<File> selection : selections) {
            HistoryControl control = new HistoryControl();
            control.first.setText(selection.first.toString());
            control.second.setText(selection.second.toString());
            control.setSearchPersistence(searchPersistence);
            vBox.getChildren().add(control);
        }
        differenceController.historyPane.setExpanded(false);

    }

    private FXMLLoader createLoaderFor(String rootFxmlFileName) {
        return new FXMLLoader(getClass().getResource(rootFxmlFileName));
    }

    private Callback<CellDataFeatures<String, String>, ObservableValue<String>> differenceCellValueFactory() {
        return new Callback<TableColumn.CellDataFeatures<String, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<String, String> param) {
                ObservableValue<String> ov;
                return new ReadOnlyStringWrapper("Faszom mostmár");
            }
        };
    }

    private Callback<TableColumn<String, String>, TableCell<String, String>> differenceCellFactory() {
        return new Callback<TableColumn<String, String>, TableCell<String, String>>() {
            @Override
            public TableCell<String, String> call(TableColumn<String, String> param) {
                System.out.println(param);
                System.out.println(param.getClass());
                TableCell<String, String> cell = new TableCell<>();
                cell.setText("MIVANMAR");
                cell.setVisible(true);
                return cell;
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void compare(Pair<File> currentPair) {
        if (currentPair.isNull()) {
            return;
        }
        searchPersistence.saveSelection(currentPair);
        directoryComparator.setDirectories(currentPair.first, currentPair.second);
        List<Path> difference = directoryComparator.calculateDifference();
        List<Path> firstPaths = directoryComparator.firstPaths();
        List<Path> secondPaths = directoryComparator.secondPaths();
        populateTable(toStringList(firstPaths), toStringList(secondPaths), toStringList(difference));
    }

    private List<String> toStringList(List<Path> paths) {
        return paths.stream().map(p -> p.toString()).collect(Collectors.toList());
    }

}

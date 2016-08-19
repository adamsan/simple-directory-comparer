package hu.adamsan.utilities.filecomparer.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyCellData {
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();

    public MyCellData(String name) {
        nameProperty.set(name);
    }

    public StringProperty getName() {
        return nameProperty;
    }

    public void setName(String name) {
        nameProperty.set(name);
    }

}

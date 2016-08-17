package hu.adamsan.utilities.filecomparer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SearchPersistence {

    private static final String SERIALIZED_NAME = "lastComparison.ser";

    public Pair<File> loadPreviousSelection() {
        Pair<File> pair = new Pair<>();
        try (ObjectInput oin = new ObjectInputStream(new FileInputStream(SERIALIZED_NAME))) {
            pair = (Pair<File>) oin.readObject();
        } catch (IOException e) {
            System.out.println("File not found.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pair;
    }

    public void saveSelection(Pair<File> directoriesToCompare) {
        if (!directoriesToCompare.isNull()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZED_NAME))) {
                out.writeObject(directoriesToCompare);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

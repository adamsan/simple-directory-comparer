package hu.adamsan.utilities.filecomparer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchPersistence {

    private static final String SERIALIZED_NAME = "lastComparison.ser";

    public List<Pair<File>> loadpreviousSelections() {
        List<Pair<File>> result = new ArrayList<>();

        try (ObjectInput oin = new ObjectInputStream(new FileInputStream(SERIALIZED_NAME))) {
            result = (List<Pair<File>>) oin.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Pair<File> loadPreviousSelection() {
        List<Pair<File>> list = loadpreviousSelections();
        return list.size() > 0 ? list.get(list.size() - 1) : new Pair<>();
    }

    public void saveSelection(Pair<File> directoriesToCompare) {
        if (!directoriesToCompare.isNull()) {
            List<Pair<File>> list = loadpreviousSelections();
            removeDuplicate(directoriesToCompare, list);
            list.add(directoriesToCompare);
            saveSelections(list);
        }
    }

    private void removeDuplicate(Pair<File> directoriesToCompare, List<Pair<File>> list) {
        while (list.contains(directoriesToCompare)) {
            list.remove(directoriesToCompare);
        }
    }

    private void saveSelections(List<Pair<File>> list) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZED_NAME))) {
            out.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(Pair<File> toDelete) {
        List<Pair<File>> selections = loadpreviousSelections();
        selections.remove(toDelete);
        saveSelections(selections);
    }

}

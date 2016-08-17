package hu.adamsan.utilities.filecomparer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFileChooser;

public class Main {

    private static final String SERIALIZED_NAME = "lastComparison.ser";
    private DirectoryComparator directoryComparator;

    public DirectoryComparator getDirectoryComparator() {
        return directoryComparator;
    }

    public void setDirectoryComparator(DirectoryComparator directoryComparator) {
        this.directoryComparator = directoryComparator;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setDirectoryComparator(new DirectoryComparator());
        main.start();
    }

    private void start() {
        Pair<File> previousSelection = loadPreviousSelection();
        Pair<File> dirs = select(previousSelection);
        if (!dirs.isNull()) {
            saveSelection(dirs);
            directoryComparator.setDirectories(dirs.first.toPath(), dirs.second.toPath());
            List<Path> difference = directoryComparator.calculateDifference();
            displayDifference(difference);
        }
    }

    private Pair<File> select(Pair<File> previousSelection) {
        Pair<File> pair = new Pair<>();
        pair.first = selectDirectory(previousSelection.first);
        if (pair.first != null) {
            pair.second = selectDirectory(previousSelection.second);
        }
        return pair;
    }

    private File selectDirectory(File previousFile) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (previousFile != null) {
            chooser.setSelectedFile(previousFile);
        }
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    private Pair<File> loadPreviousSelection() {
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

    private void saveSelection(Pair<File> directoriesToCompare) {
        if (!directoriesToCompare.isNull()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZED_NAME))) {
                out.writeObject(directoriesToCompare);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayDifference(List<Path> difference) {
        System.out.println(difference);
    }

    private static class Pair<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        public T first;
        public T second;

        public boolean isNull() {
            return first == null || second == null;
        }
    }

}

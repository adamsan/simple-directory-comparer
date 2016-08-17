package hu.adamsan.utilities.filecomparer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFileChooser;

public class App {

    private DirectoryComparator directoryComparator;
    private SearchPersistence searchPersistence;

    public DirectoryComparator getDirectoryComparator() {
        return directoryComparator;
    }

    public void setDirectoryComparator(DirectoryComparator directoryComparator) {
        this.directoryComparator = directoryComparator;
    }

    public void setPersistence(SearchPersistence searchPersistence) {
        this.searchPersistence = searchPersistence;
    }

    public void start() {
        Pair<File> previousSelection = searchPersistence.loadPreviousSelection();
        Pair<File> dirs = select(previousSelection);
        if (!dirs.isNull()) {
            searchPersistence.saveSelection(dirs);
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
            removePreviousSelectionIfCancelPressed(chooser);
        }
        chooser.showOpenDialog(null);
        File selectedFile = chooser.getSelectedFile();
        return selectedFile;
    }

    private void removePreviousSelectionIfCancelPressed(JFileChooser chooser) {
        chooser.addActionListener((ActionEvent e) -> {
            if (isCancelPressed(e)) {
                chooser.setSelectedFile(null);
            }
        });
    }

    private boolean isCancelPressed(ActionEvent e) {
        return e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION);
    }

    private void displayDifference(List<Path> difference) {
        System.out.println(difference);
    }

}

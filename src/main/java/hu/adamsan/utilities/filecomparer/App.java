package hu.adamsan.utilities.filecomparer;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFileChooser;

public class App {

    private DirectoryComparator directoryComparator;
    private SearchPersistence searchPersistence;
    private DifferenceDisplayer differenceDisplayer;

    public void setDifferenceDisplayer(DifferenceDisplayer differenceDisplayer) {
        this.differenceDisplayer = differenceDisplayer;
    }

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
            displayDifference(dirs, difference);
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
        return returnSelectedIfCancelWasNotPressed(chooser);
    }

    private File returnSelectedIfCancelWasNotPressed(JFileChooser chooser) {
        File selectedFile = null;
        if (chooser.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
            selectedFile = chooser.getSelectedFile();
        }
        return selectedFile;
    }

    private void displayDifference(Pair<File> dirs, List<Path> difference) {
        differenceDisplayer.displayDifference(dirs, difference);
    }

}

package hu.adamsan.utilities.filecomparer;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class DifferenceDisplayer {

    public void displayDifference(Pair<File> pair, List<Path> difference) {
        System.out.println(pair);
        System.out.println(difference);
    }

}

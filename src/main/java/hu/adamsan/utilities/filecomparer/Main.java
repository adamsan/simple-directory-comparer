package hu.adamsan.utilities.filecomparer;

public class Main {

    public static void main(String[] args) {
        App program = new App();
        program.setDirectoryComparator(new DirectoryComparator());
        program.setPersistence(new SearchPersistence());
        program.setDifferenceDisplayer(new DifferenceDisplayer());
        program.start();
    }
}

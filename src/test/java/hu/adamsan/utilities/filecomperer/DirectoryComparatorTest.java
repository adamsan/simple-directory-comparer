package hu.adamsan.utilities.filecomperer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import hu.adamsan.utilities.filecomparer.DirectoryComparator;

public class DirectoryComparatorTest {

    private static final String RESOURCE_PATH = "./src/test/resources/";
    private static final Path NONEXISTING_DIR = Paths.get("no_such_directory");
    private static final Path EMPTY_DIR = Paths.get(RESOURCE_PATH + "folder/empty");
    private static final Path COMPLETE_FOLDER = Paths.get(RESOURCE_PATH + "folder/folder_with_files");
    private static final Path INCOMPLETE_FOLDER = Paths.get(RESOURCE_PATH + "folder/folder_with_missing_content");
    private DirectoryComparator comparator = new DirectoryComparator();

    @Test
    public void canCreateComparator() {
        assertNotNull(comparator);
    }

    @Test(expected = RuntimeException.class)
    public void comparatorThrowsExceptionWhenPathsDontExists() throws Exception {
        comparator.setDirectories(NONEXISTING_DIR, NONEXISTING_DIR);
    }

    @Test
    public void comparatorReturnsEmptyWhenComparingTheSameDirectories() throws Exception {
        Path p = Paths.get(".");
        comparator.setDirectories(p, p);
        List<Path> result = comparator.calculateDifference();
        assertEquals(0, result.size());
    }

    @Test
    public void comparatorDoesNotThrowExceptionWhenExistingDirectoriesAreSet() throws Exception {
        comparator.setDirectories(EMPTY_DIR, EMPTY_DIR);
        comparator.setDirectories(COMPLETE_FOLDER, INCOMPLETE_FOLDER);
    }

    @Test
    public void comparatorReturnsResourcesDirectoriesAndFilesTest() throws Exception {
        comparator.setDirectories(COMPLETE_FOLDER, INCOMPLETE_FOLDER);
        List<Path> missing = comparator.calculateDifference();
        assertEquals(2, missing.size());
    }

}

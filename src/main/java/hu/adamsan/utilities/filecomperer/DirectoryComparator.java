package hu.adamsan.utilities.filecomperer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DirectoryComparator {

    private Path dirA;
    private Path dirB;

    public void setDirectories(Path directoryA, Path directoryB) {
        throwExceptionIfDirectoriesNotFound(directoryA, directoryB);
        this.dirA = directoryA;
        this.dirB = directoryB;
    }

    private void throwExceptionIfDirectoriesNotFound(Path directoryA, Path directoryB) {
        throwExceptionIfDirectoryNotFound(directoryA);
        throwExceptionIfDirectoryNotFound(directoryB);
    }

    private void throwExceptionIfDirectoryNotFound(Path directory) {
        if (!Files.exists(directory, LinkOption.NOFOLLOW_LINKS)) {
            throw new DirectoryNotexistsException();
        }
    }

    public List<Path> calculateDifference() {
        if(dirA.equals(dirB)) {
            return Collections.emptyList();
        }
        try {
            List<Path> filesInB = Files.list(dirB)
                    .map(lastPartOfPath)
                    .collect(Collectors.toList());
            List<Path> difference = Files.list(dirA)
                    .map(lastPartOfPath)
                    .filter(p -> !filesInB.contains(p))
                    .collect(Collectors.toList());
            return difference;

        } catch (IOException e) {
        }
        return Collections.emptyList();
    }

    private Function<Path, Path> lastPartOfPath = (path) -> {
        int nameCount = path.getNameCount();
        return path.subpath(nameCount - 1, nameCount);
    };

    public static class DirectoryNotexistsException extends RuntimeException {}

}

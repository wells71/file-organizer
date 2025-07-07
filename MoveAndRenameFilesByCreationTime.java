import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.List;

public class MoveAndRenameFilesByCreationTime {

    public static void moveAndRename(Path source, Path destination) throws IOException {
        if (Files.notExists(destination))
            Files.createDirectories(destination);

        List<Path> files = Files.list(source)
                .filter(Files::isRegularFile)
                .sorted(Comparator.comparing(MoveAndRenameFilesByCreationTime::getCreationTime))
                .toList();

        int counter = 1;
        for (Path file : files) {
            String newName = counter + ". " + file.getFileName();
            Path target = destination.resolve(newName);

            while (Files.exists(target)) {
                counter++;
                newName = counter + ". " + file.getFileName();
                target = destination.resolve(newName);
            }

            Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + file.getFileName() + " → " + newName);
            counter++;
        }
    }

    private static FileTime getCreationTime(Path path) {
        try {
            return Files.readAttributes(path, BasicFileAttributes.class).creationTime();
        } catch (IOException e) {
            return FileTime.fromMillis(0);
        }
    }
}

import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("Path to source");
        Path destination = Paths.get("Path to destination");

        try {
            MoveAndRenameFilesByCreationTime.moveAndRename(source, destination);
            System.out.println("Files moved and renamed successfully!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

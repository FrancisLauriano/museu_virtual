package utils;

//import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.github.cdimascio.dotenv.Dotenv;


public class ImageStorageUtil {
    
    private static final Dotenv dotenv = Dotenv.load();
    private static final String IMAGE_DIR = dotenv.get("IMAGE_DIR");
    
    public static String saveImage(String imageName, InputStream imageStream) throws IOException {
        Files.createDirectories(Paths.get(IMAGE_DIR));

        String imagePath = IMAGE_DIR + imageName;

        try (FileOutputStream outputStream = new FileOutputStream(imagePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = imageStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return imagePath; 
    }
}

package service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

import io.github.cdimascio.dotenv.Dotenv;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class FirebaseService {
    private static final Dotenv dotenv = Dotenv.load();

    public static void initializeFirebase() throws IOException {
        String credentialsPath = dotenv.get("FIREBASE_CREDENTIALS_PATH");
        String bucketName = dotenv.get("BUCKET_NAME");

        FileInputStream serviceAccount = new FileInputStream(credentialsPath);

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setStorageBucket(bucketName)
            .build();

        FirebaseApp.initializeApp(options);
        System.out.println("Firebase inicializado com sucesso.");
    }

    public static String uploadImage(String fileName, InputStream imageStream) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        if (fileName == null || fileName.isEmpty()) {
            fileName = UUID.randomUUID().toString() + ".jpg";
        }

        Blob blob = bucket.create(fileName, imageStream, "image/jpeg");

        String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucket.getName(), fileName);
        System.out.println("Imagem carregada em: " + imageUrl);
        return imageUrl;
    }

}

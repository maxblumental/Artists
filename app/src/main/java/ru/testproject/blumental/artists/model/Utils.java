package ru.testproject.blumental.artists.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
public class Utils {
    private static String SMALL_COVER_DIR = "small_covers";
    private static String COVER_DIR = "covers";

    public static File getThumbnail(Context context, int id) throws MalformedURLException {
        return new File(context.getFilesDir()
                + File.separator + SMALL_COVER_DIR
                + File.separator + Integer.toString(id) + "_small");
    }

    public static File getCoverFile(Context context, int id) throws MalformedURLException {
        return new File(context.getFilesDir()
                + File.separator + COVER_DIR
                + File.separator + Integer.toString(id));
    }

    public static Bitmap downloadCover(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Can't establish connection.");
        }

        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] bytes;
        try {
            inputStream = connection.getInputStream();
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }

            bytes = outputStream.toByteArray();
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } finally {
            connection.disconnect();
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static String getStringFromAssetsFile(Context context, String filename) {
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package ru.testproject.blumental.artists.model;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Maxim Blumental on 3/30/2016.
 * bvmaks@gmail.com
 */
public class Utils {
    private static String SMALL_COVER_DIR = "small_covers";
    private static String COVER_DIR = "covers";

    public static File getSmallCoverFile(Context context, String url) throws MalformedURLException {
        URL coverUrl = new URL(url);
        return new File(context.getFilesDir()
                + File.separator + SMALL_COVER_DIR
                + File.separator + coverUrl.getFile());
    }

    public static File getCoverFile(Context context, String url) throws MalformedURLException {
        URL coverUrl = new URL(url);
        return new File(context.getFilesDir()
                + File.separator + COVER_DIR
                + File.separator + coverUrl.getFile());
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

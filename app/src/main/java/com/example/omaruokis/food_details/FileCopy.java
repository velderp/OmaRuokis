package com.example.omaruokis.food_details;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File copy methods
 * @author Mika
 */
public class FileCopy {
    private static final FileCopy ourInstance = new FileCopy();

    public static FileCopy getInstance() {
        return ourInstance;
    }

    private FileCopy() {

    }

    /**
     * Copy asset database to application's local database folder.
     * @param context context in which file copy if performed.
     * @throws IOException file copy failed
     */
    public void copyAssetDatabase(Context context, String dbName) throws IOException {
        // Location from which to copy
        InputStream inputStream = context.getAssets().open(dbName);
        // File destination
        OutputStream outputStream = new FileOutputStream(context.getDatabasePath(dbName));
        byte[] buffer = new byte[8192];
        // Copy the file.
        int length = inputStream.read(buffer);
        while (length > 0) {
            outputStream.write(buffer, 0, length);
            length = inputStream.read(buffer);
        }
        // Finis file copying.
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}

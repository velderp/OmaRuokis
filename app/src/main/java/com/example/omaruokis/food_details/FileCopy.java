package com.example.omaruokis.food_details;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {
    private static final FileCopy ourInstance = new FileCopy();

    public static FileCopy getInstance() {
        return ourInstance;
    }

    private FileCopy() {

    }

    public void copyAssetDatabase(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("word_database");
        OutputStream outputStream = new FileOutputStream(context.getDatabasePath("word_database"));
        byte[] buffer = new byte[8192];
        int length = inputStream.read(buffer);
        while (length > 0) {
            outputStream.write(buffer, 0, length);
            length = inputStream.read(buffer);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

}

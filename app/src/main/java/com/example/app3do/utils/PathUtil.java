package com.example.app3do.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;

public class PathUtil {
    public static String getRealPathFromURI(Context context, Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        File file = new File(context.getFilesDir(), name);
//        try {
//            InputStream inputStream = context.getContentResolver().openInputStream(uri);
//            FileOutputStream outputStream = new FileOutputStream(file);
//            int read = 0;
//            int maxBufferSize = 1 * 1024 * 1024;
//            int bytesAvailable = inputStream.available();
//
//            //int bufferSize = 1024;
//            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
//
//            final byte[] buffers = new byte[bufferSize];
//            while ((read = inputStream.read(buffers)) != -1) {
//                outputStream.write(buffers, 0, read);
//            }
//        } catch (Exception e) {
//        }
        return file.getPath();
    }
}

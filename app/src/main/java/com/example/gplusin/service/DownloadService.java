package com.example.gplusin.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {

    // https://stackoverflow.com/questions/18210700/best-method-to-download-image-from-url-in-android


    // https://www.geeksforgeeks.org/how-to-retrieve-data-from-the-firebase-realtime-database-in-android/

    // android studio from server to broadcast without firebase

    public static final int UPDATE_PROGRESS = 8344;
    String folder_main = "Gln_Folder";


    public DownloadService() {
        super("DownloadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        String urlToDownload = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");

        try {

            //create url and connect
            URL url = null;
            try {
                url = new URL(urlToDownload);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            URLConnection connection = null;
            try {
                connection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.connect();

            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());


            File outerFolder = null;
            File inerDire=null;
            if (type.equals("image")) {
                 outerFolder = new File(Environment.getExternalStorageDirectory(), "Gln_Folder/images");


                 inerDire = new File(outerFolder.getAbsoluteFile(), System.currentTimeMillis() + ".jpg");

            }else if (type.equals("pdf")){

                 outerFolder = new File(Environment.getExternalStorageDirectory(), "Gln_Folder/pdf");


                 inerDire = new File(outerFolder.getAbsoluteFile(), System.currentTimeMillis() + ".pdf");

            }


            if (!outerFolder.exists()) {
                outerFolder.mkdirs();
            }

            inerDire.createNewFile();


            FileOutputStream output = new FileOutputStream(inerDire);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress", (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            // close streams
            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress", 100);

        receiver.send(UPDATE_PROGRESS, resultData);
    }

}
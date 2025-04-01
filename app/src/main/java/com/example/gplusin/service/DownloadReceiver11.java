package com.example.gplusin.service;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.example.gplusin.utils.Common;

public class DownloadReceiver11 extends ResultReceiver {

    ProgressDialog progressDialog = new ProgressDialog(Common.adhocincomecontex);

    public DownloadReceiver11(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        super.onReceiveResult(resultCode, resultData);

        if (resultCode == DownloadService.UPDATE_PROGRESS) {


            int progress = resultData.getInt("progress"); //get the progress
            progressDialog.setProgress(progress);
            progressDialog.setMessage("Images Is Downloading");
            progressDialog.show();

            if (progress == 100) {

                progressDialog.dismiss();

            }
        }
    }
}
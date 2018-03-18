package com.example.ali.project_fin.Compress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.ali.project_fin.Main_page;

import java.io.File;
import java.io.IOException;

public class UnZipFile extends AsyncTask<String, Void, Void> {
    ProgressDialog p;
    Main_page main_page;
    Context context;
    String pass;

    public UnZipFile(Main_page main_page, Context context, String pass) {
        this.main_page = main_page;
        this.context = context;
        p = new ProgressDialog(main_page);
        this.pass=pass;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Unziping file , please wait");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }


    @Override
    protected Void doInBackground(String... params) {

        try {


            CompressTools.unzip(params[0],"");

        } catch (IOException e) {


        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        p.dismiss();
       // String newFileName =GetPathFileEncrypted(get_file_name());

        // after this we must put the decryption file
    }

/*
    public String get_file_name() {

        File file = new File(path);
        String name = file.getName();
        return name;
    }
    */

    public String GetPathFileDecompressed(String lastFile) {

        String newPath = null;
        String path_file = Environment.getExternalStorageDirectory().toString() + "/StrongBox";

        File directory = new File(path_file);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getName();
            if (filename.equals(lastFile)) {
                newPath = files[i].getAbsolutePath();
                break;
            }

        }

        return newPath;

    }

}

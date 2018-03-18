package com.example.ali.project_fin.Compress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.ali.project_fin.Cryption.Cryption_background;
import com.example.ali.project_fin.Main_page;

/**
 * Created by ali on 13/03/17.
 */

public class ZipFile extends AsyncTask<String, Void, Void> {
    ProgressDialog p;
    Main_page main_page;
    Context context;
    String password;
    Cryption_background cryption_background;

    public ZipFile( Context context ,ProgressDialog p) {
         this.context = context;
         this.p=p;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Compressing file , please wait");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }


    @Override
    protected Void doInBackground(String... params) {

        String files[]={params[0]};

         CompressTools.zip(files,params[1]);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
         p.dismiss();


    }

}

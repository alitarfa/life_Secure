package com.example.ali.project_fin.cloud;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.services.Dropbox;
import com.example.ali.project_fin.R;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Pattern;

public class CloudStorage extends AppCompatActivity {
     Button getFile;
    private String filePath;
    
    //// TODO: 4/25/17 change the file chooser to get the all file lilke cryption  
    //// TODO: 4/25/17 sms when the application closed
    //// TODO: 4/25/17 add the page vide whene no item in the recycleView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_storage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        getFile= (Button) findViewById(R.id.get_file_btn_cloud);
        getFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(CloudStorage.this, FilePickerActivity.class);

                intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*"));

                intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, false);

                startActivityForResult(intent, 1);


            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

            Toast.makeText(this, "File path ["+filePath+"]", Toast.LENGTH_LONG).show();

            //getDropBoxStorage(filePath);
            new don(this).execute(filePath);

        }

    }

    public class don extends AsyncTask<String,Void,Void> {
        Context context;
        ProgressDialog p;

        public  don(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p=new ProgressDialog(CloudStorage.this);
            p.setMessage("Uploading File Please Wait ...");
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            p.dismiss();
        }

        @Override
        protected Void doInBackground(String... voids) {


            CloudRail.setAppKey("58fb6398ff21b5017c7cd065");

            Dropbox cs = new Dropbox(context, getResources().getString(R.string.dropBoxClient),getResources().getString(R.string.dropBoxSecretKey));

            try {
                File file=new File(voids[0]);

                cs.upload("/"+file.getName(), new FileInputStream(new File(voids[0])), voids[0].length(), false);

            }catch (Exception e) {
                // TODO: handle error
            } finally {


            }

            return null;

        }
    }




}

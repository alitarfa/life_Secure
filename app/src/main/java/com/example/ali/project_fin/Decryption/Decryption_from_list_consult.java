package com.example.ali.project_fin.Decryption;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.ali.project_fin.Activity.MainActivity;
import com.example.ali.project_fin.Information_item.HistoInfo;
import com.example.ali.project_fin.Information_item.Information;
import com.example.ali.project_fin.database.database;
import com.example.ali.project_fin.historique.ToulsHisto;
import com.scottyab.aescrypt.AESCrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ali on 11/03/17.
 */


  // this whene the user select decrypt the file from the consultaion list

public class Decryption_from_list_consult extends AsyncTask<byte[], Integer, Void> {
    // in this you must create the second asyn for the decryption file
    ProgressDialog p;
    Context context;
    database db;
    String path;
    byte[] iv;
    byte[] key;
    int position;
    MainActivity activity;

    public Decryption_from_list_consult(Context context, MainActivity activity, ArrayList<Information> list, int position) {
        this.context = context;
        p = new ProgressDialog(activity);
        db = new database(context);
        this.path = list.get(position).filePath;
        this.position = position;
        this.activity=activity;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Decrypting file , please wait");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected Void doInBackground(byte[]... params) {


        try {

            decrypt_all_type_of_file(params[0], params[1]);


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        p.dismiss();
        db.delete_raw(get_file_name());
        db.insertHisto(new HistoInfo("Decryption",get_file_name(), ToulsHisto.getDate()));

/*
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "The operation ended successfully", Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
*/

        Toast.makeText(context, "The operation ended successfully", Toast.LENGTH_SHORT).show();

    }

    // this to get the file name with extenstion
    public String get_file_name() {

        File file = new File(path);
        String name = file.getName();
        return name;
    }


    //to decrypt all type of files
    public void decrypt_all_type_of_file(byte[] iv, byte[] pass) throws GeneralSecurityException {

        String file_name = get_file_name();
        SecretKeySpec c = new SecretKeySpec(pass, "AES");


        try {

            byte[] file_decrypted = AESCrypt.decrypt(c, iv, convert_file_to_byte_array(path));

            saveFile2(file_decrypted, file_name);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // this convert to byte
    public byte[] convert_file_to_byte_array(String path) throws IOException {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[10000000];
        for (int readNum; ((readNum = fis.read(b)) != -1); ) {
            bos.write(b, 0, readNum);

        }

        byte[] bytes = bos.toByteArray();

        return bytes;
    }

    public void saveFile2(byte[] data, String outFileName) {

        FileOutputStream fos = null;
        String file_path = db.get_file_path(outFileName);

        try {

            String folder_main = "StrongBox";

            // File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdirs();
            }

            String p = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StrongBox";

            File ff = new File(p, outFileName);

            fos = new FileOutputStream(ff);

            fos.write(data);

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();
        } finally {

            try {

                fos.close();

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }
        }

    }

    // function for delete the file from the source path
    public void delete_file(String file_path) {

        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_path);
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file_path);
            }
        }
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }


}



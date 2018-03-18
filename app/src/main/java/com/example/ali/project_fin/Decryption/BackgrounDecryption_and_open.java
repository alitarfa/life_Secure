package com.example.ali.project_fin.Decryption;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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


// desc
//..... this class for the decryption mode whene the user click on open file in the section of
// consultaion so we use the Thread for decrypt the file and put it into other file until the user close
// the file then we delete the file for the folder secondry

public class BackgrounDecryption_and_open extends AsyncTask<byte[], Integer, Void> {
    // in this you must create the second asyn for the decryption file
    ProgressDialog p;
    Context context;
    database db;
    String path;
    byte[] iv;
    byte[] key;
    int position;
    ArrayList<Information> list;
    public String redPath;


    public BackgrounDecryption_and_open(MainActivity activity, Context context, ArrayList<Information> list, int position, byte[] iv, byte[] key, ProgressDialog p) {
        this.context = context;
        db = new database(context);
        this.iv = iv;
        this.key = key;
        this.list = list;
        this.position = position;
        path = list.get(position).filePath;
        this.p=new ProgressDialog(activity);

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Opening file,please wait");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected Void doInBackground(byte[]... params) {


        try {

            decrypt_all_type_of_file();


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        p.dismiss();

        db.insertHisto(new HistoInfo("Opened",get_file_name(), ToulsHisto.getDate()));

        //then you must open the file by the default application
        OpenFile(list, position);

    }

    // this to get the file name with extenstion
    public String get_file_name() {

        File file = new File(path);
        String name = file.getName();
        return name;
    }

    //to decrypt all type of files
    public void decrypt_all_type_of_file() throws GeneralSecurityException {

        String file_name = get_file_name();
        SecretKeySpec c = new SecretKeySpec(key, "AES");


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

        try {

            String folder_main = "TemporaryFolder";

            // File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdirs();
            }

            String p = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TemporaryFolder";

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

    public void OpenFile(ArrayList<Information> list, int position) {
        // for this put the type for the data and add the attr in the class Information
        File file = new File(GetFileFromNewPath(list.get(position).fileName));
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), list.get(position).fileType + "/" + list.get(position).fileFormat);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context.getApplicationContext(), "No Application available to view File", Toast.LENGTH_SHORT).show();
        }
    }

    public String GetFileFromNewPath(String fileName) {
        // her you must find the solution for gte the file from the second pace
        String file_path = null;

        String path_file = Environment.getExternalStorageDirectory().toString() + "/TemporaryFolder";
        Log.e("the fila pathe is :", path_file);
        File directory = new File(path_file);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {

            Log.e("the fila pathe is :", files[i].getAbsolutePath());
            String filename = files[i].getAbsolutePath();
            String filenameArray[] = filename.split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            String ex = extension.toLowerCase();
            if (files[i].getName().equals(fileName)) {

                file_path = files[i].getAbsolutePath();
                break;

            }

        }

        return file_path;
    }


}
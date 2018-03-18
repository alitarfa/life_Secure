package com.example.ali.project_fin.Cryption;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.desai.vatsal.mydynamictoast.MyDynamicToast;
import com.example.ali.project_fin.Information_item.HistoInfo;
import com.example.ali.project_fin.Main_page;
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
import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ali on 04/03/17.
 */


public class Cryption_background extends AsyncTask<String, Integer, Void> {
    Context context;
    Main_page main_page;
    ProgressDialog p;
    database db;
    RelativeLayout relativeLayout;
    ArrayList<String> path;
    long a;
    byte[] iv, key;
    byte[] byte_file;
    boolean cheked;


    public Cryption_background(Main_page main_page, Context context, RelativeLayout relativeLayout, ArrayList<String> path, ProgressDialog p, String password, boolean cheked) {
        this.main_page = main_page;
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.path = path;

        this.p = p;
        db = new database(context);
        this.cheked = cheked;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage("Encrypting file , please wait");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected Void doInBackground(String... params) {


        try {
            crypt_all_type_file(params[0]);


        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        p.dismiss();
        // to delete the file from the source place
        //delete_file(path);
       //// TODO: 4/17/17 delete the file





        MyDynamicToast.successMessage(main_page, "The operation ended successfully");


    }

    // this to get the file name with extenstion
    public String get_file_name(String localpath) {

        File file = new File(localpath);
        String name = file.getName();
        return name;
    }

    // to crypt all type of files
    public void crypt_all_type_file(String pass) throws InvalidKeySpecException, NoSuchAlgorithmException {

        for (int i=0;i<path.size();i++){

        String file_name = get_file_name(path.get(i));
        key = getKey();
        iv = getIV();

        // how to get the other information

        String type_file="simple";

        if (cheked){
            type_file="zip";
        }
        // save the information file
        save_info_file(get_file_name(path.get(i)), path.get(i), key, iv, pass,type_file);

        SecretKeySpec c = new SecretKeySpec(key, "AES");


        try {

            // to crypt the file and get the byte array crypted

            byte_file = AESCrypt.encrypt(c, iv, convert_file_to_byte_array(path.get(i)));
            // save the file with original extension and the name
            saveFile2(byte_file, file_name);

            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
            Boolean  b = preferences.getBoolean("histo",false);
            if (b==false){
               // Toast.makeText(context, b+"", Toast.LENGTH_SHORT).show();
                //in this add the action of cryption
                db.insertHisto(new HistoInfo("Cryption",file_name, ToulsHisto.getDate()));
            }




            //delete_file(path);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

            String folder_main = "StrongBox";

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
                // vider ram
                byte_file = null;

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }
        }

    }

    public void save_info_file(String file_name, String file_path, byte[] password, byte[] iv_file, String pass_cry,String type_file) {


        long a =db.insert_info_file(file_name, file_path, password, iv_file, pass_cry,type_file);

        Log.e("The file is saving into the dataBase",a+"");

    }

    // function for delete the file from the source path
    public void delete_file(String file_path) {

        File fdelete = new File(file_path);
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


    private static byte[] getKey() {

        KeyGenerator keyGen;

        byte[] dataKey = null;

        try {

            // Generate 256-bit key

            keyGen = KeyGenerator.getInstance("AES");

            keyGen.init(256);

            SecretKey secretKey = keyGen.generateKey();

            dataKey = secretKey.getEncoded();

        } catch (NoSuchAlgorithmException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

        return dataKey;

    }


    private static byte[] getIV() {

        SecureRandom random = new SecureRandom();

        byte[] iv = random.generateSeed(16);

        return iv;

    }

    public String GetPathFileEncrypted(String lastFile) {
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


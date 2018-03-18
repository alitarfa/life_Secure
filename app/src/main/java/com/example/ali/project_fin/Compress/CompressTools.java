package com.example.ali.project_fin.Compress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;




import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by ali on 12/03/17.
 */

public class CompressTools {

    public static void zip(String[] _files, String zipFileName) {
        try {
            BufferedInputStream origin = null;

            String p = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StrongBox";


            // i guess you must put the extension of the file like .rar or .zip

            int pos = zipFileName.lastIndexOf(".");
            if (pos > 0) {
                zipFileName = zipFileName.substring(0, pos);
            }

            File file = new File(p, zipFileName + ".zip");


            FileOutputStream dest = new FileOutputStream(file);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));
            byte data[] = new byte[1024];

            for (int i = 0; i < _files.length; i++) {
                Log.v("Compress", "Adding: " + _files[i]);
                FileInputStream fi = new FileInputStream(_files[i]);
                origin = new BufferedInputStream(fi, 1024);

                ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unzip1(String _zipFile, String fileName) {

        //create target location folder if not exist
        //dirChecker(_targetLocatioan);

        try {

            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {

                //create dir if required while unzipping
                if (ze.isDirectory()) {
                    /// dirChecker(ze.getName());

                } else {

                    String p = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StrongBox";


                    FileOutputStream fout = new FileOutputStream(p + "a.jpg");
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void unzip(String zipFile, String location) throws IOException {
        try {
            String p = Environment.getExternalStorageDirectory().getAbsolutePath() + "/StrongBox/ZipFile";
            File f = new File(p);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = p + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        FileOutputStream fout = new FileOutputStream(path, false);
                        try {
                            for (int c = zin.read(); c != -1; c = zin.read()) {
                                fout.write(c);
                            }
                            zin.closeEntry();
                        } finally {
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            Log.e("one", "Unzip exception", e);
        }
    }

}

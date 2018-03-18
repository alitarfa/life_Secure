package com.example.ali.project_fin.Touls_enc_decry;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ali on 11/03/17.
 */

public class ToulsEcrtDery {




    public static String get_file_name(String path) {
        File file = new File(path);
        String name = file.getName();
        return name;
    }



}

package com.example.ali.project_fin.DeleteFile;

import java.io.File;

/**
 * Created by ali on 14/03/17.
 */

public class DeleteFile {


    public static boolean deleteFile(String filePath) {

        File file = new File(filePath);
        return file.delete();

    }


}

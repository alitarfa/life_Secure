package com.example.ali.project_fin.utileFilleFolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ali.project_fin.R;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ali on 4/9/17.
 */

public class FileFolderUtil {

     private static DialogProperties pro_for_folder;
    static  FilePickerDialog dialog;


    public static ArrayList<String> getAllSelectedPath(final Context context){

        final ArrayList<String> pathfile=new ArrayList<>();
        final ArrayList<String > pathFolder=new ArrayList<>();
        final ArrayList<String > pathAllSelected=new ArrayList<>();

        pro_for_folder = new DialogProperties();
        pro_for_folder.selection_mode = DialogConfigs.MULTI_MODE;
        pro_for_folder.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        pro_for_folder.offset = new File(DialogConfigs.DEFAULT_DIR);
        pro_for_folder.root = new File(DialogConfigs.DEFAULT_DIR);
        pro_for_folder.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;

        pro_for_folder.extensions = null;
        dialog= new FilePickerDialog(context,pro_for_folder);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {


            @Override
            public void onSelectedFilePaths(String[] files) {

                if (!(files.length==0)){

                     for (int i=0;i<files.length;i++){

                          //get all path selected
                         pathAllSelected.add(files[i]);


                     }

                    for (int j=0;j<pathAllSelected.size();j++){

                        File file=new File(pathAllSelected.get(j));
                        //check if file
                        if(file.isFile()){
                             pathfile.add(file.getAbsolutePath());
                        }
                        //check if dir
                        if (file.isDirectory()){
                            //get the all file into the folder
                            File [] files2 = file.listFiles();

                            for (int i=0;i<files2.length;i++){

                                if (files2[i].isFile()) {
                                  // TODO: 4/9/17 put all in one folder or no
                                    pathfile.add(files2[i].getAbsolutePath());

                                }
                            }
                        }
                    }
                }

            }


        });

        dialog.show();

        return pathfile;
    }




}

package com.example.ali.project_fin.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.project_fin.Cryption.FileUtils;
import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.alert_dialogue.NoticeDialogFragment;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;


public class cryption_page extends Fragment  {

    ListView  listView;
    private static DialogProperties pro_for_folder_enc;
    static  FilePickerDialog dialog_enc;
    private VerticalStepperFormLayout verticalStepperForm;


    public cryption_page() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_cryption_page, container, false);



        Button button= (Button) view.findViewById(R.id.sss);
       final Button crypt= (Button) view.findViewById(R.id.crypt);
        crypt.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_file();
                //crypt.setVisibility(View.VISIBLE);

            }
        });



        //hide the button

        crypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              start_crypt();
            }
        });




        return view;
    }




    public void get_file() {

        Main_page main_page= (Main_page) getActivity();
         main_page.getFileSelected();

    }

    public void start_crypt(){

        Main_page main_page= (Main_page) getActivity();
        main_page.showNoticeDialog(1);

        //main_page.new do_in_back().execute(1);
    }



}

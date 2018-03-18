package com.example.ali.project_fin.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;


public class decryption_page extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public decryption_page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment decryption_page.
     */
    // TODO: Rename and change types and number of parameters
    public static decryption_page newInstance(String param1, String param2) {
        decryption_page fragment = new decryption_page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decryption_page, container, false);

        Button button = (Button) view.findViewById(R.id.get_file);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_file();
            }
        });

        Button decry = (Button) view.findViewById(R.id.decry);
        decry.setVisibility(View.INVISIBLE);

        decry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_deryt();
            }
        });

        return view;

    }



    public void get_file() {

        Main_page main_page= (Main_page) getActivity();
        main_page.getFileSelectedForDecrypt();

    }

    public void start_deryt(){

        Main_page main_page= (Main_page) getActivity();
        try {
            main_page.showNoticeDialog_decry(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

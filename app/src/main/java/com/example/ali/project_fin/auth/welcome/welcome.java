package com.example.ali.project_fin.auth.welcome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.authentification;
import com.example.ali.project_fin.auth.password.SignUp;

public class welcome extends Fragment {
    Button next;



    public welcome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view=inflater.inflate(R.layout.fragment_welcome, container, false);
        next = (Button) view.findViewById(R.id.go_to_signUp);
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 goToSingUp();

             }
         });

        return view;
    }


    public void goToSingUp(){

        authentification  authentification= (com.example.ali.project_fin.auth.authentification) getActivity();
        authentification.goToSingUp();
    }

}

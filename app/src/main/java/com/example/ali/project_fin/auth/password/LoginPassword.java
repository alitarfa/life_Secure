package com.example.ali.project_fin.auth.password;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.authentification;


public class LoginPassword extends Fragment {
    EditText userName;
    EditText password;
    Button login;
    Button newAccount;
    authentification  authentification;


    public LoginPassword() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authentification= (com.example.ali.project_fin.auth.authentification) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_login_password,container,false);
        userName= (EditText) view.findViewById(R.id.uname);
        password= (EditText) view.findViewById(R.id.pas);
        login= (Button) view.findViewById(R.id.login_btn);



        return view;

    }

    private void goToCreateNewAccount() {
        authentification.createNewAcount();
    }


    public  void login(String userName,String password){

        authentification.login_btn(userName,password);
    }



}

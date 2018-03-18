package com.example.ali.project_fin.auth.password;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.authentification;
import com.example.ali.project_fin.auth.blurpass.Blurpass;

import java.util.zip.Inflater;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class SignUp extends Fragment {

    EditText userName;
    EditText password;
    EditText confPass;
    EditText email;
    EditText reponse;
    Button singUp;
    Button next;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    LayoutInflater inflater;
    View view;


    public SignUp(ProgressDialog p,SharedPreferences preferences) {
        // Required empty public constructor
        this.progressDialog=p;
        this.preferences=preferences;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater=LayoutInflater.from(getContext());
        view=inflater.inflate(R.layout.fragment_sign_up,null, false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_up, container, false);
        userName= (EditText) view.findViewById(R.id.user_name_singup);
        password= (EditText) view.findViewById(R.id.password_sinup);
        confPass= (EditText) view.findViewById(R.id.confirm_singup);
        email= (EditText) view.findViewById(R.id.email_sinup);
        reponse= (EditText) view.findViewById(R.id.response);

        next= (Button) view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=userName.getText().toString().trim();
                String pass=password.getText().toString().trim();
                String confpass=confPass.getText().toString().trim();
                String email1=email.getText().toString().trim();
                String response=reponse.getText().toString().trim();

                   if (validate(name,email1,pass,response)){

                       if (!confpass.equals(pass)){
                           Toast.makeText(getContext(), "Password d ont match", Toast.LENGTH_SHORT).show();

                       }else {

                          new singup(name,pass,confpass,email1,response,progressDialog,preferences).execute();

                       }

                   }else {

                       Toast.makeText(getContext(), "Signup failed", Toast.LENGTH_LONG).show();
                       
                   }
                


            }
        });

        return view;
    }


   // this for the validation of the formauair
    public boolean validate(String a, String b, String c, String q) {


        boolean valid = true;

        if (a.isEmpty() || a.length() < 3) {
            userName.setError("at least 3 characters");
            valid = false;
        } else {
            userName.setError(null);
        }

        if (b.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(b).matches()) {
              email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (c.isEmpty() || c.length() < 4 || c.length() > 5) {
            password.setError("pass word must compose de 5 number");
            valid = false;
        } else {
            password.setError(null);
        }
        if (q.isEmpty() || q.length() < 3) {
            reponse.setError("at least 3 characters");
            valid = false;
        } else {

              reponse.setError(null);
        }

        return valid;
    }


    private void signUpMethod(String userName, String password, String confpass, String email, String reponse) {

        authentification  authentification= (com.example.ali.project_fin.auth.authentification) getActivity();
        authentification.signUp(userName,password,confpass,email,reponse);

    }

    public class singup extends AsyncTask<Void,Void,Void> {

        String name ;
        String pass;
        String confpass;
        String email1;
        String response;
        ProgressDialog p;
        public static final String MyPREFERENCES="myprefre";
        SharedPreferences preferences;

        public singup(String name, String pass, String confpass, String Email, String response, ProgressDialog progressDialog, SharedPreferences preferences){
         this.name=name;
            this.pass=pass;
            this.confpass=confpass;
            this.email1=Email;
            this.response=response;

            p=progressDialog;
            this.preferences=preferences;

        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.setMessage("please wait");
            p.setIndeterminate(false);
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setCancelable(false);
            p.show();
         }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
              p.dismiss();

            // in this save the key for indicate that we have
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("start_type_sec","blur");
            editor.putString("pasword_blur",pass);
            editor.commit();

            startActivity(new Intent(getActivity(),Main_page.class));
            getActivity().finish();

        }

        @Override
        protected Void doInBackground(Void... params) {

            signUpMethod(name,pass,confpass,email1,response);

            return null;
        }


    }



}

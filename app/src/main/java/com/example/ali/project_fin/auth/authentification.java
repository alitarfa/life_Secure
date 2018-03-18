package com.example.ali.project_fin.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ali.project_fin.Information_item.Contact;
import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;

import com.example.ali.project_fin.auth.Pattern.PatternFragment;
import com.example.ali.project_fin.auth.blurpass.Blurpass;
import com.example.ali.project_fin.auth.createAccount.CreateAccount;
import com.example.ali.project_fin.auth.password.SignUp;
import com.example.ali.project_fin.auth.welcome.welcome;
import com.example.ali.project_fin.database.database;
import com.google.gson.Gson;

import java.util.ArrayList;

public class authentification extends AppCompatActivity {
    public static final String MyPREFERENCES="myprefre";
    database db;
    ProgressDialog p;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

           db=new database(this);
        
        // this to get the shared prefrences
        
          preferences= getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        //PatternFragment patter=new PatternFragment(getApplicationContext());


         String typeSecure= preferences.getString("start_type_sec","signup");


           if (typeSecure.equals("blur")){
              
               
                startActivity(new Intent(getApplicationContext(), Blurpass.class));
               
           }

         if (typeSecure.equals("signup")){

             /*
             p=new ProgressDialog(this);
             welcome welcome=new welcome();
             FragmentManager fragmentManager = getSupportFragmentManager();
             FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
             fragmentTransaction.replace(android.R.id.content,welcome);
             fragmentTransaction.commit();
*/
              startActivity(new Intent(this, CreateAccount.class));

         }


         if(typeSecure.equals("pattern")){

             PatternFragment patternFragment=new PatternFragment(getApplicationContext());
             FragmentManager fragmentManager = getSupportFragmentManager();
             FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
             fragmentTransaction.replace(android.R.id.content,patternFragment);
             fragmentTransaction.commit();

         }





    }

    public  void goToSingUp(){


        SignUp signUp=new SignUp(p,preferences);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_for_enter,R.anim.slide_from_exit);

        fragmentTransaction.replace(android.R.id.content,signUp);
        fragmentTransaction.commit();

    }

    public ArrayList<Integer> getCodePatternFromSharedPref(){

        ArrayList<Integer> list=new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonText=preferences.getString("codePattern",null);
        Integer[] codePattern=gson.fromJson(jsonText,Integer[].class);
        for(int i=0;i<codePattern.length;i++){
            list.add(codePattern[i]);
        }
        return list;
    }

    public void login_btn(String userName,String password) {

       // String passFromBd = db.getPassWordForLogin(userName);

       // verifyLogin(userName, password, passFromBd);


    }

    public void signUp(String userName, String password, String confpass, String email, String reponse) {



            Contact s = new Contact();
            s.setName(userName);
            s.setEmail(email);
            s.setPass(password);
            s.setQues(reponse);


            long a=db.insercontact(s);


        }

    public void verifyLogin(String uname,String pass,String co) {

        if(pass.equals(co)){

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.Custom);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            finish();
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);

            Intent i=new Intent(this, Main_page.class);
            startActivity(i);

        }else

            Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show();

    }

    public  void createNewAcount(){

        SignUp signUp=new SignUp(p,preferences);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_for_enter,R.anim.slide_from_exit);
        fragmentTransaction.replace(android.R.id.content,signUp);
        fragmentTransaction.commit();

    }

    @Override
    protected void onPause() {
        try {
            if (p != null && p.isShowing()) {

                p.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
            if (p != null && p.isShowing()) {

                p.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}

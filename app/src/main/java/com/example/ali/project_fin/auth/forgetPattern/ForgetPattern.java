package com.example.ali.project_fin.auth.forgetPattern;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.Pattern.SetPattern;
import com.example.ali.project_fin.auth.blurpass.BlurPassNew;
import com.example.ali.project_fin.auth.blurpass.Blurpass;
import com.example.ali.project_fin.database.database;

public class ForgetPattern extends AppCompatActivity {
       EditText response ;
       Button getpass;

        database  database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pattern);

        response = (EditText) findViewById(R.id.aaaa);



        getpass= (Button) findViewById(R.id.getpass);

        database=new database(this);

        getpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String res=response.getText().toString().trim();

                if(database.getReposnse().equals(res)){

                    String forgetType=getIntent().getExtras().getString("forget");
                    if (forgetType.equals("blur")){
                              Intent  intent=new Intent(getApplicationContext(),BlurPassNew.class);
                               intent.putExtra("fromForget","tomain");
                               startActivity(intent);

                    }else {
                        Intent  intent=new Intent(getApplicationContext(),SetPattern.class);
                        intent.putExtra("fromForget","tomain");
                        startActivity(intent);

                    }

              }else {
                  Toast.makeText(ForgetPattern.this, " wrong Response!", Toast.LENGTH_SHORT).show();

              }


            }
        });

    }
}

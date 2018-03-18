package com.example.ali.project_fin.start.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.authentification;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(new Intent(getApplication(),authentification.class));
                startActivity.this.finish();
            }
        }, 3000);

    }
}

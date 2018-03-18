package com.example.ali.project_fin.auth.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.database.database;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ChangeProfile extends AppCompatActivity {
     database db;
     EditText name;
     EditText email;
     EditText response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        db=new database(this);
        ArrayList<String> list=db.getInformationPerson();
        name= (EditText) findViewById(R.id.name_change_profile);
        email= (EditText) findViewById(R.id.email_change_profile);
        response= (EditText) findViewById(R.id.response);
        name.setText(list.get(0));
        email.setText(list.get(1));
        response.setText(list.get(2));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });



    }

    public void saveInformation(View view){

      String Name=name.getText().toString().trim();
        String Email=email.getText().toString().trim();
         String Reponse=response.getText().toString().trim();
        if (Name.equals("")||Email.equals("")||Reponse.equals("")){

            Toast.makeText(this, "Check your Info", Toast.LENGTH_SHORT).show();
        }else{
            load();
            // TODO: 4/19/17 check the problem  

        }

    }


    public  void annuler(View view){

        this.finish();

    }


    // for animation
    public void load() {

        animateButtonWidth();

        fadeOutTextAndShowProgressDialog();

        nextAction();
    }

    private void fadeOutTextAndShowProgressDialog() {
        TextView textView= (TextView) findViewById(R.id.text);
        textView.animate().alpha(0f)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showProgressDialog();
                    }
                })
                .start();
    }

    private void animateButtonWidth() {
        FrameLayout button= (FrameLayout) findViewById(R.id.button);

        ValueAnimator anim = ValueAnimator.ofInt(button.getMeasuredWidth(), getFabWidth());

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                FrameLayout button= (FrameLayout) findViewById(R.id.button);
                ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
                layoutParams.width = val;
                button.requestLayout();
            }
        });
        anim.setDuration(250);
        anim.start();
    }

    private void showProgressDialog() {
        ProgressBar progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setAlpha(1f);
        progressBar
                .getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(VISIBLE);
    }

    private void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                revealButton();

                fadeOutProgressDialog();

                delayedStartNextActivity();
            }
        }, 3000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealButton() {
        FrameLayout button= (FrameLayout) findViewById(R.id.button);
        button.setElevation(0f);
        View reveal=findViewById(R.id.reveal);

        reveal.setVisibility(VISIBLE);

        int cx =  reveal.getWidth();
        int cy =  reveal.getHeight();


        int x = (int) (getFabWidth() / 2 +  button.getX());
        int y = (int) (getFabWidth() / 2 +  button.getY());

        float finalRadius = Math.max(cx, cy) * 1.2f;

        Animator reveal2 = ViewAnimationUtils
                .createCircularReveal(reveal, x, y, getFabWidth(), finalRadius);

        reveal2.setDuration(500);
        reveal2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset(animation);

//                finish();
            }

            private void reset(Animator animation) {
                super.onAnimationEnd(animation);
                FrameLayout button= (FrameLayout) findViewById(R.id.button);
                View reveal=findViewById(R.id.reveal);
                TextView text= (TextView) findViewById(R.id.text);
                reveal.setVisibility(INVISIBLE);
                text.setVisibility(VISIBLE);
                text.setAlpha(1f);
                button.setElevation(4f);
                ViewGroup.LayoutParams layoutParams =  button.getLayoutParams();
                layoutParams.width = (int) (getResources().getDisplayMetrics().density * 300);
                button.requestLayout();
            }
        });

        reveal2.start();
    }

    private void fadeOutProgressDialog() {
        ProgressBar progressBar= (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.animate().alpha(0f).setDuration(150).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        }).start();
    }

    private void delayedStartNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // in this save the key for indicate that we have
                String Name=name.getText().toString().trim();
                String Email=email.getText().toString().trim();
                String Reponse=response.getText().toString().trim();
                long a = db.upDateInformationPerson(Name,Email,Reponse);
                // Toast.makeText(getBaseContext(),"The Info Changed"+a,Toast.LENGTH_SHORT).show();

            }
        }, 50);
    }

    private int getFabWidth() {
        return  72;
    }



}

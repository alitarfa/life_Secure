package com.example.ali.project_fin.auth.createAccount;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.example.ali.project_fin.Information_item.Contact;
import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.authentification;
import com.example.ali.project_fin.auth.password.SignUp;
import com.example.ali.project_fin.database.database;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CreateAccount extends AppCompatActivity {
    EditText userName;
    EditText password;
    EditText confPass;
    EditText email;
    EditText reponse;
    Button singUp;
    FrameLayout next;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.progressDialog=new ProgressDialog(getApplication());
        preferences=getSharedPreferences("myprefre",MODE_PRIVATE);
        db=new database(this);

        userName= (EditText) findViewById(R.id.user_name_singup);
        password= (EditText)  findViewById(R.id.password_sinup);
        confPass= (EditText)  findViewById(R.id.confirm_singup);
        email= (EditText)  findViewById(R.id.email_sinup);
        reponse= (EditText) findViewById(R.id.response);

        next= (FrameLayout)  findViewById(R.id.button);
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
                        Toast.makeText(getBaseContext(), "Password d ont match", Toast.LENGTH_SHORT).show();

                    }else {

                       // new SignUp.singup(name,pass,confpass,email1,response,progressDialog,preferences).execute();

                        signUp(name,pass,confpass,email1,response);
                        load();

                    }

                }else {

                    Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

                }



            }
        });


    }


    public void signUp(String userName, String password, String confpass, String email, String reponse) {

        Contact s = new Contact();
        s.setName(userName);
        s.setEmail(email);
        s.setPass(password);
        s.setQues(reponse);

        long a=db.insercontact(s);


    }


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

    // for animation
    public void load() {

        animateButtonWidth();

        fadeOutTextAndShowProgressDialog();

        nextAction();
    }


    private void fadeOutTextAndShowProgressDialog() {
        TextView textView= (TextView) findViewById(R.id.text);
        textView.animate().alpha(0f)
                .setDuration(250)
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
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("start_type_sec","blur");
            editor.putString("pasword_blur",password.getText().toString().trim());
            editor.commit();

                startActivity(new Intent(getApplication(),Main_page.class));
                finish();

            }
        }, 50);
    }

    private int getFabWidth() {
        return  72;
    }



}

package com.example.ali.project_fin.auth.blurpass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.forgetPattern.ForgetPattern;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

public class Blurpass extends AppCompatActivity implements BlurLockView.OnLeftButtonClickListener, BlurLockView.OnPasswordInputListener {


    private BlurLockView blurLockView;
    private ImageView imageView1;
     SharedPreferences preferences;
    public static final String MyPREFERENCES="myprefre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_blurpass);

        imageView1 = (ImageView)findViewById(R.id.image_1);

        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);

        blurLockView.setBlurredView(imageView1);

        blurLockView.setBlurRadius(5);

        blurLockView.setTextColor(R.color.colorThree);

        //blurLockView.setOverlayColor(R.color.colorThree);

        // Set the password
        preferences=getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        String s=preferences.getString("pasword_blur","");

        blurLockView.setCorrectPassword(s);
        blurLockView.setType(Password.NUMBER,true);

        blurLockView.setTitle("Enter Password");

        blurLockView.setRightButton("Delete");
        blurLockView.setLeftButton("Forget pass !");


        blurLockView.setOnLeftButtonClickListener(this);
        blurLockView.setOnPasswordInputListener(this);



    }



    @Override
    public void correct(String inputPassword) {

        startActivity(new Intent(this, Main_page.class));
        finish();

    }

    @Override
    public void incorrect(String inputPassword) {
        Toast.makeText(this,
                "incorrect",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void input(String inputPassword) {

    }

    @Override
    public void onClick() {

        Intent intent =new Intent(this, ForgetPattern.class);
        intent.putExtra("forget","blur");
        startActivity(intent);
        this.finish();


    }
}

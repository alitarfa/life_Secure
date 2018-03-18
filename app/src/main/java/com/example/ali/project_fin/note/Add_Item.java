package com.example.ali.project_fin.note;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ali.project_fin.Information_item.NoteInfo;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.database.database;

import java.util.ArrayList;

public class Add_Item extends AppCompatActivity {
    SharedPreferences mPrefs;
    EditText one;
    EditText two;
    String Title ;
    String Info ;


    database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__item);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Add Item");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        db =new database(this);


    }


    public  void add_item(View view){

        one= (EditText) findViewById(R.id.titre );
        two = (EditText) findViewById(R.id.informa);

        Title=one.getText().toString();
        Info=two.getText().toString();

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getRandomColor();



        if ((Title.isEmpty())&& (Info.isEmpty()) ){

            Toast.makeText(this, "Empty Field", Toast.LENGTH_SHORT).show();

        }else {
            if (db.add_item(Title, Info, color1)) {
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                one.setText("");
                two.setText("");

            }

            else {
                Toast.makeText(this, "Item No Added", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public  void  get_All_item( View view){
        ArrayList<NoteInfo> list = db.get_all_item();

        Toast.makeText(this,list.get(0).info,Toast.LENGTH_LONG).show();


    }



}

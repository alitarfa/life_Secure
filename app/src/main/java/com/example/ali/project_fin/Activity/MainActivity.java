package com.example.ali.project_fin.Activity;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ali.project_fin.Decryption.BackgrounDecryption_and_open;
import com.example.ali.project_fin.Decryption.Decryption_from_list_consult;

import com.example.ali.project_fin.Fragments.document_fragment;
import com.example.ali.project_fin.Fragments.image_fragment;
import com.example.ali.project_fin.Fragments.music_fragment;
import com.example.ali.project_fin.Fragments.video_fragment;
import com.example.ali.project_fin.Information_item.Information;

import com.example.ali.project_fin.R;
import com.example.ali.project_fin.Touls_enc_decry.ToulsEcrtDery;
import com.example.ali.project_fin.alert_dialogue.Dialogue_fragment_decry;
import com.example.ali.project_fin.alert_dialogue.OpenFileDialogue;
import com.example.ali.project_fin.database.database;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Dialogue_fragment_decry.NoticeDialogListener_dery,OpenFileDialogue.NoticeDialogListener_dery {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ProgressDialog p;
    database db;

    ArrayList<Information> list;
    int position;
    byte[] iv;
    byte[] key;
    String password;
    private String path;
    private byte[] iv2;
    private byte[] key2;
    private ArrayList<Information> list2;
    private int position2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbarhi);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        int position = getIntent().getExtras().getInt("position");

        viewPager.setCurrentItem(position);
        p = new ProgressDialog(this);
        db = new database(getBaseContext());

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new document_fragment(), "Doc");
        adapter.addFragment(new music_fragment(), "Music");
        adapter.addFragment(new video_fragment(), "Video");
        adapter.addFragment(new image_fragment(), "Image");

        viewPager.setAdapter(adapter);
    }



    public void showOpenFileDialogue(ArrayList<Information> list, int position, byte[] iv, byte[] key) {
        // Create an instance of the dialog fragment and show
        this.iv2=iv;
        this.key2=key;
        this.list2=list;
        this.position2=position;

        OpenFileDialogue dialog = new OpenFileDialogue();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogueOpenFilePos(DialogFragment dialog, String p1) {

       String pass = db.get_password(list2.get(position2).fileName);

        if (!p1.equals(pass)) {

            Toast.makeText(this, "failed pass", Toast.LENGTH_SHORT).show();
        } else {

            BackgrounDecryption_and_open decryption=new BackgrounDecryption_and_open(MainActivity.this,this,list2,position2,db.get_iv(list2.get(position2).fileName),db.get_key(list2.get(position2).fileName),p);
            decryption.execute();

        }



    }

    @Override
    public void onDialogueOpenFileNeg(DialogFragment dialog) {



    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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


    // ------------


    public void showNoticeDialog_decry(ArrayList<Information> list, int i) {
        // Create an instance of the dialog fragment and show it
        Dialogue_fragment_decry dialog = new Dialogue_fragment_decry();
        this.list = list;
        this.position = i;
        this.path = list.get(position).filePath;
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick_decry(DialogFragment dialog, String p1) {

        byte[] key = db.get_key(ToulsEcrtDery.get_file_name(path));
        byte[] iv = db.get_iv(ToulsEcrtDery.get_file_name(path));
        String pass = db.get_password(ToulsEcrtDery.get_file_name(path));


            if (!p1.equals(pass)) {

                Toast.makeText(this, "failed pass", Toast.LENGTH_SHORT).show();
            } else {

                Decryption_from_list_consult decryption = new Decryption_from_list_consult(getApplicationContext(),MainActivity.this,list,position);
                decryption.execute(iv,key);

            }

        }



    @Override
    public void onDialogNegativeClick_decry(DialogFragment dialog) {

    }


}
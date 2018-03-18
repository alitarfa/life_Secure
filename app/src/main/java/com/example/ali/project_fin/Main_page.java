package com.example.ali.project_fin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ali.project_fin.Activity.MainActivity;
import com.example.ali.project_fin.Compress.UnZipFile;
import com.example.ali.project_fin.Cryption.Cryption_background;
import com.example.ali.project_fin.Decryption.Decryption_from_home;
import com.example.ali.project_fin.Fragments.Home_fragment;
import com.example.ali.project_fin.Fragments.cryption_page;
import com.example.ali.project_fin.Fragments.decryption_page;
import com.example.ali.project_fin.Information_item.Information;
import com.example.ali.project_fin.Setting.Setting;
import com.example.ali.project_fin.chat_application.mainActivitychat;
import com.example.ali.project_fin.cloud.CloudStorage;
import com.example.ali.project_fin.historique.Historique;
import com.example.ali.project_fin.alert_dialogue.Dialogue_fragment_decry;
import com.example.ali.project_fin.alert_dialogue.NoticeDialogFragment;
import com.example.ali.project_fin.database.database;
import com.example.ali.project_fin.note.NoteM;
import com.example.ali.project_fin.sec_sms.MainSms;
import com.example.ali.project_fin.utileFilleFolder.FileFolderUtil;
import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Main_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NoticeDialogFragment.NoticeDialogListener, Dialogue_fragment_decry.NoticeDialogListener_dery, SharedPreferences.OnSharedPreferenceChangeListener {
    String path;
    ProgressDialog p;
    byte[] key, iv;
    byte[] byte_file;
    ArrayList<String> strings;
    RelativeLayout relativeLayout;
    database db;
    long a;
    DialogProperties properties;
    DialogProperties pro_for_folder;

    ArrayList<String> listFilePath;
    FilePickerDialog dialog;

    private static DialogProperties pro_for_folder_enc;
    static  FilePickerDialog dialog_enc;



    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton chat_item, parametre, trie_al, infor,NoteFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeStyle();
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhi);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView textView= (TextView) findViewById(R.id.title_main);
        textView.setText("Home");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // this to indicate the what page display
        displaySelectedScreen(R.id.home_page);
        relativeLayout = (RelativeLayout) findViewById(R.id.content_main_page);

        // this section for the layout pass dialogue


        p = new ProgressDialog(this);
        // initialise the database
        db = new database(this);
        //get the information
        getInformationPerson();
        strings = new ArrayList<>();
        // for the list path
        listFilePath=new ArrayList<>();
// initialise the strong box
        init_the_StrongBox();


        // for the file chooser ^_^
        properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.MULTI_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;

        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);

        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;


        dialog= new FilePickerDialog(this,properties);
        dialog.setTitle("Select a File");
        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
               // Toast.makeText(Main_page.this,files.length+"", Toast.LENGTH_SHORT).show();
                if (!(files.length==0)){
                for (int i=0;i<files.length;i++){
                    listFilePath.add(files[i]);
                }

                      path=listFilePath.get(0);
                  }

                 Button button= (Button) findViewById(R.id.decry);
                 button.setVisibility(View.VISIBLE);
                  ListView listView= (ListView) findViewById(R.id.liste_for_selected_decry);
                  Adaptery adaptery =new Adaptery(listFilePath);
                  listView.setAdapter(adaptery);

            }
        });






        //****************************************************************************************************

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fab_menu);

        chat_item = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.chat);

        parametre = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.cloud);

        infor = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.sms);

        NoteFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.note);


        chat_item.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 startActivity( new Intent(getApplication(),mainActivitychat.class));
            }
        });


        parametre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent=new Intent(getApplication(),CloudStorage.class);
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(Main_page.this,findViewById(R.id.cloud),"cloudtrans");
              startActivity(intent,activityOptionsCompat.toBundle());
            }
        });


        infor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                  startActivity(new Intent(getApplication(),MainSms.class));


            }
        });

        NoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getBaseContext(), NoteM.class));
            }
        });



    }


    // to changte he style


    public void changeStyle(){

       SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
       String value=preferences.getString("theme","one");
      if (value.equals("one")){
          setTheme(R.style.MyMaterialTheme);
      }else {
           setTheme(R.style.oneforme);
      }

    }






    public void getInformationPerson(){

          //// TODO: 4/13/17 get the information from the
     ArrayList <String> info=db.getInformationPerson();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view =  navigationView.getHeaderView(0);

        TextView name= (TextView) view.findViewById(R.id.Info_name);
        TextView Email= (TextView)view. findViewById(R.id.Email_info);
        if (info.size()>0){
            name.setText(info.get(0));
            Email.setText(info.get(1));
        }else {
            name.setText("User");
            Email.setText("User@Android.com");
        }


    }

    public  void getFileSelected(){

       // return  listFilePath = FileFolderUtil.getAllSelectedPath(this);
           getAllSelectedPath(this);

    }


    public void getAllSelectedPath(final Context context){

        final ArrayList<String> pathfile=new ArrayList<>();
        final ArrayList<String > pathFolder=new ArrayList<>();
        final ArrayList<String > pathAllSelected=new ArrayList<>();

        pro_for_folder_enc = new DialogProperties();
        pro_for_folder_enc.selection_mode = DialogConfigs.MULTI_MODE;
        pro_for_folder_enc.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        pro_for_folder_enc.offset = new File(DialogConfigs.DEFAULT_DIR);
        pro_for_folder_enc.root = new File(DialogConfigs.DEFAULT_DIR);
        pro_for_folder_enc.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;

        pro_for_folder_enc.extensions = null;
        dialog_enc= new FilePickerDialog(context,pro_for_folder_enc);
        dialog_enc.setTitle("Select a File");
        dialog_enc.setDialogSelectionListener(new DialogSelectionListener() {


            @Override
            public void onSelectedFilePaths(String[] files) {

                if (!(files.length==0)){

                    for (int i=0;i<files.length;i++){

                        //get all path selected
                        pathAllSelected.add(files[i]);


                    }

                    for (int j=0;j<pathAllSelected.size();j++){

                        File file=new File(pathAllSelected.get(j));
                        //check if file
                        if(file.isFile()){
                            pathfile.add(file.getAbsolutePath());
                        }
                        //check if dir
                        if (file.isDirectory()){
                            //get the all file into the folder
                            File [] files2 = file.listFiles();

                            for (int i=0;i<files2.length;i++){

                                if (files2[i].isFile()) {
                                    // TODO: 4/9/17 put all in one folder or no
                                    pathfile.add(files2[i].getAbsolutePath());

                                }
                            }
                        }
                    }
                }

                 listFilePath=pathfile;
                 // make the btn visible ^_^
                Button cry= (Button) findViewById(R.id.crypt);
                cry.setVisibility(View.VISIBLE);
                 // make all file selected visible in the list view ^_^
                final ListView listView= (ListView) findViewById(R.id.file_selected);
                Adaptery adapter=new Adaptery(pathfile);
                listView.setAdapter(adapter);

            }

        });

        dialog_enc.show();


    }


    public class Adaptery extends BaseAdapter{
        ArrayList<String> liste;
        public Adaptery(ArrayList <String> liste){
            this.liste=liste;
        }

        @Override
        public int getCount() {
            return liste.size();
        }

        @Override
        public Object getItem(int position) {
            return liste.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view=inflater.inflate(R.layout.item_for_selected_file_for_encryption,parent,false);
            TextView title= (TextView) view.findViewById(R.id.textView19);
            title.setText(liste.get(position));
            return view;
        }
    }



    public void getFileSelectedForDecrypt(){
        properties.root = new File(DialogConfigs.DEFAULT_DIR+"/StrongBox");
        properties.selection_mode = DialogConfigs.SINGLE_MODE;

        dialog.show();
    }


    public void init_the_StrongBox() {
        String folder_main = "StrongBox";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // this for the first page home to display it
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment
        // object which is selected
        if (itemId == R.id.home_page) {
            fragment = new Home_fragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main_page, fragment);
            ft.commit();
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home_page) {
            // this for the home page
            fragment = new Home_fragment();
            TextView textView= (TextView) findViewById(R.id.title_main);
            textView.setText("Home");
        } else if (id == R.id.cryption_page) {
            //this for the cryptage
            fragment = new cryption_page();
            TextView textView= (TextView) findViewById(R.id.title_main);
            textView.setText("Encryption");

        } else if (id == R.id.decryption_page) {
            //this is for the decryptage
            fragment = new decryption_page();

            TextView textView= (TextView) findViewById(R.id.title_main);
            textView.setText("Decryption");
        } else if (id == R.id.setting_page) {

               startActivity(new Intent(getApplicationContext(),Setting.class));

        } else if (id == R.id.historique) {
             startActivity(new Intent(this,Historique.class));

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main_page, fragment);
            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//**************************** For Open Home And other **************************************
    // this for all the image button of the home screen                                     /
    public void open_page_video(View view) {
        // this to open the page of the video
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", 2);
        startActivity(intent);

    }                                           //
                                                                                           //
    public void open_page_music(View view) {
        // this to open the page of the music
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", 1);
        startActivity(intent);

    }

    public void open_page_document(View view) {
        // this to open the page of the document
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", 0);
        startActivity(intent);


    }

    public void open_page_image(View view) {
        // this to open the page of the image
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", 3);
        startActivity(intent);
    }


//*******************************************************************************************




    // to create object for the dialogue alert for the password
    public void showNoticeDialog(int i) {
        // Create an instance of the dialog fragment and show it

        if(listFilePath.size()==0){
            Toast.makeText(this, " Any File Selected !", Toast.LENGTH_SHORT).show();
        }else {

        DialogFragment dialog = new NoticeDialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        }
    }
    // this if the result ixs position what we do

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String p1, String p2, Boolean checked) {


        if (!p1.equals(p2)) {

            Toast.makeText(getBaseContext(), "problem pass", Toast.LENGTH_LONG).show();

        } else {

            SartCryption(p1, listFilePath, p,checked);

         }


    }


    public void showNoticeDialog_decry(int i) {
        // Create an instance of the dialog fragment and show it
        if(path==null){
            Toast.makeText(this, " Any File Selected !", Toast.LENGTH_SHORT).show();
        }else {
            Dialogue_fragment_decry dialog = new Dialogue_fragment_decry();
            dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
        }
    }


    //for  the Decryption_from_list_consult section
    @Override
    public void onDialogPositiveClick_decry(DialogFragment dialog, String p1) {

        byte[] key = db.get_key(get_file_name());
        byte[] iv = db.get_iv(get_file_name());
        String pass = db.get_password(get_file_name());
        String file_type=db.getTypeFile(get_file_name());





            if (getExtensiFile(get_file_name()).equals("zip")){
              // and send the pass word of file

                UnZipFile unZipFile=new UnZipFile(this,getBaseContext(),p1);
                unZipFile.execute(path);

              }else {

                if (!p1.equals(pass)) {

                    Toast.makeText(this, "failed pass", Toast.LENGTH_SHORT).show();
                }else {

                    StartDeryption(iv, key);

                }
            }



        }




    public  String getExtensiFile(String file){

        String filenameArray[] = file.split("\\.");
        String extension = filenameArray[filenameArray.length-1];
        return extension;
    }
    // this fro negative chose for the alert dailogue
    @Override
    public void onDialogNegativeClick_decry(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        Toast.makeText(this, "Operation canceled", Toast.LENGTH_SHORT).show();

    }

    public String get_file_name() {

        File file = new File(path);
        String name = file.getName();
        return name;
    }

    //this for calling the Thread of Cryption
    public void SartCryption(String password, ArrayList<String> path, ProgressDialog p, boolean cheked) {

        Cryption_background cryption_background = new Cryption_background(this, getApplicationContext(), relativeLayout, path, p,password,cheked);
        cryption_background.execute(password);

    }

    // for calling the Thread of decryption
    public void StartDeryption(byte[] iv, byte[] key) {

        Decryption_from_home decryption_from_home = new Decryption_from_home(getApplicationContext(), p, path, relativeLayout);
        decryption_from_home.execute(iv, key);

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


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
         String valu=sharedPreferences.getString("theme","one");

        if(valu.equals("one")){
            setTheme(R.style.MyMaterialTheme);
        }else {
            setTheme(R.style.oneforme);
        }


    }






}




package com.example.ali.project_fin.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.project_fin.Activity.MainActivity;
import com.example.ali.project_fin.DeleteFile.DeleteFile;
import com.example.ali.project_fin.Information_item.HistoInfo;
import com.example.ali.project_fin.Information_item.Information;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.database.database;
import com.example.ali.project_fin.historique.ToulsHisto;

import java.io.File;
import java.util.ArrayList;

public class music_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView;
    ArrayList<Information> list;
    public MainActivity activity;
    ProgressDialog p;


    public music_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment music_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static music_fragment newInstance(String param1, String param2) {
        music_fragment fragment = new music_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        list = new ArrayList<>();
        get_list_file_Strong_box_music();

        activity = (MainActivity) getActivity();
        p=new ProgressDialog(activity);

    }

    //this t get the music file
    public void get_list_file_Strong_box_music() {
        String path_file = Environment.getExternalStorageDirectory().toString() + "/StrongBox";
        Log.e("the fila pathe is :", path_file);
        File directory = new File(path_file);
        File[] files = directory.listFiles();


        for (int i = 0; i < files.length; i++) {

            Log.e("the fila pathe is :", files[i].getAbsolutePath().toString());
            String filename = files[i].getAbsolutePath().toString();
            String filenameArray[] = filename.split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            String ex = extension.toLowerCase();
            if (ex.equals("mp3")) {

                list.add(new Information(files[i].getName(), files[i].getAbsolutePath(), "audio", "mp3"));

            }

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_for_music);
        if (list.size()==0){
            TextView textView = (TextView) view.findViewById(R.id.txt_no_one_doc);
            textView.setText("No File Encrypted yet ^_^");
            textView.setHeight(350);
        }
        Adapter_for_music music = new Adapter_for_music(list, getContext(),activity);
        recyclerView.setAdapter(music);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}


//Adapter
class Adapter_for_music extends RecyclerView.Adapter<Adapter_for_music.view_holder_music> {

    ArrayList<Information> list;
    LayoutInflater inflater;
    Context context;
    database db;
    MainActivity activity;
    ProgressDialog p;



    public Adapter_for_music(ArrayList<Information> list, Context context,MainActivity activity ) {

        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        db = new database((context));
        this.activity=activity;
        this.p =new ProgressDialog(activity);


    }

    @Override
    public view_holder_music onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_for_music_file, parent, false);

        return new view_holder_music(view);
    }

    @Override
    public void onBindViewHolder(final view_holder_music holder, final int position) {
        holder.title.setText(list.get(position).fileName);
        holder.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_file(position, holder);
                //  Toast.makeText(context.getApplicationContext(),db.get_key(list.get(position).fileName)+"",Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class view_holder_music extends RecyclerView.ViewHolder {
        TextView title;
        ImageView menuOption;

        public view_holder_music(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView6);
            menuOption = (ImageView) itemView.findViewById(R.id.menu_option);
        }
    }


    public void open_file(final int position, view_holder_music holder) {

        PopupMenu menu = new PopupMenu(context, holder.menuOption);
        menu.getMenuInflater().inflate(R.menu.menu_option, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("Open")) {

                    //Toast.makeText(context,db.get_iv(list.get(position).fileName).toString(),Toast.LENGTH_LONG).show();
                   // BackgrounDecryption_and_open decryption = new BackgrounDecryption_and_open(activity,context, list, position, db.get_iv(list.get(position).fileName), db.get_key(list.get(position).fileName),p);
                    //decryption.execute();
                    activity.showOpenFileDialogue(list,position,db.get_iv(list.get(position).fileName),db.get_key(list.get(position).fileName));
                }

                if (item.getTitle().equals("Decry")) {
                    //// TODO: 07/03/17
                 activity.showNoticeDialog_decry(list,position);
                }


                if (item.getTitle().equals("Delete")) {

                    if (DeleteFile.deleteFile(list.get(position).filePath)) {

                        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
                        Boolean  b = preferences.getBoolean("histo",false);
                        if (b==false){
                            // Toast.makeText(context, b+"", Toast.LENGTH_SHORT).show();
                            //in this add the action of cryption
                            db.insertHisto(new HistoInfo("Delete",list.get(position).fileName, ToulsHisto.getDate()));
                         }

                        db.delete_raw(list.get(position).fileName);
                        // remove the item form the Array list
                        list.remove(position);
                        // then we must notify that the list has changed
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        notifyItemRemoved(position);

                        // finaly inform the user that the item has deleted
                        Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();

                    }

                }


                return true;
            }
        });


        menu.show();
    }




    //// TODO: 4/21/17 delete histo
}
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
import android.widget.Spinner;
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


public class video_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    Spinner spinner;

    ArrayList<Information> list;
    MainActivity activity;

    public video_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment video_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static video_fragment newInstance(String param1, String param2) {
        video_fragment fragment = new video_fragment();
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
        get_list_file_Strong_box_video();
        activity= (MainActivity) getActivity();
    }
    // Hi men put other type of view her
    //  (:

    //this t get the video file
    public void get_list_file_Strong_box_video() {
        String path_file = Environment.getExternalStorageDirectory().toString() + "/StrongBox";
        Log.e("the fila pathe is :", path_file);
        File directory = new File(path_file);
        File[] files = directory.listFiles();


        for (int i = 0; i < files.length; i++) {

            Log.e("the fila pathe is :", files[i].getAbsolutePath());
            String filename = files[i].getAbsolutePath();
            String filenameArray[] = filename.split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            String ex = extension.toLowerCase();
            if (ex.equals("mp4") || ex.equals("webm") || ex.equals("mkv") || ex.equals("flv") || ex.equals("vob") || ex.equals("avi") || ex.equals("wmv") || ex.equals("rm") || ex.equals("3gp")) {
                list.add(new Information(files[i].getName(), files[i].getAbsolutePath(),"video","mp4"));
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_for_video);
        if (list.size()==0){
            TextView textView = (TextView) view.findViewById(R.id.txt_no_one_doc);
            textView.setText("No File Encrypted yet ^_^");
            textView.setHeight(350);
        }
        Adapter_for_video video = new Adapter_for_video(getContext(), list,activity);
        recyclerView.setAdapter(video);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}


//Adapter

class Adapter_for_video extends RecyclerView.Adapter<Adapter_for_video.view_holder_video> {
    LayoutInflater inflater;
    ArrayList<Information> list;
    Context context;
    database db;
    MainActivity activity;
    ProgressDialog p;
    public Adapter_for_video(Context context, ArrayList<Information> list,MainActivity activity) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        db=new database(context);
        this.activity=activity;
        p=new ProgressDialog(activity);

    }

    @Override
    public view_holder_video onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_for_video_file, parent, false);


        return new view_holder_video(view);
    }

    @Override
    public void onBindViewHolder(final view_holder_video holder, final int position) {

        holder.textView.setText(list.get(position).fileName);
        holder.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_file(position,holder);

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class view_holder_video extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView menuOption;

        public view_holder_video(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView6);
            menuOption = (ImageView) itemView.findViewById(R.id.menu_option);

        }
    }


    public void open_file(final int position, view_holder_video holder){

        PopupMenu menu  =new PopupMenu(context,holder.menuOption);
        menu.getMenuInflater().inflate(R.menu.menu_option,menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("Open")) {

                    activity.showOpenFileDialogue(list,position,db.get_iv(list.get(position).fileName),db.get_key(list.get(position).fileName));
                }

                if (item.getTitle().equals("Decry")) {

                    activity.showNoticeDialog_decry(list,position);

                }


                if (item.getTitle().equals("Delete")) {

                    if (DeleteFile.deleteFile(list.get(position).filePath)) {
                        // first delete the raw from the database
                        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
                        Boolean  b = preferences.getBoolean("histo",false);
                        if (b==false){

                            db.insertHisto(new HistoInfo("Delete",list.get(position).fileName, ToulsHisto.getDate()));
                        }

                        db.delete_raw(list.get(position).fileName);
                        // remove the item form the Array list
                        list.remove(position);
                        // then we must notify that the list has changed
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        notifyItemRemoved(position);

               //// TODO: 4/21/17 changet the place of insert histo for any error

                        // finaly inform the user that the item has deleted
                        Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();

                    }
                }


                return true;
            }
        });


        menu.show();
    }

}


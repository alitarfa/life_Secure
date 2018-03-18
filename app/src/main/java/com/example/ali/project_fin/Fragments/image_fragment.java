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

public class image_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    ArrayList<Information> info = new ArrayList<>();
    MainActivity activity;

    public image_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment image_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static image_fragment newInstance(String param1, String param2) {
        image_fragment fragment = new image_fragment();
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

        get_list_file_Strong_box();
        activity = (MainActivity) getActivity();

    }

    // this to get the file name no we must cut the name the get the right name
    public void get_list_file_Strong_box() {
        String path_file = Environment.getExternalStorageDirectory().toString() + "/StrongBox";
        Log.e("the fila pathe is :",path_file);
        File directory = new File(path_file);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            Log.e("the fila pathe is :", files[i].getAbsolutePath() );
            String filename = files[i].getAbsolutePath();
            String filenameArray[] = filename.split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            String ex = extension.toLowerCase();
            if (ex.equals("jpg") || ex.equals("gif") || ex.equals("jpeg")||ex.equals("png")) {

                info.add(new Information(files[i].getName(), files[i].getAbsolutePath(), "image", "jpg"));

            }

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.re_image);
        if (info.size()==0){
            TextView textView = (TextView) view.findViewById(R.id.txt_no_one_doc);
            textView.setText("No File Encrypted yet ^_^");
            textView.setHeight(350);
        }
        Adapter_for_image_fragmet adapter_for_image_fragmet = new Adapter_for_image_fragmet(getContext(), info, activity);
        recyclerView.setAdapter(adapter_for_image_fragmet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}


// Adapter

class Adapter_for_image_fragmet extends RecyclerView.Adapter<Adapter_for_image_fragmet.your_holder_image> {

    LayoutInflater inflater;
    ArrayList<Information> info;
    Context context;
    MainActivity activity;
    database db;
    ProgressDialog p;

    public Adapter_for_image_fragmet(Context context, ArrayList<Information> info, MainActivity activity) {
        inflater = LayoutInflater.from(context);
        this.info = info;
        this.context = context;
        this.activity = activity;
        this.db = new database(context);
        p=new ProgressDialog(activity);

    }

    @Override
    public your_holder_image onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_for_file, parent, false);
        your_holder_image image = new your_holder_image(view);

        return image;

    }

    @Override
    public void onBindViewHolder(final your_holder_image holder, final int position) {
        holder.title_file.setText(info.get(position).fileName);
        holder.menuOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context, holder.menuOption);
                menu.getMenuInflater().inflate(R.menu.menu_option, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("Open")) {
                           // BackgrounDecryption_and_open decryption = new BackgrounDecryption_and_open(activity,context, info, position, db.get_iv(info.get(position).fileName), db.get_key(info.get(position).fileName),p);
                           // decryption.execute();
                            activity.showOpenFileDialogue(info,position,db.get_iv(info.get(position).fileName),db.get_key(info.get(position).fileName));
                        }

                        if (item.getTitle().equals("Decry")) {
                            //// TODO: 07/03/17
                            activity.showNoticeDialog_decry(info, position);
                        }


                        if (item.getTitle().equals("Delete")) {

                            if (DeleteFile.deleteFile(info.get(position).filePath)) {
                                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
                                Boolean  b = preferences.getBoolean("histo",false);
                                if (b==false){
                                    // Toast.makeText(context, b+"", Toast.LENGTH_SHORT).show();
                                    //in this add the action of cryption
                                    db.insertHisto(new HistoInfo("Delete",info.get(position).fileName, ToulsHisto.getDate()));
                                }

                                db.delete_raw(info.get(position).fileName);
                                // remove the item form the Array list


                                info.remove(position);
                                // then we must notify that the list has changed
                                notifyItemChanged(position);
                                notifyDataSetChanged();
                                notifyItemRemoved(position);





                                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();


                                //// TODO: 4/9/17 add try catch for the exception ^_^  in all other fragment

                            }

                        }
                        return true;
                    }
                });

                menu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    public class your_holder_image extends RecyclerView.ViewHolder {
        TextView title_file;
        ImageView menuOption;

        public your_holder_image(View itemView) {
            super(itemView);

            title_file = (TextView) itemView.findViewById(R.id.textView6);
            menuOption = (ImageView) itemView.findViewById(R.id.menu_option);


        }


    }
}
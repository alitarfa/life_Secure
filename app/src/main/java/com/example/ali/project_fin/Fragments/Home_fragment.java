package com.example.ali.project_fin.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.project_fin.Information_item.Information;
import com.example.ali.project_fin.R;

import java.io.File;
import java.util.ArrayList;


public class Home_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_fragment newInstance(String param1, String param2) {
        Home_fragment fragment = new Home_fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_fragment, container, false);
        TextView textView= (TextView) view.findViewById(R.id.doc_txt);
        TextView textView1= (TextView) view.findViewById(R.id.image_txt);
        TextView textView2= (TextView) view.findViewById(R.id.audio_txt);
        TextView textView3= (TextView) view.findViewById(R.id.video_txt);

        textView1.setText(nbrImage()+"");
        textView.setText(nbrFileDoc()+"");
        textView2.setText(get_list_file_Strong_box_music()+"");
        textView3.setText(get_list_file_Strong_box_video()+"");
        return view;


    }



    //calcul
    public int nbrFileDoc() {
        ArrayList<String> list=new ArrayList<>();
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
            if (ex.equals("rar")||ex.equals("zip")||ex.equals("pdf") || ex.equals("txt") || ex.equals("text") || ex.equals("doc") || ex.equals("docx") || ex.equals("ppt") || ex.equals("html") || ex.equals("php") || ex.equals("js") || ex.equals("java") || ex.equals("class") || ex.equals("pptx") || ex.equals("ssd") || ex.equals("sti") || ex.equals("pps")) {
                list.add("");
            }

        }

        return list.size();
    }

    public int nbrImage() {
        ArrayList<String> list=new ArrayList<>();
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

                list.add("");

            }

        }
        return list.size();

    }

    public int get_list_file_Strong_box_music() {
        int value=0;
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

              value=value+1;

            }


        }

        return value;

    }

    public int get_list_file_Strong_box_video() {
        int val=0;
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
                val++;
            }

        }

        return val;
    }


    @Override
    public void onResume() {
        super.onResume();
        LayoutInflater inflater=LayoutInflater.from(getContext());
        View view= inflater.inflate(R.layout.fragment_home_fragment, null);
        TextView textView= (TextView) view.findViewById(R.id.doc_txt);
        TextView textView1= (TextView) view.findViewById(R.id.image_txt);
        TextView textView2= (TextView) view.findViewById(R.id.audio_txt);
        TextView textView3= (TextView) view.findViewById(R.id.video_txt);

        textView1.setText(nbrImage()+"");
        textView.setText(nbrFileDoc()+"");
        textView2.setText(get_list_file_Strong_box_music()+"");
        textView3.setText(get_list_file_Strong_box_video()+"");

    }
}

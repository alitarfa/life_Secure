package com.example.ali.project_fin.auth.Pattern;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bcgdv.asia.lib.connectpattern.ConnectPatternView;
import com.example.ali.project_fin.Activity.MainActivity;
import com.example.ali.project_fin.Main_page;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.auth.authentification;
import com.example.ali.project_fin.auth.forgetPattern.ForgetPattern;

import java.util.ArrayList;


public class PatternFragment extends Fragment {


    ConnectPatternView view2;
    Context context;


    public PatternFragment(Context context) {
        this.context=context;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_pattern,container,false);
          TextView  forgetpattern= (TextView) view.findViewById(R.id.forgetpattern);
          forgetpattern.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent =new Intent(getActivity(),ForgetPattern.class);
                  intent.putExtra("forget","pattern");
                  startActivity(intent);
                  getActivity().finish();
              }
          });




        view2 = (ConnectPatternView) view.findViewById(R.id.connect);
        view2.animateIn();

        view2.setOnConnectPatternListener(new ConnectPatternView.OnConnectPatternListener() {
            @Override
            public void onPatternEntered(ArrayList<Integer> result) {
                //in this example we animate the widget out as soon as the user connects 3 points
                if (result.size() >3) {
                    ArrayList<Integer> list=(ArrayList<Integer>) result.clone();

                    if (getCodeFromPrefre().equals(list)){

                        startActivity(new Intent(context,Main_page.class));
                        getActivity().finish();
                    }else {
                        Toast.makeText(context, "Wrong Pattern ! ", Toast.LENGTH_SHORT).show();
                    }



                }else{

                     Toast.makeText(context, "Short pattern Problem", Toast.LENGTH_SHORT).show();

                 }

            }

            @Override
            public void onPatternAbandoned() {


            }

            @Override
            public void animateInStart() {

            }

            @Override
            public void animateInEnd() {

            }

            @Override
            public void animateOutStart() {

            }

            @Override
            public void animateOutEnd() {
                view2.setVisibility(View.GONE);
                view2.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 1000);
            }
        });


        return view;
    }

      public ArrayList<Integer> getCodeFromPrefre(){

          ArrayList<Integer> listCode;
          authentification authentification= (com.example.ali.project_fin.auth.authentification) getActivity();
          listCode = authentification.getCodePatternFromSharedPref();

             return listCode;
      }

}

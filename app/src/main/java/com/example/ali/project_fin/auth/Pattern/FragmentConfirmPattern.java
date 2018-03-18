package com.example.ali.project_fin.auth.Pattern;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bcgdv.asia.lib.connectpattern.ConnectPatternView;
import com.example.ali.project_fin.R;

import java.util.ArrayList;


public class FragmentConfirmPattern extends Fragment {

    ConnectPatternView view2;
    Context context;
    ArrayList<Integer> patterCode;
     public FragmentConfirmPattern(){


     }
     public FragmentConfirmPattern(Context context, ArrayList<Integer> patternCode) {
        this.context=context;
        this.patterCode=patternCode;
     }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_confirm_pattern,container,false);

        view2 = (ConnectPatternView) view.findViewById(R.id.connect);
        view2.animateIn();

         //annuler btn
        Button annuler = (Button) view.findViewById(R.id.annulerConf);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        Button confrim= (Button) view.findViewById(R.id.confirm);
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Pattern Saved ", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });


        view2.setOnConnectPatternListener(new ConnectPatternView.OnConnectPatternListener() {
            @Override
            public void onPatternEntered(ArrayList<Integer> result) {
                //in this example we animate the widget out as soon as the user connects 3 points
                if (result.size() >3) {

                     if (result.equals(patterCode)){
                         Toast.makeText(context, "Correct Pattern", Toast.LENGTH_SHORT).show();
                          
                        saveCodeInPrefrences();

                     }else {
                         Toast.makeText(context, "your Pattern is incorrect", Toast.LENGTH_SHORT).show();
                     }

                }else{

                    Toast.makeText(context, "Short Pattern Problem !", Toast.LENGTH_SHORT).show();
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
                        view2.animateIn();
                    }
                }, 1000);
            }
        });

        return view;


    }


    public  void saveCodeInPrefrences(){

         SetPattern setPattern= (SetPattern) getActivity();
         setPattern.saveCodePaternIntoPre(patterCode);

    }



}

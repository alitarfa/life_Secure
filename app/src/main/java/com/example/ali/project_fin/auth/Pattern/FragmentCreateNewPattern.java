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


public class FragmentCreateNewPattern extends Fragment {

     Context context;
     ConnectPatternView view2;
     ArrayList<Integer> patternCode;



    public FragmentCreateNewPattern(Context context) {
        // Required empty public constructor
        this.context =context;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_create_new_pattern, container, false);

        //next btn
        final Button  next= (Button) view.findViewById(R.id.Next);
        if (patternCode==null){
            next.setEnabled(false);
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressContiner();
            }
        });


        //annuler btn

        Button annuler= (Button) view.findViewById(R.id.annuler);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });



        view2 = (ConnectPatternView) view.findViewById(R.id.connect);
        view2.animateIn();

        view2.setOnConnectPatternListener(new ConnectPatternView.OnConnectPatternListener() {
            @Override
            public void onPatternEntered(ArrayList<Integer> result) {
                //in this example we animate the widget out as soon as the user connects 3 points
                if (result.size() > 3) {

                    patternCode= (ArrayList<Integer>) result.clone();
                    next.setEnabled(true);

                } else {

                    Toast.makeText(context, "Short Pattern Problem", Toast.LENGTH_SHORT).show();
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



    public void pressContiner(){
        SetPattern setPattern= (SetPattern) getActivity();
        setPattern.contuner(patternCode);

    }




}

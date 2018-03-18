package com.example.ali.project_fin.alert_dialogue;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ali.project_fin.Information_item.NoteInfo;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.database.database;
import com.example.ali.project_fin.note.NoteM;

import java.util.ArrayList;

/**
 * Created by ali on 4/21/17.
 */

public class NoteDialogue  extends DialogFragment {
    public int position ;
    database db;
    Context context;


    public NoteDialogue(int position,Context context) {
        this.position=position;
        this.db=new database(context);
        this.context=context;
    }

    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String newValues, String cDesc);
        public void onDialogNegativeClick(DialogFragment dialog);
    }




    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    EditText title;
    EditText  desc;

    String cTitle;
    String cDesc;
    static  int color=000000;
    Button cOne;
    Button cTwo;
    Button cThree;
    Button cFour;
    Button cFive;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();



        View view=inflater.inflate(R.layout.fragment_modification_note,null);
        title= (EditText) view.findViewById(R.id.title);
        desc= (EditText) view.findViewById(R.id.desc);
        ArrayList<NoteInfo> list=new ArrayList<>();
        list=db.getContent(position);

        title.setText(list.get(0).title);
        desc.setText(list.get(0).info);


        cOne= (Button) view.findViewById(R.id.color_simple);
        cTwo= (Button) view.findViewById(R.id.color_blue);
        cThree= (Button) view.findViewById(R.id.color_creen);
        cFour= (Button) view.findViewById(R.id.color_red);
        cFive= (Button) view.findViewById(R.id.color_yellow);




        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)

                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity

                        cTitle =title.getText().toString();
                        cDesc=desc.getText().toString();
                        getColorFromMain();

                        setNewValue(cTitle,cDesc,position,color);

                        mListener.onDialogPositiveClick(NoteDialogue.this,cTitle,cDesc);

                    }
                })


                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(NoteDialogue.this);
                    }
                });
        return builder.create();
    }




    public void setNewValue(String cTitle, String cDesc, int position,int color){
        // take your attention for this part u must add the description

        db.update_item(position,cTitle,cDesc,color);
        // Toast.makeText(getContext(), newValue, Toast.LENGTH_SHORT).show();
    }







    /////////////////////////////////////////////////////////////////////



    public void getColorFromMain(){

        NoteM activity= (NoteM) getActivity();
        color=activity.color;

    }






}






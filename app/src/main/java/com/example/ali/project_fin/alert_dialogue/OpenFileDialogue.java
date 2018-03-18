package com.example.ali.project_fin.alert_dialogue;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.ali.project_fin.R;

/**
 * Created by ali on 4/16/17.
 */

public class OpenFileDialogue extends DialogFragment {

     public interface NoticeDialogListener_dery {

        public void onDialogueOpenFilePos(DialogFragment dialog, String p1);

        public void onDialogueOpenFileNeg(DialogFragment dialog);

    }

    // Use this instance of the interface to deliver action events
    OpenFileDialogue.NoticeDialogListener_dery mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OpenFileDialogue.NoticeDialogListener_dery) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    //


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.password_open_file, null);

        builder.setMessage("enter the pass")
                .setView(view)

                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity

                        // in this section past the information of the password to main activity
                        EditText editText = (EditText) view.findViewById(R.id.b);
                        String p1 = editText.getText().toString();

                        mListener.onDialogueOpenFilePos(OpenFileDialogue.this,p1);
                    }
                })
                .setNegativeButton("no ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity

                        mListener.onDialogueOpenFileNeg(OpenFileDialogue.this);
                    }
                });
        return builder.create();
    }
}
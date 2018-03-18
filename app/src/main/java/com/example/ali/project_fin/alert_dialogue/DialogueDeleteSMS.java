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
 * Created by ali on 4/21/17.
 */

public class DialogueDeleteSMS extends DialogFragment {

    public interface NoticeDialogListenerSMS {

        public void onDialogueOpenSMSPOS(DialogFragment dialog);

        public void onDialogueOpenSMSNEG(DialogFragment dialog);

    }

    // Use this instance of the interface to deliver action events
    DialogueDeleteSMS.NoticeDialogListenerSMS mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (DialogueDeleteSMS.NoticeDialogListenerSMS) activity;

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
        builder.setMessage("Are You Sur To Delete !")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        mListener.onDialogueOpenSMSPOS(DialogueDeleteSMS.this);
                    }
                })
                .setNegativeButton("no ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mListener.onDialogueOpenSMSNEG(DialogueDeleteSMS.this);
                    }
                });
        return builder.create();
    }
}
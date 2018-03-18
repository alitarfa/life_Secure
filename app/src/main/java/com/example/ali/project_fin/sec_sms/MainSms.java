package com.example.ali.project_fin.sec_sms;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ali.project_fin.Information_item.ItemSms;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.alert_dialogue.DialogueDeleteSMS;
import com.example.ali.project_fin.database.database;

import java.util.ArrayList;

public class MainSms extends AppCompatActivity  implements DialogueDeleteSMS.NoticeDialogListenerSMS{
    RecyclerView recyclerView;
    ArrayList<ItemSms> itemSmses;

    database db;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sms);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhi);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        db=new database(this);
        recyclerView = (RecyclerView) findViewById(R.id.messageRecy);
        itemSmses =new ArrayList<>();

        itemSmses=db.getAllSms();

        AdapterSms adapterSms=new AdapterSms(this,itemSmses);
        recyclerView.setAdapter(adapterSms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));



    }


    @Override
    protected  void onResume(){
        super.onResume();

        itemSmses=db.getAllSms();
        AdapterSms adapterSms=new AdapterSms(this,itemSmses);
        recyclerView.setAdapter(adapterSms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

    }




    public void openSendSms(View view) {
        startActivity(new Intent(this,SendSms.class));
    }

   public void openDialogueForDelete(int position){
       this.position=position;
       DialogueDeleteSMS dialog = new DialogueDeleteSMS();
       dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");

   }


    @Override
    public void onDialogueOpenSMSPOS(DialogFragment dialog) {

          if (position>=0){
              db.delete_raw_SMS(position);
              itemSmses=db.getAllSms();
              AdapterSms adapterSms=new AdapterSms(this,itemSmses);
              recyclerView.setAdapter(adapterSms);
              recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
          }

    }

    @Override
    public void onDialogueOpenSMSNEG(DialogFragment dialog) {



    }




}

class  AdapterSms extends RecyclerView.Adapter<AdapterSms.ViewHolderSms>{

    ArrayList<ItemSms> list;
    Context context;
    LayoutInflater inflater;

    public AdapterSms (Context context,ArrayList<ItemSms> list){
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);

    }


    @Override
    public ViewHolderSms onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =inflater.inflate(R.layout.item_message_consult,parent,false);

        return new ViewHolderSms(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderSms holder, final int position) {
        if (list.get(position).status.equals("env")){
            holder.imageView.setImageResource(R.drawable.ic_arrow_top_right);
        }else {
            holder.imageView.setImageResource(R.drawable.ic_arrow_bottom_left);
        }
        holder.number.setText(list.get(position).number);
        holder.message.setText(list.get(position).message);



        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteOne(list.get(position).id);
                return true;
            }
        });

    }

     public void deleteOne(int position){

         MainSms mainSms= (MainSms) context;
         mainSms.openDialogueForDelete(position);

     }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class ViewHolderSms extends RecyclerView.ViewHolder{
        TextView message;
        TextView number;
        CardView cardView;
        ImageView imageView;
        public ViewHolderSms(View itemView) {
            super(itemView);
            message= (TextView) itemView.findViewById(R.id.messageTXT);
            number= (TextView) itemView.findViewById(R.id.numberTEx);
            cardView= (CardView) itemView.findViewById(R.id.card);
            imageView= (ImageView) itemView.findViewById(R.id.meorhow);

        }
    }
}

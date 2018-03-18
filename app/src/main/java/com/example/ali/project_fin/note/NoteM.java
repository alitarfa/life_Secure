package com.example.ali.project_fin.note;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ali.project_fin.Information_item.NoteInfo;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.alert_dialogue.NoteDialogue;
import com.example.ali.project_fin.database.database;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class NoteM extends AppCompatActivity  implements NoteDialogue.NoticeDialogListener  {
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton Add_item, parametre, trie_al, infor;

    boolean mIsLargeLayout;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<NoteInfo> data=new ArrayList<>();
    NoteM.MyAdapter myAdapter;
    SharedPreferences mPrefs;

    database db;
    Activity c=this;

    android.app.FragmentManager f;
    public static int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_m);


        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_note);
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

           Button button= (Button) findViewById(R.id.add);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplication(),Add_Item.class));
                }
            });


        recyclerView = (RecyclerView) findViewById(R.id.all_item);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        get_All_item();

        f= getFragmentManager();


    }



    public  void  get_All_item(){

        data = db.get_all_item();
        if(data.size()>0){
            myAdapter = new MyAdapter(data,c,db, f);
            recyclerView.setAdapter(myAdapter);
            //  ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);// create MyItemTouchHelperCallback
            // ItemTouchHelper touchHelper = new ItemTouchHelper(callback); // Create ItemTouchHelper and pass with parameter the MyItemTouchHelperCallback
            //touchHelper.attachToRecyclerView(recyclerView); // Attach ItemTouchHelper to RecyclerView
        }else{
            myAdapter = new MyAdapter(new ArrayList<NoteInfo>(),c,db,f);
            recyclerView.setAdapter(myAdapter);
        }

    }



    @Override
    protected void onPostResume() {
        super.onPostResume();
        get_All_item();

    }

    @Override
    public void onDialogPositiveClick(android.support.v4.app.DialogFragment dialog, String newValues, String cDesc) {
        // update the array list and update the recycleView

        get_All_item();

    }

    @Override
    public void onDialogNegativeClick(android.support.v4.app.DialogFragment dialog) {
        // do no thing
    }


// the adapter

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private ArrayList<NoteInfo> mDataset;
        Context context;
        database db;
        RecyclerView recyclerView;
        android.app.FragmentManager  f;
        private int lastPosition=-1;


        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<NoteInfo> myDataset, Context context, database db, android.app.FragmentManager f) {
            mDataset = myDataset;
            this.context=context;
            this.db=db;
            this.f=f;
        }



        public void open(final int position){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Are you sur to delete note ..?");
            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            db.delete_item(mDataset.get(position).id);
                            mDataset.remove(position);
                            notifyItemRemoved(position);
                            notifyItemChanged(position);

                        }
                    });



            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }




        public  void upDateContente(final int position){


            NoteDialogue newFragment = new NoteDialogue(mDataset.get(position).id,getBaseContext());
            newFragment.show(getSupportFragmentManager(), "missiles");


        }



        public void showDialog(int position) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NoteDialogue newFragment = new NoteDialogue(mDataset.get(position).id,getBaseContext());

            if (mIsLargeLayout) {
                // The device is using a large layout, so show the fragment as a dialog
                newFragment.show(fragmentManager, "dialog");
            } else {

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.one, newFragment);
                fragmentTransaction.commit();
            }
        }








        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note, parent, false);
            // set the view's size, margins, paddings and layout parameters

            return  new ViewHolder(view);

        }


        // this for animation

        private void setAnimation(View viewToAnimate, int position) {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                animation.setDuration(500);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        private void setFadeAnimation(View view) {
            AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1000);
            view.startAnimation(anim);

        }

        private void setScaleAnimation(View view) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(500);
            view.startAnimation(anim);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            String font_size = sharedPref.getString("font_size", "");


            if(font_size.equals("1")){

                holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                holder.info.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }else if (font_size.equals("2")){
                holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                holder.info.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            }else if (font_size.equals("3")){
                holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                holder.info.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);


            }

            holder.title.setText(mDataset.get(position).title);
            holder.info.setText(mDataset.get(position).info);
            String t=mDataset.get(position).title;

            if(!t.equals("")){
                t =t.substring(0,1);



                TextDrawable.IBuilder builder = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4)
                        .endConfig()
                        .rect();

                // to build the shape of the rectangle and the color
                TextDrawable ic1 = builder.build(t.toUpperCase(),db.getColor(mDataset.get(position).id));

                holder.imageView.setImageDrawable(ic1);

                // this for the modification  of the parametre of the card view
                SharedPreferences sharedPref1 = PreferenceManager.getDefaultSharedPreferences(context);
                String value = sharedPref1.getString("animation_cards", "");

                if (value.equals("1")){
                    setFadeAnimation(holder.view);
                }
                if (value.equals("2")){
                    setAnimation(holder.view,position);
                }
                if (value.equals("3")){
                    setScaleAnimation(holder.view);
                }


            }


            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    open(position);
                    return true;
                }
            });


            // for update the content of the cardview
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // for this we must open fragment for new modification

                    upDateContente(position);
                    //showDialog(position);

                }
            });

        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }



        public class ViewHolder extends RecyclerView.ViewHolder   {
            // each data item is just a string in this case
            public TextView title;
            public TextView info;
            CardView view;
            ImageView imageView;
            public ViewHolder(View v) {
                super(v);

                title = (TextView) v.findViewById(R.id.textView2);
                info= (TextView) v.findViewById(R.id.textView);
                view= (CardView) v.findViewById(R.id.card_view);
                imageView = (ImageView) v.findViewById(R.id.imageView);
            }


        }
    }






    public void getColorWhite(View view){
        color=getResources().getColor(R.color.c_five);

    }

    public void getColorRed(View view){

        color=getResources().getColor(R.color.c_four);

    }

    public void getColorYellow(View view){



        color= getResources().getColor(R.color.c_three);

    }

    public void getColorBleu(View view){

        color= getResources().getColor(R.color.c_two);

    }

    public void getColorGreen(View view){

        color= getResources().getColor(R.color.c_one);

    }


}

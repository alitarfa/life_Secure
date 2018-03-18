package com.example.ali.project_fin.historique;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.project_fin.Information_item.HistoInfo;
import com.example.ali.project_fin.R;
import com.example.ali.project_fin.database.database;

import java.util.ArrayList;

public class Historique extends AppCompatActivity {
    private ListView listview;
    private TextView title;
    private Button btn_prev;
    private Button btn_next;
    private Button btn_add;

    Context ctx = this;
    database bd = new database(this);
    private int increment = 0;

    private ArrayList<HistoInfo> data;
    ArrayList<HistoInfo> sd;

    AdapterHisto adapterHisto;


    public int TOTAL_LIST_ITEMS ;
    public int NUM_ITEMS_PAGE  ;
    private int noOfBtns;
    private Button[] btns;

    private int pageCount ;
     RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        changeStyle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique2);
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

        recyclerView = (RecyclerView) findViewById(R.id.rec_histo);
        TOTAL_LIST_ITEMS = bd.getProfilesCount1();
        NUM_ITEMS_PAGE   =10;

        btn_prev     = (Button)findViewById(R.id.prev);
        btn_next     = (Button)findViewById(R.id.next);
        title    = (TextView)findViewById(R.id.title);
        btn_prev.setEnabled(false);


        data = new ArrayList<HistoInfo>();

        /**
         * this block is for checking the number of pages
         * ====================================================
         */

        int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;

        pageCount = TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+1;
        /**
         * =====================================================
         */

        /**
         * The ArrayList data contains all the list items
         */


        data=bd.get_desc();

        loadList(0);
        if(increment+1 == pageCount)
        {
            btn_next.setEnabled(false);
        }



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(increment+1 == pageCount)
                {
                    btn_next.setEnabled(false);
                }else{
                    increment++;
                    loadList(increment);
                    CheckEnable();
                }
                if(increment == 0)

                    btn_prev.setEnabled(false);else
                    btn_prev.setEnabled(true);

            }
        });




        btn_prev.setOnClickListener(new View.OnClickListener (){

            public void onClick(View v) {

                increment--;
                loadList(increment);
                CheckEnable();
                if(increment+1 == pageCount)

                    btn_next.setEnabled(false);else
                    btn_next.setEnabled(true);

            }
        });

    }
    public void changeStyle(){

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String value=preferences.getString("theme","one");
        if (value.equals("one")){
            setTheme(R.style.MyMaterialTheme);
        }else {
            setTheme(R.style.oneforme);
        }

    }
    private void CheckEnable()
    {
        if(increment+1 == pageCount)
        {
            btn_next.setEnabled(false);
        }
        else if(increment == 0)
        {
            btn_prev.setEnabled(false);
        }
        else
        {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    } private void loadList(int number)
    {
        ArrayList<HistoInfo> sort= new ArrayList<HistoInfo>();
        ArrayList<HistoInfo> sort1 = new ArrayList<HistoInfo>();
        title.setText("Page "+(number+1)+" of "+pageCount);

        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<data.size())
            {
                sort.add(data.get(i));
            }
            else
            {
                break;
            }
        }
        sort1=sort;
        sd=sort1;
        adapterHisto =new AdapterHisto(sd,this);

        recyclerView.setAdapter(adapterHisto);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sort=null;
    }




    public class AdapterHisto extends RecyclerView.Adapter<AdapterHisto.viewHolde> {

        ArrayList<HistoInfo> Item ;
       Context context;
        LayoutInflater inflater;
        public AdapterHisto (ArrayList<HistoInfo> Item,Context  context) {
            this.Item = Item;
            this.context=context;
            inflater=LayoutInflater.from(context);
        }



        @Override
        public viewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.histo_item,parent,false);

            return new viewHolde(view);

        }

        @Override
        public void onBindViewHolder(viewHolde holder, int position) {
            holder.fileName.setText(Item.get(position).Desc);

               holder.date.setText(Item.get(position).Name);

              holder.action.setText(Item.get(position).Time);


        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return Item.size();
        }

        public class viewHolde extends RecyclerView.ViewHolder{
           TextView   fileName,action,date;

            public viewHolde(View itemView) {
                super(itemView);
                fileName= (TextView) itemView.findViewById(R.id.name_file);
                action= (TextView) itemView.findViewById(R.id.action_file);
                date= (TextView) itemView.findViewById(R.id.date_action);
            }
        }



    }





    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.his_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_histo:
           if (sd.size()>0){
               new delete().execute();
             }else {

               Toast.makeText(ctx, "no history to delete !", Toast.LENGTH_SHORT).show();
           }

                // TODO: 4/19/17 change the style of the histo  
                // TODO: 4/19/17 make the sms 
                // TODO: 4/19/17 note  
                // TODO: 4/19/17 style  
                // TODO: 4/19/17



                break;
        }
        return true;
    }


    public  class delete extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Historique.this);
            progressDialog.setTitle("Deleting Historique");
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();

             startActivity(new Intent(getBaseContext(),Historique.class));
             finish();

        }

        @Override
        protected Void doInBackground(Void... params) {
            database database=new database(getBaseContext());
            database.deleteAllHisto();
            return null;
        }


    }
}

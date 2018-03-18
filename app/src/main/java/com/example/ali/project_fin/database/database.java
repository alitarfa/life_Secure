package com.example.ali.project_fin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ali.project_fin.Information_item.Contact;
import com.example.ali.project_fin.Information_item.HistoInfo;
import com.example.ali.project_fin.Information_item.ItemSms;
import com.example.ali.project_fin.Information_item.NoteInfo;

import java.util.ArrayList;

/**
 * Created by ali on 27/02/17.
 */

public class database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "cryption_project";

    // Contacts table name
    private static final String TABLE_name = "information_file_path";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String FILE_NAME = "name_file";
    private static final String FILE_PATH = "file_path";
    private static final String TABLE_CREATE = "create table contacts(id integer primary key not null , name text not null,email text not null,pass text not null,ques text)";


    private static final String  COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "pass";
    private static final String COLUMN_QUES = "ques";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_TIME = "time";




    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "create  table info_file(id_file integer primary key not null ,file_name text,file_path text,password integer,iv integer,pass_cr text,type_file text)";
        String history="create table histories(id integer primary key not null , name text not null,desc text not null,time text not null)";
        String sms="create table sms(id integer primary key not null,message text,number text,status text)";
        db.execSQL("create table note(id integer primary key autoincrement,title text,info text,impor integer)");
        db.execSQL(sms);
        db.execSQL(history);
        db.execSQL(query);
        db.execSQL(TABLE_CREATE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE IF EXISTS info_file");
        String query = "DROP TABLE IF EXISTS contacts";
        db.execSQL("DROP TABLE IF EXISTS histories");
        db.execSQL("DROP TABLE IF EXISTS sms");
        db.execSQL("DROP TABLE IF EXISTS note");
        db.execSQL(query);
        onCreate(db);


    }

     public long upDateInformationPerson(String name, String email, String reponse){
         SQLiteDatabase database=getWritableDatabase();
         ContentValues values=new ContentValues();
         values.put("name",name);
         values.put("email",email);

         values.put("ques",reponse);
      long a= database.update("contacts",values,"id="+0,null);
         return a;
     }

    // this for the second file
    public long insert_info_file(String file_name, String file_path, byte[] password, byte[] iv, String pass,String type_file) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("file_name", file_name);
        values.put("file_path", file_path);
        values.put("password", password);
        values.put("iv", iv);
        values.put("pass_cr", pass);
        values.put("type_file",type_file);

        long a = database.insert("info_file", null, values);
        database.close();
        return a;

    }


       public  void deleteAllHisto(){
           SQLiteDatabase database=getWritableDatabase();
           database.delete("histories",null,null);
           database.close();

       }



      public void saveSms(String number,String message,String status){

          SQLiteDatabase database=getWritableDatabase();
          ContentValues values=new ContentValues();
          values.put("message",message);
          values.put("number",number);
          values.put("status",status);
          database.insert("sms",null,values);

          database.close();

      }

      public ArrayList<ItemSms> getAllSms(){
      ArrayList<ItemSms> itemSmses=new ArrayList<>();
          SQLiteDatabase database=getReadableDatabase();
          String s="select * from sms";
          Cursor cursor=database.rawQuery(s,null);
          if (cursor.moveToFirst()){
              do {

                  itemSmses.add(new ItemSms(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
              }while (cursor.moveToNext());


          }

          return itemSmses;

      }



    //for get the name file
    public String get_file_path(String file_name) {
        String file_path = null;
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from info_file";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                if (cursor.getString(1).equals(file_name)) {
                    file_path = cursor.getString(2);
                    break;
                }

            } while (cursor.moveToNext());


        }

        return file_name;
    }

    public byte[] get_key(String file_name) {
        byte[] pass = null;
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from info_file";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                String p = cursor.getString(1);
                if (p.equals(file_name)) {
                    pass = cursor.getBlob(3);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return pass;


    }

    public byte[] get_iv(String file_name) {

        byte[] pass = null;
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from info_file";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {

                String p = cursor.getString(1);
                if (p.equals(file_name)) {
                    pass = cursor.getBlob(4);
                    break;
                }

            } while (cursor.moveToNext());
        }
        return pass;


    }

    public ArrayList<String> get_all(String file_name) {

        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String s = "select * from info_file";
        Cursor cursor = database.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(1).equals(file_name)) {
                    list.add(cursor.getString(1));
                    break;
                }


            } while (cursor.moveToNext());

        }

        return list;

    }

    public String get_password(String file_name) {
        String password = null;
        SQLiteDatabase database = getReadableDatabase();
        String s = "select * from info_file";
        Cursor cursor = database.rawQuery(s, null);
        if (cursor.moveToFirst()) {
            do {

                if (file_name.equals(cursor.getString(1))) {
                    password = cursor.getString(5);
                    break;
                }

            } while (cursor.moveToNext());

        }
        return password;
    }

    public void delete_raw(String condition ){
        String tabel="info_file";
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(tabel, "file_name"+ "='" + condition + "'", null);
        database.close();
    }



    public void delete_raw_SMS(int pos){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("sms", "id"+ "='" + pos + "'", null);
        database.close();
    }

    public String getTypeFile(String file_name){
         String file_type = null;
        SQLiteDatabase database=getReadableDatabase();
        String query="select * from info_file";
        Cursor cursor=database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do {

                if (cursor.getString(1).equals(file_name)){
                   file_type=cursor.getString(6);
                    break;
                }


            }while (cursor.moveToNext());
        }




        return file_type;
    }



    public String getPassWordForLogin(String name) {
      SQLiteDatabase db =getReadableDatabase();

        String query = "select name,pass from contacts";

        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(name)) {
                    b = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return b;
    }

    public long insercontact(Contact c) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from contacts";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();


        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_PASS, c.getPass());
        values.put(COLUMN_QUES, c.getQues());

        long a= db.insert("contacts", null, values);
      return a;

    }

     public ArrayList<String> getInformationPerson(){
         ArrayList<String> info=new ArrayList<>();
          SQLiteDatabase database=getReadableDatabase();
         String query="select * from contacts";
         Cursor cursor=database.rawQuery(query,null);
         if (cursor.moveToFirst()){
             do {

                  info.add(cursor.getString(1));
                  info.add(cursor.getString(2));
                  info.add(cursor.getString(4));
                  break;

             }while (cursor.moveToNext());
         }


         return info;
     }

     public void setInformationPerson(String name,String email){
      // // TODO: 4/13/17 create the fucntion for change the info 

     }


     public String getReposnse(){
         String s = null;
         SQLiteDatabase database=getReadableDatabase();
         String query="select * from contacts";
         Cursor cursor=database.rawQuery(query,null);
         if(cursor.moveToFirst()){
             do {

                   s= cursor.getString(4);

             }while (cursor.moveToNext());


         }

          return s;
     }


















    public void insertHisto(HistoInfo info) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from histories";
        Cursor cursor = database.rawQuery(query, null);
        values.put(COLUMN_ID,cursor.getCount());
        values.put(COLUMN_NAME, info.Name);
        values.put(COLUMN_DESC,info.Desc);
        values.put(COLUMN_TIME,info.Time);

        database.insert("histories", null, values);
        database.close();

    }




    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM  create table histories(id integer primary key not null , name text not null,desc text not null,time text not null)" ;
       SQLiteDatabase  db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    public int getProfilesCount1() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from histories";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        return cursor.getCount();
    }




    public ArrayList<HistoInfo> get_desc() {


       SQLiteDatabase db = getReadableDatabase();
        String query = "select * from histories";
        Cursor cursor = db.query("histories", null, null,
                null, null, null, COLUMN_ID + " DESC  ", null);

        ArrayList <HistoInfo> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String name=cursor.getString(1);
                String desc=cursor.getString(2);
                String date=cursor.getString(3);
                list.add(new HistoInfo(name,desc,date));



            } while (cursor.moveToNext());
        }

        return list;

    }





















    public Boolean add_item(String title,String info,int impore){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put("impor",impore);
        contentValues.put("title",title);
        contentValues.put("info",info);



        // Inserting Row
        long r = db.insert("note", null, contentValues);
        if(r==-1){
            return false;

        }else
            return true;


    }

    public ArrayList<NoteInfo> get_all_item(){

        // Getting All items

        ArrayList<NoteInfo> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM note";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                list.add(new NoteInfo(cursor.getInt(0),cursor.getInt(3),cursor.getString(1),cursor.getString(2)));

            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }

    public int getColor(int id){
        int color = 0;
        SQLiteDatabase database=getReadableDatabase();
        String query="select * from note";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                if (cursor.getInt(0)==id){
                    color=cursor.getInt(3);
                    break;

                }
            }while (cursor.moveToNext());
        }

        return color;

    }


    public void update_item(int id, String title, String desc, int color){

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("info",desc);
        values.put("impor",color);

        database.update("note",values,"id="+id,null);
    }

    public void delete_item(int id){

        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from note where id="+Integer.toString(id));


    }

    public ArrayList<NoteInfo> getContent(int id){
        ArrayList<NoteInfo> informations=new ArrayList<>();
        SQLiteDatabase database=getReadableDatabase();
        String query="select * from note";
        Cursor cursor=database.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                if (id==cursor.getInt(0)){
                    informations.add(new NoteInfo(cursor.getInt(0),cursor.getInt(3),cursor.getString(1),cursor.getString(2)));
                    break;
                }


            }while (cursor.moveToNext());

        }
        return informations;
    }









}

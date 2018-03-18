package com.example.ali.project_fin.Information_item;

/**
 * Created by ali on 4/21/17.
 */

public class NoteInfo   {
    public  int id;
    public String title ;
    public String info;
    int impor;


    public NoteInfo(){

    }


    public  NoteInfo(int id,int impor,String title,String info){
        this.id=id;
        this.title=title;
        this.info=info;
        this.impor=impor;
    }
}

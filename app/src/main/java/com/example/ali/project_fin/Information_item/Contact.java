package com.example.ali.project_fin.Information_item;

/**
 * Created by ali on 4/10/17.
 */

public class Contact
{

    int id;
    String name,email,pass,ques;
    public void setName(String name){
        this.name=name;

    }
    public void setId(int id){
        this.id=id;}
    public int getId(){
        return this.id;
    }
    public void setEmail(String email){
        this.email=email;

    }
    public void setPass(String pass){
        this.pass=pass;}
    public String getPass(){
        return this.pass;
    }
    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.name;
    }
    public void setQues(String ques){
        this.ques=ques;}
    public String getQues(){
        return this.ques;
    }


}

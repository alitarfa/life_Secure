package com.example.ali.project_fin.Information_item;

/**
 * Created by ali on 4/20/17.
 */

public class ItemSms {
    public int id;
    public String message;
    public String number;
    public String status;
    public ItemSms (int id,String message,String number,String status){
        this.id=id;
        this.message=message;
        this.number=number;
        this.status=status;

    }
}

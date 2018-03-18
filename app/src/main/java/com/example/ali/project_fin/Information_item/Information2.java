package com.example.ali.project_fin.Information_item;

/**
 * Created by ali on 10/03/17.
 */

public class Information2 {

    public String fileName;
    public String filePath;
    public byte[] iv;
    public byte[] key;


    public Information2(String fileName, String filePath,byte[] iv,byte[] key) {

        this.fileName = fileName;
        this.filePath = filePath;
        this.iv=iv;
        this.key=key;

    }
}

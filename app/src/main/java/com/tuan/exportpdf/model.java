package com.tuan.exportpdf;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName="bmi")
public class model {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "age")
    private int age;
    @ColumnInfo(name = "height")
    private float height;
    @ColumnInfo(name = "weight")
    private float weight;
    @ColumnInfo(name = "bmi")
    private float bmi;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "picture")
    private String picture;
    @ColumnInfo(name = "date")
    private Date date;


    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public void setAge(int age){
        this.age=age;
    }

    public int getAge(){
        return this.age;
    }

    public void setHeight(float height){
        this.height=height;
    }

    public float getHeight(){
        return this.height;
    }

    public void setWeight(float weight){
        this.weight=weight;
    }

    public float getWeight(){
        return this.weight;
    }

    public void setBmi(float bmi){
        this.bmi=bmi;
    }

    public float getBmi(){
        return this.bmi;
    }

    public void setNote(String note){
        this.note=note;
    }

    public String getNote(){
        return this.note;
    }

    public void setPicture(String picture){
        this.picture=picture;
    }

    public String getPicture(){
        return this.picture;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public Date getDate(){
        return this.date;
    }





}

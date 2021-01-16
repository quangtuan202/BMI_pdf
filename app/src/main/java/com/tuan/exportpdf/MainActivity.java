package com.tuan.exportpdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static modelRoomDB roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomDB = Room.databaseBuilder(getApplicationContext(), modelRoomDB.class, "perfect_bmi").allowMainThreadQueries().build();
        setContentView(R.layout.activity_main);
        EditText edt_name=findViewById(R.id.name);
        EditText edt_age=findViewById(R.id.age);
        EditText edt_height=findViewById(R.id.height);
        EditText edt_weight=findViewById(R.id.weight);
        EditText edt_bmi=findViewById(R.id.bmi);
        EditText edt_note=findViewById(R.id.note);
        EditText edt_picture=findViewById(R.id.picture);
        Button save_btn=findViewById(R.id.save);
        Button query_btn=findViewById(R.id.query);
        Button create_db_btn=findViewById(R.id.create_db);

        save_btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view){

             String name= edt_name.getText().toString();
             int age=Integer.parseInt(String.valueOf(edt_age.getText()));
             float height=Float.parseFloat(String.valueOf(edt_height.getText()));
             float weight=Float.parseFloat(String.valueOf(edt_weight.getText()));
             float bmi=Float.parseFloat(String.valueOf(edt_bmi.getText()));
             String note =edt_note.getText().toString();
             String picture =edt_picture.getText().toString();

             model data=new model();
             data.setName(name);
             data.setAge(age);
             data.setHeight(height);
             data.setWeight(weight);
             data.setBmi(bmi);
             data.setNote(note);
             data.setPicture(picture);
             java.util.Date date=new java.util.Date();
             SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
             String strDate= formatter.format(date);
             data.setDate(date);
             roomDB.modelDao().insertData(data);
             edt_name.setText(null);
             edt_age.setText(null);
             edt_height.setText(null);
             edt_weight.setText(null);
             edt_bmi.setText(null);
             edt_note.setText(null);
             edt_picture.setText(null);
             Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
         }
     });

        create_db_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                for(int i=0;i<100;i++) {
                    model data = new model();
                    data.setName(String.valueOf(i));
                    data.setAge(i);
                    data.setHeight(i);
                    data.setWeight(i);
                    data.setBmi(i);
                    data.setNote(String.valueOf(i));
                    data.setPicture(String.valueOf(i));
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = formatter.format(date);
                    data.setDate(date);
                    roomDB.modelDao().insertData(data);
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        query_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent);
            }
        });
        
    }

}
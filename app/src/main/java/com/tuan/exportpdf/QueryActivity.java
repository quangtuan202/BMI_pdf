package com.tuan.exportpdf;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryActivity extends AppCompatActivity {
    List<model> dataList=new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    Button btn_export_to_pdf;
    Button btn_pdf_new;
    Button btn_open;
    Bitmap bmp, scaledBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        dataList = MainActivity.roomDB.modelDao().findAllData();
        for(model data: dataList){
            Log.i("name:",data.getName());
        }
        btn_export_to_pdf=findViewById(R.id.export_to_pdf);
        btn_pdf_new=findViewById(R.id.btn_pdf_new);
        btn_open=findViewById(R.id.btn_open);
        recyclerView = findViewById(R.id.pdf_recycler_view);
        recyclerAdapter=new RecyclerAdapter(this,dataList);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        bmp= BitmapFactory.decodeResource(getResources(),R.drawable.riagon_logo);
        scaledBitmap=Bitmap.createScaledBitmap(bmp,58,15,false);

        btn_export_to_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*PdfGenerator.getBuilder()
                        .setContext(QueryActivity.this)
                        .fromViewIDSource()
                        .fromViewID(QueryActivity.this,R.id.pdf_recycler_view,R.id.pdf_recycler_view)
                        .setCustomPageSize(3000,3000)
                        .setFileName("Test-PDF")
                        .setFolderName("Test-PDF-folder")
                        .openPDFafterGeneration(true)
                        .build(new PdfGeneratorListener() {
                            @Override
                            public void onFailure(FailureResponse failureResponse) {
                                super.onFailure(failureResponse);
                            }

                            @Override
                            public void showLog(String log) {
                                super.showLog(log);
                            }

                            @Override
                            public void onSuccess(SuccessResponse response) {
                                super.onSuccess(response);
                            }
                        });*/
                PdfGenerator.getBuilder()
                        .setContext(QueryActivity.this)
                        .fromViewIDSource()
                        .fromViewID(QueryActivity.this,R.id.title,R.id.print_date)
                        /* "fromViewID()" takes array of view ids those MUST BE and MUST BE contained in the inserted "activity" .
                         * You can also invoke "fromViewIDList()" method here which takes list of view ids instead of array. */
                        .setCustomPageSize(3000,3000)
                        /* Here I used ".setCustomPageSize(3000,3000)" to set custom page size.*/
                        .setFileName("Test-PDF")
                        .setFolderName("Test-PDF-folder")
                        .openPDFafterGeneration(true)
                        .build(new PdfGeneratorListener() {
                            @Override
                            public void onFailure(FailureResponse failureResponse) {
                                super.onFailure(failureResponse);
                            }

                            @Override
                            public void showLog(String log) {
                                super.showLog(log);
                            }

                            @Override
                            public void onSuccess(SuccessResponse response) {
                                super.onSuccess(response);
                            }
                        });

                Toast.makeText(QueryActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        btn_pdf_new.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PdfDocument myPdfDocument=new PdfDocument();
                Paint myPaint=new Paint();
                PdfDocument.PageInfo myPageInfo1= new PdfDocument.PageInfo.Builder(400,600,1).create();
                PdfDocument.Page myPage1=myPdfDocument.startPage(myPageInfo1);
                Canvas canvas=myPage1.getCanvas();

                Date date=new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String strDate= formatter.format(date);

                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTextSize(20.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("PERFECT BMI",myPageInfo1.getPageWidth()/2,30,myPaint);

                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(false);
                canvas.drawText("Riagon Technology, Phu Ly, Ha Nam, Vietnam",myPageInfo1.getPageWidth()/2,45,myPaint);
                canvas.drawBitmap(scaledBitmap,1,1,myPaint);

                myPaint.setTextAlign(Paint.Align.CENTER);
                myPaint.setTextSize(15.0f);
                myPaint.setFakeBoldText(true);
                //myPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD)); // set Bold
                canvas.drawText("BMI REPORT",myPageInfo1.getPageWidth()/2,70,myPaint);

                String[] reportFieldTitle={"Print date: ","Name: ","Age: ","Height: ","Current BMI: ","Target Weight: ","Target Date: "};
                String[] reportFieldValue={strDate,"None","None","None","None","None","None"};
                // Create report infor
                for(int i=0;i<reportFieldValue.length;i++){
                    int margin=70;
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText(reportFieldTitle[i]+reportFieldValue[i],16,margin+(i+1)*20,myPaint);

                }

                // Create table header : first line
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setStrokeWidth(1.5f);
                myPaint.setFakeBoldText(true);
                canvas.drawLine(16,220,myPageInfo1.getPageWidth()-16,220,myPaint);

                // Create table header : second line
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setStrokeWidth(1.5f);
                myPaint.setFakeBoldText(true);
                canvas.drawLine(16,250,myPageInfo1.getPageWidth()-16,250,myPaint);

                // Create table header : No
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("No",22,240,myPaint);

                // Create table header : Change
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("Change",60,240,myPaint);

                // Create table header : Weight
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("Weight",120,240,myPaint);

                // Create table header : Date
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("Date",190,240,myPaint);

                // Create table header : BMI
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("BMI",280,240,myPaint);

                // Create table header : Note
                myPaint.setTextAlign(Paint.Align.LEFT);
                myPaint.setTextSize(10.0f);
                myPaint.setFakeBoldText(true);
                canvas.drawText("Note",340,240,myPaint);

                // Draw lines and data
                for(int i=0;i<10;i++){ // first page 10 rows only
                    int margin =250;
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setStrokeWidth(1.5f);
                    myPaint.setFakeBoldText(true);
                    canvas.drawLine(16,margin+(i+1)*30,myPageInfo1.getPageWidth()-16,margin+(i+1)*30,myPaint);

                    // No
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText(String.valueOf(dataList.get(i).getId()),22,margin-10+(i+1)*30,myPaint);

                    // Change
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText("None",60,margin-10+(i+1)*30,myPaint);

                    // Weight
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText(String.valueOf(dataList.get(i).getWeight()),120,margin-10+(i+1)*30,myPaint);

                    // Date
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText(String.valueOf(formatter.format(dataList.get(i).getDate())),190,margin-10+(i+1)*30,myPaint);

                    // BMI
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText(String.valueOf(dataList.get(i).getBmi()),280,margin-10+(i+1)*30,myPaint);

                    // Note
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(10.0f);
                    myPaint.setFakeBoldText(false);
                    canvas.drawText(String.valueOf(dataList.get(i).getNote()),340,margin-10+(i+1)*30,myPaint);

                }
                myPdfDocument.finishPage(myPage1);

                // Count number of pages from 2nd page and print
                int numberOfPages;
                if((dataList.size()-10)<0){
                    numberOfPages=0;
                }
                else if((dataList.size()-10)%15>0){
                    numberOfPages=(dataList.size()-10)/15+1;
                }
                else {
                    numberOfPages= (dataList.size()-10)/15;
                }

                // Start printing from second page
                if (numberOfPages==0){ // if data size <10, create no more page
                    return;
                }
                else{
                    for(int i=0;i<numberOfPages;i++) {
                        // Create attributes for each page
                        PdfDocument.PageInfo myPageAfterFirstInfo = new PdfDocument.PageInfo.Builder(400, 600, i + 2).create();
                        PdfDocument.Page myPageAfterFirst = myPdfDocument.startPage(myPageAfterFirstInfo);
                        Canvas canvasAfterFirst = myPageAfterFirst.getCanvas();

                        //Create content for each page
                        //for (int k = 1; k < numberOfPages + 1; k++) {
                            // Create table header : first line
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setStrokeWidth(1.5f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawLine(16, 20, myPageAfterFirstInfo.getPageWidth() - 16, 20, myPaint);

                            // Create table header : second line
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setStrokeWidth(1.5f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawLine(16, 50, myPageAfterFirstInfo.getPageWidth() - 16, 50, myPaint);

                            // Create table header : No
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawText("No", 22, 40, myPaint);

                            // Create table header : Change
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawText("Change", 60, 40, myPaint);

                            // Create table header : Weight
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawText("Weight", 120, 40, myPaint);

                            // Create table header : Date
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawText("Date", 190, 40, myPaint);

                            // Create table header : BMI
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawText("BMI", 280, 40, myPaint);

                            // Create table header : Note
                            myPaint.setTextAlign(Paint.Align.LEFT);
                            myPaint.setTextSize(10.0f);
                            myPaint.setFakeBoldText(true);
                            canvasAfterFirst.drawText("Note", 340, 40, myPaint);

                            // Draw lines and data
                            for (int j = i*15+10; j < i*15+25; j++) {
                                if(j<dataList.size()) {
                                    int margin = 50;
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setStrokeWidth(1.5f);
                                    myPaint.setFakeBoldText(true);
                                    canvasAfterFirst.drawLine(16, margin + CountValue.getCount() * 30, myPageAfterFirstInfo.getPageWidth() - 16, margin + CountValue.getCount() * 30, myPaint);

                                    // No
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setFakeBoldText(false);
                                    canvasAfterFirst.drawText(String.valueOf(dataList.get(j).getId()), 22, margin - 10 + CountValue.getCount() * 30, myPaint);

                                    // Change
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setFakeBoldText(false);
                                    canvasAfterFirst.drawText("None", 60, margin - 10 + CountValue.getCount() * 30, myPaint);

                                    // Weight
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setFakeBoldText(false);
                                    canvasAfterFirst.drawText(String.valueOf(dataList.get(j).getWeight()), 120, margin - 10 + CountValue.getCount() * 30, myPaint);

                                    // Date
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setFakeBoldText(false);
                                    canvasAfterFirst.drawText(String.valueOf(formatter.format(dataList.get(j).getDate())), 190, margin - 10 + CountValue.getCount() * 30, myPaint);

                                    // BMI
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setFakeBoldText(false);
                                    canvasAfterFirst.drawText(String.valueOf(dataList.get(j).getBmi()), 280, margin - 10 + CountValue.getCount() * 30, myPaint);

                                    // Note
                                    myPaint.setTextAlign(Paint.Align.LEFT);
                                    myPaint.setTextSize(10.0f);
                                    myPaint.setFakeBoldText(false);
                                    canvasAfterFirst.drawText(String.valueOf(dataList.get(j).getNote()), 340, margin - 10 + CountValue.getCount() * 30, myPaint);
                                    Log.d("J=", String.valueOf(j));
                                    Log.d("Count", String.valueOf(CountValue.getCount()));
                                    if (CountValue.getCount() == 15) {
                                        CountValue.resetCount();
                                    }
                                    CountValue.increase();

                                }


                            }
                            myPdfDocument.finishPage(myPageAfterFirst);
                        //}
                    }
                }
                Log.d("H","true final");
                String path= String.valueOf(getExternalFilesDir(null));
                //Date date = new Date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                String fileName="BMI_Report"+dateFormat.format(date)+".pdf";
                File file = new File(getExternalFilesDir(null),fileName);

                try{
                    myPdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e){
                    e.printStackTrace();
                }
                myPdfDocument.close();

                Toast.makeText(QueryActivity.this, path, Toast.LENGTH_SHORT).show();

            }


        });



        btn_open.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){




            }
        });
    }

}
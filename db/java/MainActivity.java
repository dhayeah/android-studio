package com.example.database1;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    RadioGroup rg;
    RadioButton brand;
    Button add;
    int flag=0;
    EditText productid;
    EditText productPrice;
    EditText description;
    DataBaseHelper db;
    Button show,create;
    public String id,des,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create=findViewById(R.id.createdb);
        db= new DataBaseHelper(MainActivity.this);

        create.setOnClickListener(v -> {
            EditText dbname = findViewById(R.id.dbname);
            String dbs=dbname.getText().toString();
            db= new DataBaseHelper(MainActivity.this,dbs);
            flag=1;
        });
        //db=(DataBaseHelper) getIntent().getSerializableExtra("obj");
        rg=findViewById(R.id.brandgrp);
        add=findViewById(R.id.add);
        Spinner spinner= findViewById(R.id.name);
        spinner.setOnItemSelectedListener(this);
        List<String> type = new ArrayList<>();
        type.add("Product 1");
        type.add("Product 2");
        type.add("Product 3");
        type.add("Product 4");
        ArrayAdapter<String> dataAd= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        dataAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAd);

        //retrieve data
        productid=findViewById(R.id.prod_id);
        description=findViewById(R.id.desc_s);
        productPrice=findViewById(R.id.prod_price);
        RadioButton group1,group2;
        group1 = findViewById(R.id.brand1);
        group2= findViewById(R.id.brand2);
        String groupType="";
        groupType+=(group1.isChecked())?"Group 1":(group2.isChecked())?"Group 2":"";
        String productname=spinner.getSelectedItem().toString();


        String finalGroupType = groupType;
        add.setOnClickListener(v -> {
            int selected = rg.getCheckedRadioButtonId();
            brand= findViewById(selected);
            //Toast.makeText(create.this,brand.getText(),Toast.LENGTH_SHORT).show();
            id=productid.getText().toString();
            des=description.getText().toString();
            price=productPrice.getText().toString();
            if(id.length()==4){
                boolean insertvalue = db.insertData(id,productname, finalGroupType,des,price);
                System.out.println(id+productname+finalGroupType+des+price);
                if (insertvalue){
                    Toast.makeText(MainActivity.this,"Inserted....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"Some error occur..",Toast.LENGTH_SHORT).show();
                }}
            else{
                Toast.makeText(MainActivity.this,"Id constraint not satisfied....",Toast.LENGTH_SHORT).show();
            }
        });
        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id3=findViewById(R.id.prod_id3);
                EditText price=findViewById(R.id.updateprice);
                String ids=id3.getText().toString();
                String ps=price.getText().toString();
                db.updatedata(ids,ps);
            }
        });
        Button delete=findViewById(R.id.deleterow);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id4=findViewById(R.id.prod_id4);
                String id4s = id4.getText().toString();
                db.deleterow(id4s);
            }
        });
        show =findViewById(R.id.showdata);
        show.setOnClickListener(v -> {
            EditText brand = findViewById(R.id.showbrand);
            String bs=brand.getText().toString();
            Cursor data = db.getAlldata();
            if (data.getCount()==0){
                showmsg("Empty","No data found");
                return;
            }

            StringBuilder buff = new StringBuilder();
            while(data.moveToNext()){
                String temp=data.getString(2).toLowerCase();
                String temp2=bs.toLowerCase();
                System.out.println(temp+temp2);
                if(temp.equals(temp2)){
                    buff.append("ID :").append(data.getString(0)).append("\n");
                    buff.append("Product Name :").append(data.getString(1)).append("\n");
                    buff.append("Product Brand :").append(data.getString(2)).append("\n");
                    buff.append("Description :").append(data.getString(3)).append("\n");
                    buff.append("Product Price :").append(data.getString(4)).append("\n\n");
                }
            }
            showmsg(bs+" rows :",buff.toString());

        });
        Button showrow = findViewById(R.id.showrow);
        showrow.setOnClickListener(v -> {
            EditText pid = findViewById(R.id.prod_id2);
            String id2 = pid.getText().toString();
            Cursor data = db.getAlldata();
            if (data.getCount()==0){
                showmsg("Empty","No data found");
                return;
            }

            StringBuilder buff = new StringBuilder();
            while(data.moveToNext()){
                System.out.println(data.getString(0)+id2);
                if (data.getString(0).equals(id2)){
                    buff.append("ID :").append(data.getString(0)).append("\n");
                    buff.append("Product Name :").append(data.getString(1)).append("\n");
                    buff.append("Product Brand :").append(data.getString(2)).append("\n");
                    buff.append("Description :").append(data.getString(3)).append("\n");
                    buff.append("Product Price :").append(data.getString(4)).append("\n\n");
                }
            }
            showmsg("Rows",buff.toString());
        });
    }
    public void showmsg(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),"Selected: "+item,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
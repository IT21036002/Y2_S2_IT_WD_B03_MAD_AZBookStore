package com.example.booksmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RentBookActivity extends AppCompatActivity {

    String name,price,img;
    TextView bookName,bookPrice;
    ImageView rentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_book);

        bookName = findViewById(R.id.bookname);
        bookPrice = findViewById(R.id.bookprice);
        rentImage = findViewById(R.id.rentbookimage);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        img = intent.getStringExtra("img");


        bookName.setText(name);
        bookPrice.setText(price);
        Picasso.get().load(img).into(rentImage);

    }

    public void cancel(View view) {
        Intent intent = new Intent(RentBookActivity.this, CustomerDashboard.class);
        startActivity(intent);
    }

    public void rentNow(View view) {
        Intent intent = new Intent(RentBookActivity.this, PaymentActivity.class);
        intent.putExtra("amount",200);
        startActivity(intent);
    }
}
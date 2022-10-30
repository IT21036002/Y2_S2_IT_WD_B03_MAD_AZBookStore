package com.example.booksmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import android.graphics.Color;

public class PaymentActivity extends AppCompatActivity {

    EditText name,cardNumber,month,year,cvc;
    FirebaseDatabase firebaseDatabase;
    TextView amount;
    String tamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        firebaseDatabase = FirebaseDatabase.getInstance();

        name = findViewById(R.id.userName);
        cardNumber = findViewById(R.id.cardNumber);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        cvc = findViewById(R.id.cvc);
        amount = findViewById(R.id.view_amount);

        Intent intent = getIntent();
        tamount = intent.getStringExtra("amount");
        amount.setText(tamount);

    }

    public void paynow(View view) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("card_number",cardNumber.getText().toString());
        map.put("month",month.getText().toString());
        map.put("year",year.getText().toString());
        map.put("cvc",cvc.getText().toString());
        map.put("amount",tamount);

        firebaseDatabase.getReference().child("payment").push().setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PaymentActivity.this, "Payment is Successful", Toast.LENGTH_SHORT).show();
                        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Payment is success")
                                .setContentText(" Order will receive as soon as possible.")
                                .show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PaymentActivity.this, "Payment is Fail", Toast.LENGTH_SHORT).show();
                        new SweetAlertDialog(PaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Payment is Failed")
                                .setContentText(" Please check your connection.")
                                .show();
                    }
                });
    }

    public void cancel(View view) {
        Intent intent = new Intent(PaymentActivity.this, OrderedBookList.class);
        startActivity(intent);
    }
}
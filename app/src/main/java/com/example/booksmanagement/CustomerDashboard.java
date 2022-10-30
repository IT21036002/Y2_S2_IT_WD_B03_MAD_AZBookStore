package com.example.booksmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksmanagement.Models.BookDetails;
import com.example.booksmanagement.Models.OrderedItems;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerDashboard extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button button;
    ListView listView;
    List<BookDetails> user;
    DatabaseReference ref;
    String upprice;
    String pricee;
    String[] number = { "Select Quantity", "1", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        listView = (ListView) findViewById(R.id.listview);
        button = (Button) findViewById(R.id.addProduct);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, OrderedBookList.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("BooksDetails");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    BookDetails orderDetails = studentDatasnap.getValue(BookDetails.class);
                    user.add(orderDetails);
                }

                MyAdapter adapter = new MyAdapter(CustomerDashboard.this, R.layout.custom_books, (ArrayList<BookDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    static class ViewHolder {

        ImageView imageView;
        TextView COL1;
        TextView COL2;
        Button button,rentbutton;
    }

    class MyAdapter extends ArrayAdapter<BookDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<BookDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<BookDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_books, null);

                holder.COL1 = (TextView) view.findViewById(R.id.item_name);
                holder.COL2 = (TextView) view.findViewById(R.id.item_price);
                holder.imageView = (ImageView) view.findViewById(R.id.item_image);
                holder.button = (Button) view.findViewById(R.id.ordernow);
                holder.rentbutton = (Button) view.findViewById(R.id.rent);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Book Name :- "+user.get(position).getBookName());
            holder.COL2.setText("Book Price :- "+user.get(position).getBookPrice());
            Picasso.get().load(user.get(position).getImage()).into(holder.imageView);
            System.out.println(holder);

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view = inflater.inflate(R.layout.custom_order_item_user, null);
                    dialogBuilder.setView(view);

                    final TextView textView1 = (TextView) view.findViewById(R.id.item_id);
                    final TextView textView2 = (TextView) view.findViewById(R.id.item_namee);
                    final TextView textView3 = (TextView) view.findViewById(R.id.item_pricee);
                    final ImageView imageView1 = (ImageView) view.findViewById(R.id.item_imagee);
                    final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
                    final Button buttonAdd = (Button) view.findViewById(R.id.itemuordernow);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BooksDetails").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("bookName").getValue();
                            pricee = (String) snapshot.child("bookPrice").getValue();
                            upprice = (String) snapshot.child("bookPrice").getValue();
                            String image = (String) snapshot.child("image").getValue();

                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(pricee);
                            Picasso.get().load(image).into(imageView1);

                            spinner.setOnItemSelectedListener(CustomerDashboard.this);

                            // Create the instance of ArrayAdapter
                            // having the list of courses
                            ArrayAdapter ad = new ArrayAdapter(CustomerDashboard.this, android.R.layout.simple_spinner_item, number);

                            // set simple layout resource file
                            // for each item of spinner
                            ad.setDropDownViewResource(
                                    android.R.layout
                                            .simple_spinner_dropdown_item);

                            // Set the ArrayAdapter (ad) data on the
                            // Spinner which binds data to spinner
                            spinner.setAdapter(ad);

                            buttonAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");

                                    final String qty = spinner.getSelectedItem().toString();

                                    String image = snapshot.child("image").getValue().toString();

                                        Integer qtyval = Integer.valueOf(qty);
                                        String id = textView1.getText().toString();
                                        String name = textView2.getText().toString();
                                        Integer price = Integer.valueOf(upprice);

                                        Integer tax = (price*2) / 100 ;
                                        Integer total = price+tax;
                                        String finalval = String.valueOf(total * qtyval);

                                        String key = ref.push().getKey();

                                        OrderedItems orderedItems = new OrderedItems(key,name, pricee ,finalval,qty,image);
                                        reference.child(key).setValue(orderedItems);

                                        Toast.makeText(CustomerDashboard.this, "Successfully added", Toast.LENGTH_SHORT).show();

                                        alertDialog.dismiss();


                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }

            });

            holder.rentbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CustomerDashboard.this, RentBookActivity.class);
                    intent.putExtra("name",user.get(position).getBookName());
                    intent.putExtra("price",user.get(position).getBookPrice());
                    intent.putExtra("img",user.get(position).getImage());
                    startActivity(intent);
                }
            });

            return view;

        }
    }
}
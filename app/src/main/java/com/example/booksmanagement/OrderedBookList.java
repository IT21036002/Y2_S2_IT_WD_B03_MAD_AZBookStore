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
import java.util.Map;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderedBookList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String totalAmount;
    ListView listView;
    List<OrderedItems> user;
    DatabaseReference ref;
    String idd;
    String upprice;
    String[] number = { "New Quantity", "1", "2", "3", "4", "5"};
    TextView Tamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_book_list);

        listView = (ListView) findViewById(R.id.orderlist);
        Tamount = findViewById(R.id.tamount);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Orders");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();
                int sum=0;

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    OrderedItems orderedItems = studentDatasnap.getValue(OrderedItems.class);
                    user.add(orderedItems);

                    Map<String, Object> map = (Map<String, Object>) studentDatasnap.getValue();
                    Object price = map.get("updatePrice");
                    int pValue = Integer.parseInt(String.valueOf(price));
                    sum += pValue;

                    Tamount.setText(String.valueOf(sum)+".00");
                    totalAmount = String.valueOf(sum)+".00 LKR";

                }

                MyAdapter adapter = new MyAdapter(OrderedBookList.this, R.layout.custom_ordered_books, (ArrayList<OrderedItems>) user);
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

    public void gotopay(View view) {
        Intent intent = new Intent(OrderedBookList.this, PaymentActivity.class);
        intent.putExtra("amount",totalAmount);
        startActivity(intent);
    }

    static class ViewHolder {

        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
//        Button button3;
        TextView COL1;
        TextView COL2;
        TextView COL3;
//        TextView COL4;
//        TextView COL5;
//        TextView COL6;
//        TextView COL7;
//        TextView COL8;
    }

    class MyAdapter extends ArrayAdapter<OrderedItems> {
        LayoutInflater inflater;
        Context myContext;
        List<OrderedItems> user;


        public MyAdapter(Context context, int resource, ArrayList<OrderedItems> objects) {
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
                view = inflater.inflate(R.layout.custom_ordered_books, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.price);
                holder.COL3 = (TextView) view.findViewById(R.id.qty);
                holder.imageView1 = (ImageView) view.findViewById(R.id.bookimg);
                holder.imageView2 = (ImageView) view.findViewById(R.id.edit);
                holder.imageView3 = (ImageView) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getPrice()+" LKR");
            holder.COL3.setText("Quantity:- "+user.get(position).getQty());
            Picasso.get().load(user.get(position).getImage()).into(holder.imageView1);
            System.out.println(holder);

            idd = user.get(position).getId();

//            holder.button3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//                            .setTitle("Select your Payment Method?")
//                            .setPositiveButton("Pay Online", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    Intent intent = new Intent(OrderedBookList.this, CustomerAccountCard.class);
//                                    intent.putExtra("id", user.get(position).getId());
//                                    intent.putExtra("name", user.get(position).getName());
//                                    intent.putExtra("image", user.get(position).getImage());
//                                    startActivity(intent);
//
//                                }
//                            })
//
//                            .setNegativeButton("Chash On Delivery", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            })
//                            .show();
//
//                }
//            });

            holder.imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("Orders").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_orders, null);
                    dialogBuilder.setView(view1);

                    final TextView textView1 = (TextView) view1.findViewById(R.id.updateId);
                    final TextView textView2 = (TextView) view1.findViewById(R.id.updateName);
                    final TextView textView3 = (TextView) view1.findViewById(R.id.updateprice);
                    final TextView textView4 = (TextView) view1.findViewById(R.id.updateUqty);
                    final ImageView imageView1 = (ImageView) view1.findViewById(R.id.updateimage);
                    final Spinner spinner = (Spinner) view1.findViewById(R.id.spinner);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String price = (String) snapshot.child("price").getValue();
                            upprice = (String) snapshot.child("updatePrice").getValue();
                            String qty = (String) snapshot.child("qty").getValue();
                            String image = (String) snapshot.child("image").getValue();


                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(price);
                            textView4.setText(qty);
                            Picasso.get().load(image).into(imageView1);

                            spinner.setOnItemSelectedListener(OrderedBookList.this);

                            // Create the instance of ArrayAdapter
                            // having the list of courses
                            ArrayAdapter ad = new ArrayAdapter(OrderedBookList.this, android.R.layout.simple_spinner_item, number);

                            // set simple layout resource file
                            // for each item of spinner
                            ad.setDropDownViewResource(
                                    android.R.layout
                                            .simple_spinner_dropdown_item);

                            // Set the ArrayAdapter (ad) data on the
                            // Spinner which binds data to spinner
                            spinner.setAdapter(ad);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String qty = spinner.getSelectedItem().toString();
                            Integer qtyy = Integer.valueOf(qty);
                            Integer price = Integer.valueOf(textView3.getText().toString());

                                HashMap map = new HashMap();
                                map.put("qty", qty);
                                String finalval = String.valueOf(price*qtyy);
                                map.put("updatePrice",finalval);
                                reference.updateChildren(map);

                                Toast.makeText(OrderedBookList.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                new SweetAlertDialog(OrderedBookList.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Cart Updated Successful")
                                    .show();
                                alertDialog.dismiss();

                        }
                    });
                }
            });

            return view;

        }
    }
}
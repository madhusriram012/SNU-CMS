package com.example.snucms.tuckshop;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;
import com.example.snucms.firebaseHelper;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;

public class AddOrder extends AppCompatActivity {

    ArrayList<String> shops = new ArrayList<String>(){{
        add("Navin Tea Stall");
        add("Kaathi Rolls");
        add("Anna's cafe");
    }};
    String[][] items = {
        {"Badam milk", "Tea", "Coffee"},
        {"Aloo roll", "Cheese roll", "Paneer roll"},
        {"Idly", "Plain Dosa", "Masala Dosa", "Filter coffee"}
    };
    String currentShop, orderString = "";

    HashMap<String, Integer> orders = new HashMap<>();

    ItemViewAdapter itemViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));

        Spinner spinnerShop = findViewById(R.id.spinnerShop);
        ArrayAdapter<String> adapter = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                shops.toArray()
        );
        spinnerShop.setAdapter(adapter);
        spinnerShop.setSelection(0);
        spinnerShop.setPrompt("Select");
        currentShop = spinnerShop.getSelectedItem().toString();
        for(int i=0;i<items[0].length;i++){
            orders.put(items[0][i], 0);
        }
        spinnerShop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if(!shops.get(pos).equals(currentShop)) {
                    orders.clear();
                    for(int i=0;i<items[pos].length;i++){
                        orders.put(items[pos][i], 0);
                    }
                    itemViewAdapter.updateLists();
                    itemViewAdapter.notifyDataSetChanged();
                    currentShop = shops.get(pos);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        itemViewAdapter = new ItemViewAdapter(this, orders);
        recyclerView.setAdapter(itemViewAdapter);
        itemViewAdapter.notifyDataSetChanged();

        Button btnPlaceOrder, btnCancel;
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnCancel = findViewById(R.id.btnCancel);

        btnPlaceOrder.setOnClickListener(view -> {
            OrderClass orderClass = new OrderClass();
            orderClass.shop = currentShop;
            orderClass.genTime = Timestamp.now();
            orderClass.delivered = false;
            ArrayList<String> temp = new ArrayList<>();
            orders.forEach((s, integer) -> temp.add(s + " - " + integer));
            orderClass.order = temp;
            firebaseHelper.addOrder(orderClass);
            finish();
        });

        btnCancel.setOnClickListener(view -> finish());
    }

}

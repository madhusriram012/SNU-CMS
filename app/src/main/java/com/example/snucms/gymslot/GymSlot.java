package com.example.snucms.gymslot;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;
import com.example.snucms.firebaseHelper;

import java.util.ArrayList;

public class GymSlot extends AppCompatActivity {

    public static ArrayList<SlotClass> allSlots = new ArrayList<>();
    public static SlotViewAdapter slotViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_slot);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));


        RecyclerView recyclerView = findViewById(R.id.recyclerViewSlot);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        slotViewAdapter = new SlotViewAdapter(this, allSlots);
        recyclerView.setAdapter(slotViewAdapter);
        firebaseHelper.setSlotListener();

    }
}

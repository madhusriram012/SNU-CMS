package com.example.snucms.tuckshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewAdapter.ItemViewHolder> {

    Context context;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<Integer> integerArrayList = new ArrayList<>();
    HashMap<String, Integer> hashMap;

    public ItemViewAdapter(Context context, HashMap<String, Integer> hashMap) {
        this.context = context;
        hashMap.forEach((s, integer) -> {
            arrayList.add(s);
            integerArrayList.add(integer);
        });
        this.hashMap = hashMap;
    }

    public void updateLists() {
        arrayList.clear();
        integerArrayList.clear();
        hashMap.forEach((s, integer) -> {
            arrayList.add(s);
            integerArrayList.add(integer);
        });
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.shop_item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return hashMap.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button btnAddQty, btnSubQty;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewItem);
            btnAddQty = itemView.findViewById(R.id.btnAddQty);
            btnSubQty = itemView.findViewById(R.id.btnSubQty);
        }

        private void bind(int pos) {
            setText(pos);

            btnAddQty.setOnClickListener(view -> {
                hashMap.put(arrayList.get(pos), integerArrayList.get(pos) + 1);
                integerArrayList.set(pos, hashMap.get(arrayList.get(pos)));
                setText(pos);
            });
            btnSubQty.setOnClickListener(view -> {
                if(integerArrayList.get(pos) > 0){
                    hashMap.put(arrayList.get(pos), integerArrayList.get(pos) - 1);
                    integerArrayList.set(pos, hashMap.get(arrayList.get(pos)));
                    setText(pos);
                }
            });
        }

        private void setText(int pos) {
            String s = arrayList.get(pos) + " - " + integerArrayList.get(pos);
            textView.setText(s);
        }

    }
}

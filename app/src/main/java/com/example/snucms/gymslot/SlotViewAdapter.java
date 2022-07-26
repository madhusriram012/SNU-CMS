package com.example.snucms.gymslot;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;
import com.example.snucms.firebaseHelper;

import java.util.ArrayList;

public class SlotViewAdapter extends RecyclerView.Adapter<SlotViewAdapter.SlotViewHolder> {

    Context context;
    ArrayList<SlotClass> arrayList;

    public SlotViewAdapter(Context context, ArrayList<SlotClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.slot_item,parent,false);
        return new SlotViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        SlotClass slotClass = arrayList.get(position);
        holder.bind(slotClass, context);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class SlotViewHolder extends RecyclerView.ViewHolder{

        TextView slotTitle, textViewTotal, textViewRemaining, textViewBooked, textViewFull;
        Button btnBookSlot, btnCancelSlot;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);
            slotTitle = itemView.findViewById(R.id.slotTitle);
            textViewTotal = itemView.findViewById(R.id.textViewTotal);
            textViewRemaining = itemView.findViewById(R.id.textViewRemaining);
            btnBookSlot = itemView.findViewById(R.id.btnBookSlot);
            btnCancelSlot = itemView.findViewById(R.id.btnCancelSlot);
            textViewBooked = itemView.findViewById(R.id.textViewBooked);
            textViewFull = itemView.findViewById(R.id.textViewFull);
        }

        private void bind(SlotClass slotClass, Context context) {

            slotTitle.setText(slotClass.slotName);

            String s = "Total: " + slotClass.totalSlots;
            textViewTotal.setText(s);

            s = "Remaining: " + slotClass.remainingSlots;
            textViewRemaining.setText(s);

            if(slotClass.remainingSlots > 0 && !slotClass.rollno.contains(firebaseHelper.rollno)) {
                btnBookSlot.setVisibility(View.VISIBLE);
                btnCancelSlot.setVisibility(View.GONE);
                textViewBooked.setVisibility(View.GONE);
                textViewFull.setVisibility(View.GONE);
                btnBookSlot.setOnClickListener(view -> {
                    firebaseHelper.addSlot(slotClass);
                });
            } else if(slotClass.rollno.contains(firebaseHelper.rollno)) {
                btnBookSlot.setVisibility(View.GONE);
                btnCancelSlot.setVisibility(View.VISIBLE);
                textViewBooked.setVisibility(View.VISIBLE);
                textViewFull.setVisibility(View.GONE);
                btnCancelSlot.setOnClickListener(view -> {
                    confirm(slotClass, context);
                });
            } else {
                btnBookSlot.setVisibility(View.GONE);
                btnCancelSlot.setVisibility(View.GONE);
                textViewBooked.setVisibility(View.GONE);
                textViewFull.setVisibility(View.VISIBLE);
            }
        }

        private void confirm(SlotClass slotClass, Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to cancel your slot?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    (dialogInterface, i) -> {
                        firebaseHelper.removeSlot(slotClass);
                        dialogInterface.dismiss();
                    }
            );
            builder.setNegativeButton(
                    "No",
                    (dialogInterface, i) -> dialogInterface.dismiss()
            );
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }

    }

}
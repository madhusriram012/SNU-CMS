package com.example.snucms.callbob;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snucms.R;
import com.example.snucms.firebaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class IssueViewAdapter extends RecyclerView.Adapter<IssueViewAdapter.IssueViewHolder> {

    Context context;
    ArrayList<IssueClass> arrayList;
    int expandedPos = -1;

    public IssueViewAdapter(Context context, ArrayList<IssueClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public IssueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.issue_item,parent,false);

        return new IssueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueViewHolder holder, int position) {

        IssueClass issueClass = arrayList.get(position);
        holder.bind(issueClass, context);

    }

     //private void setUp

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class IssueViewHolder extends RecyclerView.ViewHolder{

        TextView idTextView, titleTextView, descTextView,
                dateView, statusView,
                locationTextView, ackTextView, fixTimeTextView,
                callBobVerifyTextView, studentVerifyTextView;
        LinearLayout linearLayout, linearLayoutOpen, linearLayoutButton;
        Button btnVerify;

        boolean visible = false;

        public IssueViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id);
            titleTextView = itemView.findViewById(R.id.slotTitle);
            descTextView = itemView.findViewById(R.id.desc);

            dateView = itemView.findViewById(R.id.dateView);
            statusView = itemView.findViewById(R.id.statusView);

            locationTextView = itemView.findViewById(R.id.locationTextView);
            ackTextView = itemView.findViewById(R.id.ackTextView);
            fixTimeTextView = itemView.findViewById(R.id.fixTimeTextView);
            callBobVerifyTextView = itemView.findViewById(R.id.callBobVerifyTextView);
            studentVerifyTextView = itemView.findViewById(R.id.studentVerifyTextView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            linearLayoutOpen = itemView.findViewById(R.id.linearLayoutOpen);
            linearLayoutButton = itemView.findViewById(R.id.linearLayoutButton);

            btnVerify = itemView.findViewById(R.id.btnVerify);

        }
        
        private void bind(IssueClass issueClass, Context context) {
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            idTextView.setText(issueClass.id);
            titleTextView.setText(issueClass.title);
            descTextView.setText(issueClass.description);
            descTextView.setMaxLines(1);

            //dateView.setText((issueClass.genTime == null)?"":issueClass.genTime.toString());
            dateView.setText((issueClass.genTime == null)?"":sfd.format(issueClass.genTime.toDate()));
            if(issueClass.studentVerify) {
                statusView.setText(R.string.resolved);
                statusView.setTextColor(Color.GREEN);
            } else {
                statusView.setText(R.string.pending);
                statusView.setTextColor(Color.RED);
            }

            linearLayoutOpen.setVisibility(View.GONE);
            linearLayoutButton.setVisibility(View.GONE);
            //System.out.println((issueClass.genTime != null)?"-----------"+issueClass.genTime.getClass():"");
            String
                    location = "Location: " + issueClass.location,
                    ack = "Acknowleged: " + ((issueClass.ack)?"True":"False"),
                    fixTime = "Time till fix: " + ((issueClass.fixTime == null)?"":sfd.format(issueClass.fixTime.toDate())),
                    callBobVerify = "Verified by Callbob: " + ((issueClass.callBobVerify)?"True":"False"),
                    studentVerify = "Verified by student: " + ((issueClass.studentVerify)?"True":"False");
            locationTextView.setText(location);
            ackTextView.setText(ack);
            fixTimeTextView.setText(fixTime);
            callBobVerifyTextView.setText(callBobVerify);
            studentVerifyTextView.setText(studentVerify);

            itemView.setOnClickListener(
                    view -> {
                        open(issueClass.studentVerify);
                    }
            );

            btnVerify.setOnClickListener(
                    view -> confirm(issueClass, context)
            );
            
        }

        private void open(boolean studentVerify) {
            visible = !visible;
            linearLayoutOpen.setVisibility(
                    (visible)
                            ? View.VISIBLE
                            : View.GONE
            );
            linearLayoutButton.setVisibility(
                    (visible && !studentVerify)
                            ? View.VISIBLE
                            : View.GONE);
            descTextView.setMaxLines((visible)?5:1);
        }

        private void confirm(IssueClass issueClass, Context context) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to confirm that is issue is resolved?");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "Yes",
                    (dialogInterface, i) -> {
                        firebaseHelper.verifyIssue(issueClass.documentReference);
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
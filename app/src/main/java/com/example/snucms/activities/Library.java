package com.example.snucms.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.snucms.R;
import com.example.snucms.firebaseHelper;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

public class Library extends AppCompatActivity {

    CompoundBarcodeView barcodeView;
    //private TextView cameraStatus;
    private ImageView imageNotVerified, imageVerified;
    private EditText editTextToken;
    private boolean isVerified = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.img_1));

        //cameraStatus = findViewById(R.id.cameraStatus);
        imageNotVerified = findViewById(R.id.imageNotVerified);
        imageVerified = findViewById(R.id.imageVerified);
        editTextToken = findViewById(R.id.editTextToken);
        Button btnAddEntry = findViewById(R.id.btnAddEntry);

        TextView studentName, studentRoll, studentNetId;
        studentName = findViewById(R.id.studentName);
        studentRoll = findViewById(R.id.studentRoll);
        studentNetId = findViewById(R.id.studentNetId);

        studentName.setText(firebaseHelper.name);
        studentRoll.setText(firebaseHelper.rollno);
        studentNetId.setText(firebaseHelper.netid);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(Library.this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.setStatusText("");
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                //System.out.println("-----------"+result.getResult().toString());
                if(result.getResult().toString().equals("garbage value")) {
                    imageNotVerified.setVisibility(View.GONE);
                    imageVerified.setVisibility(View.VISIBLE);
                    isVerified = true;
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) { }
        });

        btnAddEntry.setOnClickListener(view -> {
            if(editTextToken.getText().toString().trim().equals("")) {
                editTextToken.setError("Token number cannot be empty");
            } else if(!isVerified) {
                Toast.makeText(this, "User is not verified", Toast.LENGTH_SHORT).show();
            } else {
                firebaseHelper.addLibraryEntry(Integer.parseInt(editTextToken.getText().toString()));
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if(!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                /*cameraStatus.setVisibility(View.VISIBLE);
                cameraStatus.setText("Allow Camera Permission");*/
            }
        }
    }

}

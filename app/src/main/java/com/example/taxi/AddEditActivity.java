package com.example.taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditActivity extends AppCompatActivity {
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private EditText edt_soxe;
    private EditText edt_quangduong;
    private EditText edt_dongia;
    private EditText edt_khuyenmai;
    private Button buttonSave;
    private Button buttonCancel;

    private Taxi_01 taxi;
    private boolean needRefresh;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        edt_soxe=findViewById(R.id.edt_soxe);
        edt_quangduong=findViewById(R.id.edt_quangduong);
        edt_dongia=findViewById(R.id.edt_dongia);
        edt_khuyenmai=findViewById(R.id.edt_khuyenmai);
        buttonSave=findViewById(R.id.buttonSave);
        buttonCancel=findViewById(R.id.buttonCancel);

        buttonSave.setOnClickListener(v -> buttonSaveClicked());
        buttonCancel.setOnClickListener(v -> buttonCancelClicked());

        Intent intent = this.getIntent();
        taxi = (Taxi_01) intent.getSerializableExtra("taxi");

        if(taxi== null)  {
            this.mode = MODE_CREATE;
        } else  {
            this.mode = MODE_EDIT;
            this.edt_soxe.setText(taxi.getSoXe());
            this.edt_quangduong.setText(""+taxi.getQuangDuong());
            this.edt_dongia.setText(""+taxi.getDonGia());
            this.edt_khuyenmai.setText(""+taxi.getKhuyenMai());

        }
    }

    private void buttonCancelClicked() {
        this.onBackPressed();

    }

    private void buttonSaveClicked() {
        SqliteDB_2209 db = new SqliteDB_2209(this);

        String soxe =  edt_soxe.getText().toString();
        float quangduong =  Float.parseFloat(edt_quangduong.getText().toString());
        int dongia =  Integer.parseInt(edt_dongia.getText().toString());
        int khuyenmai =  Integer.parseInt( edt_khuyenmai.getText().toString());

        if(soxe.equals("") || quangduong==0||dongia==0||khuyenmai==0) {
            Toast.makeText(getApplicationContext(),
                    "Please enter data again ", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode == MODE_CREATE ) {
            this.taxi= new Taxi_01(soxe,quangduong, dongia,khuyenmai);
            db.addTaxi(taxi);
        } else  {
            this.taxi.setSoXe(soxe);
            this.taxi.setQuangDuong(quangduong);
            this.taxi.setDonGia(dongia);
            this.taxi.setKhuyenMai(khuyenmai);
            db.updateTaxi(taxi);
        }

        this.needRefresh = true;

        // Back to MainActivity.
        this.onBackPressed();
    }
    @Override
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("DoneedRefresh?", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}

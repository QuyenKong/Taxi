package com.example.taxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvTaxi;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;

    private final List<Taxi_01> arrTaxi = new ArrayList<Taxi_01>();
    private ArrayAdapter<Taxi_01> lvTaxiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Get ListView object from xml
        lvTaxi = findViewById(R.id.listView);

        SqliteDB_2209 db = new SqliteDB_2209(this);
        db.createDefaultTaxisIfNeed();

        List<Taxi_01> list=  db.getAllTaxi();
        arrTaxi.addAll(list);

        lvTaxiAdapter = new Adapter_01(this,R.layout.taxi_item,this.arrTaxi);
        lvTaxi.setAdapter(lvTaxiAdapter);

        registerForContextMenu(this.lvTaxi);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, "View");
        menu.add(0, MENU_ITEM_CREATE , 1, "Create");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Taxi_01 selectedTaxi = (Taxi_01) this.lvTaxi.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Toast.makeText(MainActivity.this,"Cong ne",Toast.LENGTH_LONG).show();

        }
        else if(item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this, AddEditActivity.class);

            // Start AddEditVeTauActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        // Start AddEditVeTauActivity, (with feedback).
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, AddEditActivity.class);
            intent.putExtra("taxi", selectedTaxi);

            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selectedTaxi.getSoXe()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteTaxi(selectedTaxi);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }
    private void deleteTaxi(Taxi_01 taxi) {
        SqliteDB_2209 db = new SqliteDB_2209(this);
        db.deleteTaxi(taxi);
        this.arrTaxi.remove(taxi);
        // Refresh ListView.
        this.lvTaxiAdapter.notifyDataSetChanged();
    }

    // When AddEditVeTauActivity completed, it sends feedback.
    // (If you start it using startActivityForResult ())
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("DoneedRefresh?", true);
            // Refresh ListView
            if (needRefresh) {
                this.arrTaxi.clear();
                SqliteDB_2209 db = new SqliteDB_2209(this);
                List<Taxi_01> list = db.getAllTaxi();
                this.arrTaxi.addAll(list);

                // Notify the data change (To refresh the ListView).
                this.lvTaxiAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"update successfull",Toast.LENGTH_SHORT);
            }
        }
    }
}
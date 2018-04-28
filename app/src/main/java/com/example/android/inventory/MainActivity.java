package com.example.android.inventory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.inventory.data.InventoryDBHelper;

public class MainActivity extends AppCompatActivity {

    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainText = findViewById(R.id.main_text);

        InventoryDBHelper helper = new InventoryDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        InventoryDBHelper.insertRowIntoInventoryTable(db);

        Cursor c = InventoryDBHelper.readDataFromInventoryTable(db);

        String dataRetrieved = "";
        for(int i = 0, j = c.getColumnCount(); i < j; i++){
            dataRetrieved += c.getColumnName(i);
            if(i != j - 1){
                dataRetrieved += " | ";
            }
        }

        dataRetrieved += "\n";

        while(c.moveToNext()){
            for(int i = 0, j = c.getColumnCount(); i < j; i++){
                dataRetrieved += c.getString(i);
                if(i != j - 1){
                    dataRetrieved += " | ";
                }
            }
            dataRetrieved += "\n";
        }

        mainText.setText(dataRetrieved);
    }
}

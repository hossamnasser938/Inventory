package com.example.android.inventory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.inventory.data.InventoryContract;

public class AddProduct extends AppCompatActivity {

    Uri recievedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        final Intent recievedIntent = getIntent();
        recievedUri = recievedIntent.getData();

        if(recievedUri != null){

        }
        else{

        }

        Button orderBtn = findViewById(R.id.see_order_btn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recievedUri != null){
                    /*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + supplier_number));
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    } */
                }

            }
        });
    }
}

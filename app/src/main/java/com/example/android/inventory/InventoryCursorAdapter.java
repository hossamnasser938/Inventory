package com.example.android.inventory;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.app.LoaderManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract;

import java.util.zip.Inflater;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {

        TextView productName = view.findViewById(R.id.product_name);
        TextView productQuantity = view.findViewById(R.id.product_quantity);
        TextView productPrice = view.findViewById(R.id.product_unit_price);
        Button saleBtn = view.findViewById(R.id.sale_button);

        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_QUANTITY));
                if(quantity > 0){
                    quantity--;
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Product._ID));
                    ContentValues values = new ContentValues();
                    values.put(InventoryContract.Product.COLUMN_QUANTITY, quantity);
                    Uri rowUri = ContentUris.withAppendedId(InventoryContract.Product.CONTENT_URI, id);
                    if(view.getContext().getContentResolver().update(rowUri, values, null, null) == 0){
                        Toast.makeText(view.getContext(), R.string.error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_PRODUCT_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_QUANTITY));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_PRICE));

        productName.setText(name);
        productQuantity.setText(String.valueOf(quantity));
        productPrice.setText(String.valueOf(price));
    }

    public void saleBtnClick(View view){
        final Cursor c = getCursor();
        int quantity = c.getInt(c.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_QUANTITY));
        if(quantity > 0){
            quantity--;
            int id = c.getInt(c.getColumnIndexOrThrow(InventoryContract.Product._ID));
            ContentValues values = new ContentValues();
            values.put(InventoryContract.Product.COLUMN_QUANTITY, quantity);
            Uri rowUri = ContentUris.withAppendedId(InventoryContract.Product.CONTENT_URI, id);
            if(view.getContext().getContentResolver().update(rowUri, values, null, null) == 0){
                Toast.makeText(view.getContext(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        }
    }

}

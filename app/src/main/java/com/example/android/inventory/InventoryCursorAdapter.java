package com.example.android.inventory;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.app.LoaderManager;
import android.widget.TextView;

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
    public void bindView(View view, Context context, Cursor cursor) {

        TextView productName = view.findViewById(R.id.product_name);
        TextView productQuantity = view.findViewById(R.id.product_quantity);
        TextView productPrice = view.findViewById(R.id.product_unit_price);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_PRODUCT_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_QUANTITY));
        double price = cursor.getDouble(cursor.getColumnIndexOrThrow(InventoryContract.Product.COLUMN_PRICE));

        productName.setText(name);
        productQuantity.setText(String.valueOf(quantity));
        productPrice.setText(String.valueOf(price));
    }
}

package com.example.android.inventory;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract;

public class AddProduct extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri recievedUri;

    private final static int INVENTORY_LOADER = 0;

    private TextView nameView;
    private TextView quantityView;
    private TextView priceView;
    private TextView supplierNameView;
    private TextView supplierPhoneView;
    private Button addUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        nameView = findViewById(R.id.add_name);
        quantityView = findViewById(R.id.add_quantity);
        priceView = findViewById(R.id.add_unit_price);
        supplierNameView = findViewById(R.id.add_supplier_name);
        supplierPhoneView = findViewById(R.id.add_supplier_phone);
        addUpdateBtn = findViewById(R.id.add_update_btn);

        addUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProduct();
            }
        });

        Intent recievedIntent = getIntent();
        recievedUri = recievedIntent.getData();

        if(recievedUri != null){
            setTitle(R.string.edit_existing);
            addUpdateBtn.setText(R.string.edit_existing);
            getLoaderManager().initLoader(INVENTORY_LOADER, null, this);
        }
        else{
            setTitle(R.string.add_new);
            addUpdateBtn.setText(R.string.add_new);
        }

    }

    private void saveProduct(){

        String nameString = nameView.getText().toString().trim();
        String quantityString = quantityView.getText().toString().trim();
        String priceString = priceView.getText().toString().trim();
        String supplierNameString = supplierNameView.getText().toString().trim();
        String supplierPhoneString = supplierPhoneView.getText().toString().trim();

        if (TextUtils.isEmpty(nameString) && TextUtils.isEmpty(quantityString) &&
                TextUtils.isEmpty(priceString) && TextUtils.isEmpty(supplierNameString) &&
                TextUtils.isEmpty(supplierPhoneString)) {
            return;
        }

        Integer quantity = (TextUtils.isEmpty(quantityString))? null : Integer.parseInt(quantityString);
        Double price = (TextUtils.isEmpty(priceString))? null : Double.parseDouble(priceString);

        ContentValues values = new ContentValues();
        values.put(InventoryContract.Product.COLUMN_PRODUCT_NAME, nameString);
        values.put(InventoryContract.Product.COLUMN_QUANTITY, quantity);
        values.put(InventoryContract.Product.COLUMN_PRICE, price);
        values.put(InventoryContract.Product.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(InventoryContract.Product.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneString);

        if(recievedUri == null){
            try{
                if(getContentResolver().insert(InventoryContract.Product.CONTENT_URI, values) == null){
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.inserted, Toast.LENGTH_SHORT).show();
                }
            }
            catch (IllegalArgumentException e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            try{
                if(getContentResolver().update(recievedUri, values, null, null) == 0){
                    Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.updated, Toast.LENGTH_SHORT).show();
                }
            }
            catch (IllegalArgumentException e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] columns = {InventoryContract.Product._ID,
                InventoryContract.Product.COLUMN_PRODUCT_NAME,
                InventoryContract.Product.COLUMN_QUANTITY,
                InventoryContract.Product.COLUMN_PRICE,
                InventoryContract.Product.COLUMN_SUPPLIER_NAME,
                InventoryContract.Product.COLUMN_SUPPLIER_PHONE_NUMBER};

        return new CursorLoader(this,
                recievedUri,
                columns,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(cursor.getColumnIndex(InventoryContract.Product.COLUMN_PRODUCT_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndex(InventoryContract.Product.COLUMN_QUANTITY));
            double price = cursor.getDouble(cursor.getColumnIndex(InventoryContract.Product.COLUMN_PRICE));
            String supplierName = cursor.getString(cursor.getColumnIndex(InventoryContract.Product.COLUMN_SUPPLIER_NAME));
            String supplierPhone = cursor.getString(cursor.getColumnIndex(InventoryContract.Product.COLUMN_SUPPLIER_PHONE_NUMBER));

            // Update the views on the screen with the values from the database
            nameView.setText(name);
            quantityView.setText(String.valueOf(quantity));
            priceView.setText(String.valueOf(price));
            supplierNameView.setText(supplierName);
            if(TextUtils.isEmpty(supplierPhone)){
                supplierPhoneView.setText("No phone for this supplier");
            }
            else{
                supplierPhoneView.setText(supplierPhone);
            }

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        nameView.setText("");
        quantityView.setText("");
        priceView.setText("");
        supplierNameView.setText("");
        supplierPhoneView.setText("");
    }
}

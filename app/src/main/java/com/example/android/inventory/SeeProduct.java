package com.example.android.inventory;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract;

public class SeeProduct extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Uri recievedUri;

    private final static int INVENTORY_LOADER = 0;

    private TextView nameView;
    private TextView quantityView;
    private TextView priceView;
    private TextView supplierNameView;
    private TextView supplierPhoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_product);

        nameView = findViewById(R.id.see_name);
        quantityView = findViewById(R.id.see_quantity);
        priceView = findViewById(R.id.see_unit_price);
        supplierNameView = findViewById(R.id.see_supplier_name);
        supplierPhoneView = findViewById(R.id.see_supplier_phone);

        Intent intent = getIntent();
        recievedUri = intent.getData();
        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_see_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete:
                showConfirmationDialog();
                break;
            case R.id.edit:
                Intent intent = new Intent(this, AddProduct.class);
                intent.setData(recievedUri);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void showConfirmationDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
        dialog.setTitle("Are you sure you want to delete current product?");
        dialog.setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePet();
                    }
                });
        dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface != null){
                    dialogInterface.dismiss();
                }
            }
        });
    }

    private void deletePet(){
        if(recievedUri != null){
            if(getContentResolver().delete(recievedUri, null, null) == 0){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.deleted), Toast.LENGTH_SHORT);
            }
        }
        finish();
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
            supplierPhoneView.setText(supplierPhone);
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

package com.example.android.inventory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.inventory.data.InventoryContract.Product;
import com.example.android.inventory.data.InventoryDBHelper;

public class MainActivity extends AppCompatActivity {

    private TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InventoryDBHelper helper = new InventoryDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        InventoryDBHelper.insertRowIntoInventoryTable(getApplicationContext(), db);

        Cursor c = InventoryDBHelper.readDataFromInventoryTable(db);
        try {
            //Display the table on the screen
            String dataRetrieved = "";
            for(int i = 0, j = c.getColumnCount(); i < j; i++){
                dataRetrieved += c.getColumnName(i);
                if(i != j - 1){
                    dataRetrieved += " | ";
                }
            }

            dataRetrieved += "\n";

            int idColumnIndex = c.getColumnIndex(Product._ID);
            int productNameColumnIndex = c.getColumnIndex(Product.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = c.getColumnIndex(Product.COLUMN_PRICE);
            int quantityColumnIndex = c.getColumnIndex(Product.COLUMN_QUANTITY);
            int supplierNameColumnIndex = c.getColumnIndex(Product.COLUMN_SUPPLIER_NAME);
            int supplierPhoneNumberColumnIndex = c.getColumnIndex(Product.COLUMN_SUPPLIER_PHONE_NUMBER);

            int currentId;
            String currentProductName;
            double currentPrice;
            int currentQuantity;
            String currentSupplierName;
            String currentSupplierPhoneNumber;

            while(c.moveToNext()){
                currentId = c.getInt(idColumnIndex);
                currentProductName = c.getString(productNameColumnIndex);
                currentPrice = c.getDouble(priceColumnIndex);
                currentQuantity = c.getInt(quantityColumnIndex);
                currentSupplierName = c.getString(supplierNameColumnIndex);
                currentSupplierPhoneNumber = c.getString(supplierPhoneNumberColumnIndex);

                dataRetrieved += "" + currentId + " | " + currentProductName + " | " + currentPrice + " | " + currentQuantity + " | " + currentSupplierName + " | " + currentSupplierPhoneNumber + "\n";
            }

        }
        finally {
            //Get rid of the Cursor object
            c.close();;
        }

    }
}

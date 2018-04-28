package com.example.android.inventory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract.Product;

/**
 * Created by Hosam on 4/28/2018.
 */

public class InventoryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public InventoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table Product SQL statement
        String SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE" + Product.TABLE_NAME + "("
                + Product._ID + InventoryContract.DATA_INTEGER + InventoryContract.CONSTRAINT_PRIMARY_KEY + InventoryContract.CONSTRAINT_AUTOINCREMENT + ", "
                + Product.COLUMN_PRODUCT_NAME + InventoryContract.DATA_TEXT + InventoryContract.CONSTRAINT_NOT_NULL + ", "
                + Product.COLUMN_PRICE + InventoryContract.DATA_REAL + ", "
                + Product.COLUMN_QUANTITY + InventoryContract.DATA_INTEGER + InventoryContract.CONSTRAINT_DEFAULT + " 0" + ", "
                + Product.COLUMN_SUPPLIER_NAME + InventoryContract.DATA_TEXT + ", "
                + Product.COLUMN_SUPPLIER_PHONE_NUMBER + InventoryContract.DATA_TEXT + ");";

        //Execute Create table statement
        db.execSQL(SQL_CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing for now
    }

    public static void insertRowIntoInventoryTable(Context context, SQLiteDatabase db){
        //Prepare Product info
        String productName = "mouse", supplierName = "Hosam", supplierPhoneNumber = "01026501235";
        double price = 15.0;
        int quantity = 10;

        //Put them in ContentValues object
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_PRODUCT_NAME, productName);
        values.put(Product.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(Product.COLUMN_SUPPLIER_PHONE_NUMBER, supplierPhoneNumber);
        values.put(Product.COLUMN_PRICE, price);
        values.put(Product.COLUMN_QUANTITY, quantity);

        //Insert this row in Product table
        if(db.insert(Product.TABLE_NAME, null, values) != -1){
            Toast.makeText(context, "Item Inserted", Toast.LENGTH_SHORT);
        }
        else {
            Toast.makeText(context, "Error Inserting Item", Toast.LENGTH_SHORT);
        }

    }

    public static Cursor readDataFromInventoryTable(SQLiteDatabase db){
        //Specify columns to be read
        String columns[] = {Product._ID,
                Product.COLUMN_PRODUCT_NAME,
                Product.COLUMN_PRICE,
                Product.COLUMN_QUANTITY,
                Product.COLUMN_SUPPLIER_NAME,
                Product.COLUMN_SUPPLIER_PHONE_NUMBER};

        //Query the database
        return db.query(Product.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

}

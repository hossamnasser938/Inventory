package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

}

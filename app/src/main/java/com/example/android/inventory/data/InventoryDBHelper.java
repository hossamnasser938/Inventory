package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.inventory.data.InventoryContract.product;

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
        String SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE" + InventoryContract.Product.TABLE_NAME + "("
                + InventoryContract.Product._ID + InventoryContract.DATA_INTEGER + InventoryContract.CONSTRAINT_PRIMARY_KEY + InventoryContract.CONSTRAINT_AUTOINCREMENT + ", "
                + InventoryContract.Product.COLUMN_PRODUCT_NAME + InventoryContract.DATA_TEXT + InventoryContract.CONSTRAINT_NOT_NULL + InventoryContract.CONSTRAINT_UNIQUE + ", "
                + InventoryContract.Product.COLUMN_PRICE + InventoryContract.DATA_REAL + ", "
                + InventoryContract.Product.COLUMN_QUANTITY + InventoryContract.DATA_INTEGER + InventoryContract.CONSTRAINT_DEFAULT + " 0" + ", "
                + InventoryContract.Product.COLUMN_SUPPLIER_NAME + InventoryContract.DATA_TEXT + ", "
                + InventoryContract.Product.COLUMN_SUPPLIER_PHONE_NUMBER + InventoryContract.DATA_TEXT + ");";

        db.execSQL(SQL_CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.android.inventory.data;

import android.provider.BaseColumns;

/**
 * Created by Hosam on 4/28/2018.
 */

public class InventoryContract {

    public static final String DATA_TEXT = " TEXT";
    public static final String DATA_INTEGER = " INTEGER";
    public static final String DATA_REAL = " REAL";

    public static final String CONSTRAINT_PRIMARY_KEY = " PRIMARY KEY";
    public static final String CONSTRAINT_AUTOINCREMENT = " AUTOINCREMENT";
    public static final String CONSTRAINT_NOT_NULL = " NOT NULL";
    public static final String CONSTRAINT_UNIQUE = " UNIQUE";
    public static final String CONSTRAINT_DEFAULT = " DEFAULT";

    public static final class Product implements BaseColumns{

        public static final String TABLE_NAME = " Product";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "Product_Name";
        public static final String COLUMN_PRICE = "Price";
        public static final String COLUMN_QUANTITY = "Quantity";
        public static final String COLUMN_SUPPLIER_NAME = "Supplier_Name";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "Supplier_Phone_Number";

    }

}

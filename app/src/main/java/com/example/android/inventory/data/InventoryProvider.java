package com.example.android.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class InventoryProvider extends ContentProvider{

    private static final int PRODUCTS = 100;
    private static final int PRODUCT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCT, PRODUCTS);
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCT + "/#", PRODUCT_ID);
    }

    InventoryDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match){
            case PRODUCTS:
                cursor = database.query(InventoryContract.Product.TABLE_NAME,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PRODUCT_ID:
                selection = InventoryContract.Product._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(InventoryContract.Product.TABLE_NAME, columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case PRODUCTS:
                return InventoryContract.Product.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return InventoryContract.Product.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);

        switch (match){
            case PRODUCTS:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }

    }

    private Uri insertProduct(Uri uri, ContentValues values){

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long rowId;

        /*
        Validate data before inserting
         */
        //Validate product name
        String name = values.getAsString(InventoryContract.Product.COLUMN_PRODUCT_NAME);
        if(name == null){
            throw new IllegalArgumentException("Please enter name of the product");
        }

        //Validate product quantity
        Integer quantity = values.getAsInteger(InventoryContract.Product.COLUMN_QUANTITY);
        if(quantity == null){
            quantity = 0;
        }
        else if(quantity < 0){
            throw new IllegalArgumentException("Please enter positive number for quantity");
        }

        //Validate product price
        Double price = values.getAsDouble(InventoryContract.Product.COLUMN_PRICE);
        if(price == null){
            throw new IllegalArgumentException("Please enter price of the product");
        }
        else if(price < 0){
            throw new IllegalArgumentException("Please positive number for price");
        }

        //Validate supplier name
        String supplierName = values.getAsString(InventoryContract.Product.COLUMN_SUPPLIER_NAME);
        if(supplierName == null){
            throw new IllegalArgumentException("Please enter name of the supplier");
        }

        rowId = database.insert(InventoryContract.Product.TABLE_NAME, null, values);
        if(rowId != -1){
            Uri insertedRowUri = ContentUris.withAppendedId(InventoryContract.Product.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(insertedRowUri, null);
            return insertedRowUri;
        }
        return null;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case PRODUCTS:
                rowsDeleted = database.delete(InventoryContract.Product.TABLE_NAME,
                        selection, selectionArgs);
                break;
            case PRODUCT_ID:
                selection = InventoryContract.Product._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(InventoryContract.Product.TABLE_NAME,
                        selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }

        if(rowsDeleted == 0){
            return 0;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case PRODUCTS:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ID:
                selection = InventoryContract.Product._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown Uri " + uri);
        }

    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated;

        /*
        Validate data before inserting
         */
        //Validate product name
        if(values.containsKey(InventoryContract.Product.COLUMN_PRODUCT_NAME)){
            String name = values.getAsString(InventoryContract.Product.COLUMN_PRODUCT_NAME);
            if(name == null){
                throw new IllegalArgumentException("Please enter name of the product");
            }
        }

        //Validate product quantity
        if(values.containsKey(InventoryContract.Product.COLUMN_QUANTITY)){
            Integer quantity = values.getAsInteger(InventoryContract.Product.COLUMN_QUANTITY);
            if(quantity == null){
                quantity = 0;
            }
            else if(quantity < 0){
                throw new IllegalArgumentException("Please enter positive number for quantity");
            }
        }

        //Validate product price
        if(values.containsKey(InventoryContract.Product.COLUMN_PRICE)){
            Double price = values.getAsDouble(InventoryContract.Product.COLUMN_PRICE);
            if(price == null){
                throw new IllegalArgumentException("Please enter price of the product");
            }
            else if(price < 0){
                throw new IllegalArgumentException("Please positive number for price");
            }
        }

        //Validate supplier name
        if(values.containsKey(InventoryContract.Product.COLUMN_SUPPLIER_NAME)){
            String supplierName = values.getAsString(InventoryContract.Product.COLUMN_SUPPLIER_NAME);
            if(supplierName == null){
                throw new IllegalArgumentException("Please enter name of the supplier");
            }
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return database.update(InventoryContract.Product.TABLE_NAME, values, selection, selectionArgs);

    }
}

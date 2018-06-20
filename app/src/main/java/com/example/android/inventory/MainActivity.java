package com.example.android.inventory;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.android.inventory.data.InventoryContract.Product;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int INVENTORY_LOADER = 0;
    InventoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCursorAdapter = new InventoryCursorAdapter(this, null);

        ListView inventorysList = findViewById(R.id.products_list);
        View emptyView = findViewById(R.id.empty_view);

        inventorysList.setEmptyView(emptyView);
        inventorysList.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(INVENTORY_LOADER, null, this);

        inventorysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SeeProduct.class);
                intent.setData(ContentUris.withAppendedId(Product.CONTENT_URI, id));
                startActivity(intent);
            }
        });

        ImageButton addBtn = findViewById(R.id.main_insert_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddProduct.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] columns = {
                Product._ID,
                Product.COLUMN_PRODUCT_NAME,
                Product.COLUMN_QUANTITY,
                Product.COLUMN_PRICE
        };

        return new CursorLoader(this,
                Product.CONTENT_URI,
                columns,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}

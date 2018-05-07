package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

public class ProductCursorAdapter extends CursorAdapter {
    // Content URI for the existing product (null if it's a new product)
    private Uri mCurrentProductUri;

    //constructor
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    //Makes a new blank list item view. No data is set (or bound) to the views yet
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    //This method binds the inventory data
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);

        // If the pet price is empty string or null, then use some default text
        // that says "Unknown price", so the TextView isn't blank.
        if (TextUtils.isEmpty(productPrice)) {
            productPrice = context.getString(R.string.unknown_price);
        }

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        quantityTextView.setText(productQuantity);
        priceTextView.setText(productPrice);


        //call order button when is clicked to decrease quantity
        Button quantityDecreased = (Button) view.findViewById(R.id.order);

        // Setup the item click listener
        quantityDecreased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                //decrease quantity
                productQuantity--;
                // put in content value
                ContentValues values = new ContentValues();
                values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
                int rowsUpdated = getContentResolver().update(mCurrentProductUri, values, null, null);
            }
        });
    }
}

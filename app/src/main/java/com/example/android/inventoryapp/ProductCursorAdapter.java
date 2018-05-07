package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract;

public class ProductCursorAdapter extends CursorAdapter {

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
        String productQuantity = cursor.getString(quantityColumnIndex);

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
        Button quantityDecreased= (Button) view.findViewById(R.id.order);

        // Setup the item click listener
        quantityDecreased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onItemClick(View v) {
                decreaseQuantity();
            }
        });
    }

    //decrease quantity
    private void decreaseQuantity() {

        int currentQuantity = Integer.parseInt(quantityDecreased);
        //crease current quantity by one
        currentQuantity--;

        ContentValues values = new ContentValues();
        values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, currentQuantity);

        int rowsUpdated = getContentResolver().update(mCurrentProductUri, values, null, null);

        // Show a toast message depending on whether or not the decrease was successful or not
        //fail
        if (rowsUpdated == -1) {
            // If the row uri is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.decrease_error), Toast.LENGTH_SHORT).show();
        }
        //success
        else {
            Toast.makeText(this, getString(R.string.decrease_success), Toast.LENGTH_SHORT).show();
        }

    }

}

package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        //call order button when is clicked to decrease quantity
        Button quantityDecreased = (Button) view.findViewById(R.id.order);


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

        //get current quantity from database
        final int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY));
        //for the product clicked
        String currentId = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.ProductEntry._ID));

        final Uri currentUri = ContentUris.withAppendedId(InventoryContract.ProductEntry.CONTENT_URI, Long.parseLong(currentId));

        // Setup the item click listener
        quantityDecreased.setOnClickListener(new View.OnClickListener() {
            //on click
            @Override
            public void onClick(View v) {
                //check to see if quantity is more than 1
                if (currentQuantity > 0) {
                    //decrease quantity by one
                    ContentValues values = new ContentValues();
                    int decreasedQuantity = currentQuantity - 1;
                    values.put(InventoryContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, decreasedQuantity);
                    //update the current value
                    int newUpdate = context.getContentResolver().update(currentUri, values, null, null);
                    if (newUpdate == 0)
                        Toast.makeText(context, R.string.error_in_updating, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.quantity_bigger_then_one, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

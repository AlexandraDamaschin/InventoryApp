package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    //empty constructor
    private InventoryContract() {
    }

    //global variables needed

    //guaranteed to be unique on the device.
    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";

    //use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Possible path (appended to base content URI for possible URI's)
    public static final String PATH_INVENTORY = "inventory";

    //inner class that defines the values for the inventory table
    //each entry is a single product
    public static final class ProductEntry implements BaseColumns {

        // The content URI to access the product data in the provider
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        // The MIME type of the {@link #CONTENT_URI} for a list of products.
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        // The MIME type of the {@link #CONTENT_URI} for a single product.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        //  Name of database table for inventory
        public final static String TABLE_NAME = "inventory";

        //Unique ID number for the inventory (only for use in the database table).
        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        // Name of the product.
        // Type: TEXT
        public final static String COLUMN_PRODUCT_NAME = "name";

        // Price of the product.
        // Type: INTEGER
        public final static String COLUMN_PRODUCT_PRICE = "price";

        // Quantity of the product.
        // Type: INTEGER
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";

        // Supplier Name of the product.
        // Type: TEXT
        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplierName";

        // Supplier Phone Number of the product.
        // Type: TEXT
        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE = "supplierPhone";
    }

}

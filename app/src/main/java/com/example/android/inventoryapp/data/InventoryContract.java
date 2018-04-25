package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

public final class InventoryContract {

    //empty constructor
    private InventoryContract() {
    }

    //inner class that defines the values for the inventory table
    //each entry is a single product
    public static final class ProductEntry implements BaseColumns {

        //  Name of database table for inventory
        public final static String TABLE_NAME = "inventory";

        //Unique ID number for the inventory (only for use in the database table).
        //Type: INTEGER
        public final static String _ID = BaseColumns._ID;

        // Name of the product.
        // Type: TEXT
        public final static String COLUMN_PRODUCT_NAME = "name";

        // Price of the product.
        // Type: int
        public final static String COLUMN_PRODUCT_PRICE = "1";

        // Quantity of the product.
        // Type: int
        public final static String COLUMN_PRODUCT_QUANTITY = "1";

        // Supplier Name of the product.
        // Type: TEXT
        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier name";

        // Supplier Phone Number of the product.
        // Type: int
        public final static String COLUMN_PRODUCT_SUPPLIER_PHONE = "04234";
    }

}

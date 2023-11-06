package com.example.xicocart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProductDAO {
    private Context context;
    private GroceriesDbHelper dbHelper;

    private SQLiteDatabase db;
    public ProductDAO(Context context){
        this.context=context;
        dbHelper=new GroceriesDbHelper(context);
    }
    public boolean insertProduct(Product product){
        boolean result=false;
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(GroceriesContract.Product.COLUMN_NAME_BARCODE,product.getBarcode());
        values.put(GroceriesContract.Product.COLUMN_NAME_DESCRIPTION,product.getDescription());
        values.put(GroceriesContract.Product.COLUMN_NAME_BRAND,product.getBrand());
        values.put(GroceriesContract.Product.COLUMN_NAME_COST,product.getCost());
        values.put(GroceriesContract.Product.COLUMN_NAME_PRICE,product.getPrice());
        values.put(GroceriesContract.Product.COLUMN_NAME_STOCK,product.getStock());

        long newRowId=db.insert(GroceriesContract.Product.TABLE_NAME,null,values);

        if(newRowId!= -1) {
            result=true;
        }
        return result;
    }
    public ArrayList<String> getAllBarcode(){
        ArrayList<String> result=new ArrayList<String>();
        db=dbHelper.getWritableDatabase();
        String[] projection={
                GroceriesContract.Product.COLUMN_NAME_BARCODE
        };

        Cursor cursor=db.query(
                GroceriesContract.Product.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while(cursor.moveToNext()){
            result.add(cursor.getString(0));
        }
        cursor.close();
        return result;
    }

    public Product getProductByBarcode(String barcode){
        Product result=null;

        db=dbHelper.getReadableDatabase();
        String selection=GroceriesContract.Product.COLUMN_NAME_BARCODE + " = ?";
        String[] selectionArgs={barcode};

        Cursor cursor = db.query(
                GroceriesContract.Product.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor.moveToNext()){
            result=new Product();
            result.setBarcode(cursor.getString(0));
            result.setDescription(cursor.getString(1));
            result.setBrand(cursor.getString(2));
            result.setCost(cursor.getFloat(3));
            result.setPrice(cursor.getFloat(4));
            result.setStock(cursor.getInt(5));
        }
        cursor.close();
        return result;
    }

    public boolean updateProduct(Product product) {
        boolean result = false;
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GroceriesContract.Product.COLUMN_NAME_DESCRIPTION, product.getDescription());
        values.put(GroceriesContract.Product.COLUMN_NAME_BRAND, product.getBrand());
        values.put(GroceriesContract.Product.COLUMN_NAME_COST, product.getCost());
        values.put(GroceriesContract.Product.COLUMN_NAME_PRICE, product.getPrice());
        values.put(GroceriesContract.Product.COLUMN_NAME_STOCK, product.getStock());

        String selection = GroceriesContract.Product.COLUMN_NAME_BARCODE + " = ?";
        String[] selectionArgs = {product.getBarcode()};

        int count = db.update(
                GroceriesContract.Product.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            result = true;
        }

        return result;
    }



}

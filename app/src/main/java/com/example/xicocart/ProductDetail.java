package com.example.xicocart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetail extends AppCompatActivity {
    private EditText txtBarcode;
    private EditText txtDescription;
    private EditText txtBrand;
    private EditText txtCost;
    private EditText txtPrice;
    private EditText txtStock;
    private Product product;
    private ProductDAO productDAO;

    private Button buttonUpdate;
    private Button buttonDelete;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtBarcode=findViewById(R.id.txt_barcode);
        txtDescription=findViewById(R.id.txt_description);
        txtBrand=findViewById(R.id.txt_brand);
        txtCost=findViewById(R.id.txt_cost);
        txtPrice=findViewById(R.id.txt_price);
        buttonUpdate=findViewById(R.id.btn_save);
        buttonDelete=findViewById(R.id.btn_delete);
        String barcode=getIntent().getExtras().getString("barcode");
        productDAO=new ProductDAO(this);
        product=productDAO.getProductByBarcode(barcode);


    }
}

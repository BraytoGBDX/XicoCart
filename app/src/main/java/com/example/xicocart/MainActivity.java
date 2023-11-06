package com.example.xicocart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtBarcode;
    private EditText txtDescription;
    private EditText txtBrand;
    private EditText txtCost;
    private EditText txtPrice;
    private EditText txtStock;

    private ListView lvProducts;
    private Button btnSave;

    private Product product;
    private ProductDAO productDAO;
    private ArrayList<String> dataOrigin;

    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBarcode=findViewById(R.id.txt_barcode);
        txtDescription=findViewById(R.id.txt_description);
        txtBrand=findViewById(R.id.txt_brand);
        txtCost=findViewById(R.id.txt_cost);
        txtPrice=findViewById(R.id.txt_price);
        txtStock=findViewById(R.id.txt_stock);
        lvProducts=findViewById(R.id.lv_products);
        btnSave=findViewById(R.id.btn_save);

        productDAO=new ProductDAO(this);
        updateList();

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String barcode=(String) lvProducts.getItemAtPosition(i);
                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("barcode",barcode);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product = new Product();
                product.setBarcode(txtBarcode.getText().toString());
                product.setDescription(txtDescription.getText().toString());
                product.setBrand(txtBrand.getText().toString());
                product.setCost(Float.parseFloat(txtCost.getText().toString()));
                product.setPrice(Float.parseFloat(txtPrice.getText().toString()));
                product.setStock(Integer.parseInt(txtStock.getText().toString()));

                if(productDAO.insertProduct(product)==true){
                    Toast.makeText(MainActivity.this,"Producto almacenado con exito",Toast.LENGTH_SHORT).show();
                    clearFields();
                    updateList();
                }else{
                    Toast.makeText(MainActivity.this,"Servidor no disponible",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    protected void updateList() {
        dataOrigin = productDAO.getAllBarcode();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataOrigin);
        lvProducts.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    protected void clearFields(){
        txtBarcode.setText("");
        txtBrand.setText("");
        txtCost.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtDescription.setText("");

    }

}
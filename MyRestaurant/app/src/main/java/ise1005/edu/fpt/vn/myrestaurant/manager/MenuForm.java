package ise1005.edu.fpt.vn.myrestaurant.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.GetMenuTask;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

public class MenuForm extends AppCompatActivity implements IAsyncTaskHandler {

    public static String id;
    public static EditText name;
    public static EditText desc;
    public static EditText price;

    Button btnReset;
    Button btnCreate;

    public static String up_pid = null;
    public static ProductDTO up_p = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_form);

        name = (EditText)findViewById(R.id.mMenuEdtName);
        desc = (EditText)findViewById(R.id.mMenuEdtDesc);
        price = (EditText)findViewById(R.id.mMenuEdtPrice);
        btnCreate = (Button)findViewById(R.id.mMenuBtnCreate);
        btnReset = (Button)findViewById(R.id.mMenuBtnReset);

        if(getIntent().getStringExtra("id") != null) {
            up_pid = getIntent().getStringExtra("id");
            btnCreate.setText("Update");
            updateMenu();
        }

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getStringExtra("id") != null){
                    Log.e("Clicked: ", "Update");
                    update();
                }else{
                    Log.e("Clicked: ", "Create");
                    createMenu();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                desc.setText("");
                price.setText("");
            }
        });



    }

    public void createMenu(){

        boolean cancel = false;
        View focusView = null;

        ProductDTO p = new ProductDTO();
        p.setName(name.getText().toString());
        p.setDescription((desc.getText().toString()));
        p.setPrice(Integer.parseInt(price.getText().toString()));

        name.setError(null);
        desc.setError(null);
        price.setError(null);

        if(TextUtils.isEmpty(name.getText())){
            name.setError(getString(R.string.error_field_required));
            focusView = name;
            cancel = true;
        }

        if(TextUtils.isEmpty(desc.getText())){
            desc.setError(getString(R.string.error_field_required));
            focusView = desc;
            cancel = true;
        }

        if(TextUtils.isEmpty(price.getText())){
            price.setError(getString(R.string.error_field_required));
            focusView = price;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            new GetMenuTask("create",null, MenuForm.this, null, p);
        }



    }

    public void update(){

        boolean cancel = false;
        View focusView = null;

        ProductDTO p = new ProductDTO();
        p.setId(Integer.parseInt(id));
        p.setName(name.getText().toString());
        p.setDescription((desc.getText().toString()));
        p.setPrice(Integer.parseInt(price.getText().toString()));

        name.setError(null);
        desc.setError(null);
        price.setError(null);

        if(TextUtils.isEmpty(name.getText())){
            name.setError(getString(R.string.error_field_required));
            focusView = name;
            cancel = true;
        }

        if(TextUtils.isEmpty(desc.getText())){
            desc.setError(getString(R.string.error_field_required));
            focusView = desc;
            cancel = true;
        }

        if(TextUtils.isEmpty(price.getText())){
            price.setError(getString(R.string.error_field_required));
            focusView = price;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            Log.e("Update: ", p.toString());
            new GetMenuTask("update",null, MenuForm.this, null, p);
        }



    }

    public void updateMenu(){
        try {

            ProductDTO p = new ProductDTO();
            p.setId(Integer.parseInt(up_pid));

            new GetMenuTask("updateGetForm", null, MenuForm.this, null, p);


        }catch (Exception ex){
            Log.e("Error: ", ex.getMessage());
        }
    }

    @Override
    public void onPostExecute(Object o) {
        up_pid = null;
        up_p = null;
    }
}

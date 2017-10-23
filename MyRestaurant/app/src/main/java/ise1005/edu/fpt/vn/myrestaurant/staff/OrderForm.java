package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;

public class OrderForm extends AppCompatActivity implements IAsyncTaskHandler {

    private ListView listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
    }

    @Override
    public void onPostExecute(Object o) {

    }
}

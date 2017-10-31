package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import ise1005.edu.fpt.vn.myrestaurant.R;

public class PayForm extends AppCompatActivity {

    EditText listPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);
        listPay = (EditText) findViewById(R.id.listPay);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            listPay.setText(b.getString("listPay"));
        } else {
            listPay.setText("don't have any product to pay!!!!");
        }
    }
}

package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;

public class ListTable extends AppCompatActivity implements IAsyncTaskHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);
    }

    @Override
    public void onPostExecute(Object o) {

    }
}

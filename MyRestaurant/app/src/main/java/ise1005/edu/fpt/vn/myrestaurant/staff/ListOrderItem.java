package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.ListOrderDetailAdapter;
import ise1005.edu.fpt.vn.myrestaurant.adapter.ListProductAdapter;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ManagerOrderDetailTask;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ProductListTask;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

public class ListOrderItem extends AppCompatActivity implements IAsyncTaskHandler,View.OnClickListener{

    ArrayList<OrderDetailDTO> dataModels;
    ListView listView;
    private static ListOrderDetailAdapter adapter;
    String id;
    Button mProductBtnAddItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_item);
        mProductBtnAddItem = (Button)findViewById(R.id.mProductBtnAddItem);
        mProductBtnAddItem.setOnClickListener(this);
        listView=(ListView)findViewById(R.id.mTableLv);


        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            this.id  =(String) b.get("id");

        }
        ManagerOrderDetailTask orderDetailTask = new ManagerOrderDetailTask("get",1+"",this,listView, null);

    }

    @Override
    public void onPostExecute(Object o) {
        dataModels= (ArrayList<OrderDetailDTO>) o;
        adapter= new ListOrderDetailAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderDetailDTO dataModel= dataModels.get(position);

                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getDescription()+" Price: "+dataModel.getPrice(), Snackbar.LENGTH_LONG)
                       // .setAction("No action", null).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int getWiget = view.getId();
        if(getWiget == R.id.mProductBtnAddItem){
            Intent i = new Intent(this, FormOrder.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}

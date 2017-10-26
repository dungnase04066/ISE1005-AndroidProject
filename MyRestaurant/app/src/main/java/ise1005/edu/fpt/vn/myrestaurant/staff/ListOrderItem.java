package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class ListOrderItem extends AppCompatActivity implements IAsyncTaskHandler{

    ArrayList<OrderDetailDTO> dataModels;
    ListView listView;
    private static ListOrderDetailAdapter adapter;
    int id;

    public ListOrderItem(int id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_item);

        listView=(ListView)findViewById(R.id.mTableLv);

        ManagerOrderDetailTask orderDetailTask = new ManagerOrderDetailTask("get",id+"",this,listView, null);

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
}

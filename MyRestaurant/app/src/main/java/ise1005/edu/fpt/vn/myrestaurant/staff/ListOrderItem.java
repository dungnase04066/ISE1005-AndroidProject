package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.ListOrderDetailAdapter;
import ise1005.edu.fpt.vn.myrestaurant.adapter.ListProductAdapter;
import ise1005.edu.fpt.vn.myrestaurant.apihelper.JSonHelper;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ManagerOrderDetailTask;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ProductListTask;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

public class ListOrderItem extends AppCompatActivity implements IAsyncTaskHandler, View.OnClickListener {

    ArrayList<OrderDetailDTO> dataModels;
    ListView listView;
    private static ListOrderDetailAdapter adapter;
    String id;
    String tableID;
    Button mProductBtnAddItem;
    Button mProductBtnSummitItem;
    Button mProductBtnCancelItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_item);
        mProductBtnAddItem = (Button) findViewById(R.id.mProductBtnAddItem);
        mProductBtnAddItem.setOnClickListener(this);

        mProductBtnCancelItem = (Button) findViewById(R.id.mProductBtnCancel);
        mProductBtnCancelItem.setOnClickListener(this);

        mProductBtnSummitItem = (Button) findViewById(R.id.mProductBtnSubmit);
        mProductBtnSummitItem.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.mTableLv);


        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            this.id = "0";//(String) b.get("id");
            this.tableID = (String) b.getString("table_id");
        } else {
            this.id = "1";
            this.tableID = "2";
        }
        ManagerOrderDetailTask orderDetailTask = new ManagerOrderDetailTask("get", this.tableID, this, listView, null,null);

    }

    @Override
    public void onPostExecute(Object o) {
        dataModels = (ArrayList<OrderDetailDTO>) o;
        adapter = new ListOrderDetailAdapter(dataModels, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderDetailDTO dataModel = dataModels.get(position);
                Intent intent = new Intent(getApplicationContext(), UpdateOrderDetail.class);
                intent.putExtra("orderDetailDTO", dataModel);
                intent.putExtra("id", position);
                startActivityForResult(intent, 2);
                //Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getDescription()+" Price: "+dataModel.getPrice(), Snackbar.LENGTH_LONG)
                // .setAction("No action", null).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int getWiget = view.getId();
        if (getWiget == R.id.mProductBtnAddItem) {
            Intent intent = new Intent(this, FormOrder.class);

            startActivityForResult(intent, 1);
            return;
        }
        if (getWiget == R.id.mProductBtnSubmit) {
            try {
                ManagerOrderDetailTask orderDetailTask = new ManagerOrderDetailTask("create", tableID, this, listView, null,JSonHelper.parseJsonOrderDetail(dataModels));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }

        if (getWiget == R.id.mProductBtnCancel) {
            Intent intent = new Intent(this, TableActivity.class);
            startActivity(intent);
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ProductDTO productDTO = (ProductDTO) bundle.getSerializable("productDTO");
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setProduct(productDTO);
                orderDetailDTO.setPrice(productDTO.getPrice());
                orderDetailDTO.setQuantity(1);
                orderDetailDTO.setProduct_id(productDTO.getId());
                dataModels.add(orderDetailDTO);
                onPostExecute(dataModels);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                OrderDetailDTO orderDetailDTO = (OrderDetailDTO) bundle.getSerializable("orderDetailDTO");
                int id = bundle.getInt("id");
                dataModels.set(id, orderDetailDTO);
                onPostExecute(dataModels);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}

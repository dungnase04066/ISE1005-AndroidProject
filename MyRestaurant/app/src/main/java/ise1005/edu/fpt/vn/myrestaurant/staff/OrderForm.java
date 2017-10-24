package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.ListProductAdapter;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ProductListTask;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

public class OrderForm extends AppCompatActivity implements IAsyncTaskHandler {

    ArrayList<ProductDTO> dataModels;
    ListView listView;
    private static ListProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.mTableLv);

        ProductListTask productListTask = new ProductListTask(this);
        productListTask.execute();
    }

    @Override
    public void onPostExecute(Object o) {
        dataModels= (ArrayList<ProductDTO>) o;
        adapter= new ListProductAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductDTO dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getDescription()+" Price: "+dataModel.getPrice(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}

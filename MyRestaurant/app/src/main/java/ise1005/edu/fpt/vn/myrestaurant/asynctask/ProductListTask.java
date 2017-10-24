package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

/**
 * Created by authinh on 10/24/17.
 */

public class ProductListTask extends AsyncTask<Void, Void, Boolean> {
    List<ProductDTO> listProduct =  new ArrayList<>();
    String url = Constants.API_URL+"product/get/";
    private IAsyncTaskHandler container;
    private String name;


    public ProductListTask(String name, IAsyncTaskHandler container) {
        this.name = name;
        this.container = container;
    }
    
    @Override
    protected Boolean doInBackground(Void... voids) {
        listProduct = new ArrayList<>();
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.get(url);
        if(jsonStr !=null) {
            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray results = jsonObject.getJSONArray("result");
                for(int i=0;i<results.length();i++) {
                    JSONObject result = results.getJSONObject(i);
                    ProductDTO product;
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    Log.d("111111",name);
                    String description = result.getString("description");
                    double price = result.getDouble("price");
                    product = new ProductDTO(id,name,price,description);
                    listProduct.add(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(listProduct);
    }
}

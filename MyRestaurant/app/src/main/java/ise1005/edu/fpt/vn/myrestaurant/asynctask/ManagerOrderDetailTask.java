package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.InterpolatorRes;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;
import ise1005.edu.fpt.vn.myrestaurant.manager.MenuForm;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 10/25/2017.
 */

public class ManagerOrderDetailTask {


    public ManagerOrderDetailTask(String method, String txtSearch, IAsyncTaskHandler container, ListView lv, OrderDetailDTO p) {

        if(method.equals("get")){
            new getOrderDetail(txtSearch, container, lv).execute((Void) null);
        }
        else if(method.equals("create")){
            new createOrderDetail( container, p).execute((Void) null);
        }
        else if(method.equals("delete")){
            new deleteOrderDetail(p).execute((Void) null);
        }
        else if(method.equals("updateGetForm")){
            new UpdateOrderDetail(container, p).execute((Void) null);
        }
        else  if(method.equals("update")){
            new Update(container, p).execute((Void) null);
        }
    }

    class getOrderDetail extends AsyncTask<Void, Void, Boolean> {

        private final String txtSearch;
        private final IAsyncTaskHandler container;
        private final List<OrderDetailDTO> lstMenus;
        private JSONObject oneMenu;
        private Activity activity;
        private ListView lv;

        public getOrderDetail(String txtSearch, IAsyncTaskHandler container, ListView lv){
            this.txtSearch = txtSearch;
            this.container = container;
            activity = (Activity)container;
            lstMenus = new ArrayList<OrderDetailDTO>();
            this.lv = lv;

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try{
                String json = httpHandler.get(Constants.API_URL + "orderdetail/get/?id=" + txtSearch);
                JSONObject jsonObj = new JSONObject(json);
                JSONArray menus = jsonObj.getJSONArray("result");

                lstMenus.clear();

                for(int i=0;i<menus.length();i++){
                    oneMenu = menus.getJSONObject(i);
                    String id = oneMenu.getString("id");
                    String orderId = oneMenu.getString("order_id");
                    String product_id = oneMenu.getString("product_id");
                    String quantity = oneMenu.getString("quantity");
                    String price = oneMenu.getString("price");
                    String note = oneMenu.getString("note");
                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setId(Integer.parseInt(id));
                    orderDetailDTO.setNote(note);
                    orderDetailDTO.setOrder_id(Integer.parseInt(orderId));
                    orderDetailDTO.setProduct_id(Integer.parseInt(product_id));
                    orderDetailDTO.setQuantity(Integer.parseInt(quantity));
                    orderDetailDTO.setPrice(Double.parseDouble(price));
                    lstMenus.add(orderDetailDTO);
                }

            }catch (Exception ex){
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            container.onPostExecute(lstMenus);
        }
    }

    class createOrderDetail extends AsyncTask<Void, Void, Boolean> {

        IAsyncTaskHandler container;
        OrderDetailDTO p;
        Activity activity;
        boolean success = false;

        public createOrderDetail(IAsyncTaskHandler container, OrderDetailDTO p){
            this.container = container;
            this.activity = (Activity)container;
            this.p = p;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try{
                JSONObject formData = new JSONObject();
                //formData.put("name", p.getName());
                //formData.put("description", p.getDescription());
                formData.put("price", ""+p.getPrice());
                String json = httpHandler.post(Constants.API_URL + "product/create/", formData.toString());
                JSONObject jsonObj = new JSONObject(json);
                if (jsonObj.getInt("size") > 0) {
                    success = true;
                }

            }catch (Exception ex){
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Intent intent = new Intent();
            if(success)
                intent.putExtra("result", "success!");
            else
                intent.putExtra("result", "failed");
            this.activity.setResult(RESULT_OK, intent);
            this.activity.finish();
        }
    }

    class deleteOrderDetail extends AsyncTask<Void, Void, Boolean> {

        OrderDetailDTO p;
        boolean success;

        public deleteOrderDetail(OrderDetailDTO p){
            this.p = p;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try{
                JSONObject formData = new JSONObject();
                formData.put("id",p.getId());
                String json = httpHandler.post(Constants.API_URL + "product/delete/", formData.toString());
                JSONObject jsonObj = new JSONObject(json);
                if (!jsonObj.getBoolean("hasErr")) {
                    success = true;
                }

            }catch (Exception ex){
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(success)
                Log.e("Result: ", "YES!");
            else
                Log.e("Result: ", "Nah!");
        }
    }

    class UpdateOrderDetail extends AsyncTask<Void, Void, Boolean> {

        OrderDetailDTO p;
        OrderDetailDTO p_i;
        IAsyncTaskHandler container;
        Activity activity;

        public String id;
        public String name;
        public String desc;
        public String price;


        public UpdateOrderDetail(IAsyncTaskHandler container , OrderDetailDTO p){
            this.container = container;
            activity = (Activity)container;
            this.p = p;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try{

                String json = httpHandler.get(Constants.API_URL + "product/get/?id="+p.getId());
                JSONObject jsonObj = new JSONObject(json);
                JSONArray menus = jsonObj.getJSONArray("result");

                JSONObject oneMenu = menus.getJSONObject(0);
                id = oneMenu.getString("id");
                name = oneMenu.getString("name");
                desc = oneMenu.getString("description");
                price = oneMenu.getString("price");
                //p_i = new OrderDetailDTO(Integer.parseInt(id),name,Double.parseDouble(price), desc);

               // MenuForm.up_p = p_i;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MenuForm.id = id.toString();
                        MenuForm.name.setText(name.toString());
                        MenuForm.desc.setText(desc.toString());
                        MenuForm.price.setText(price.toString());
                    }
                });


                Log.e("PPP: ", MenuForm.up_p.toString());


            }catch (Exception ex){
                Log.e("Update-Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.e("UpdateMenu: ", p_i.toString());
        }
    }

    class Update extends AsyncTask<Void, Void, Boolean> {

        IAsyncTaskHandler container;
        OrderDetailDTO p;
        Activity activity;
        boolean success = false;

        public Update(IAsyncTaskHandler container, OrderDetailDTO p){
            this.container = container;
            this.activity = (Activity)container;
            this.p = p;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try{
                Log.e("Update-Async", p.toString());
                JSONObject formData = new JSONObject();
                formData.put("id",p.getId());
                //formData.put("name", p.getName());
                //formData.put("description", p.getDescription());
                formData.put("price", ""+p.getPrice());
                String json = httpHandler.post(Constants.API_URL + "product/update/", formData.toString());
                JSONObject jsonObj = new JSONObject(json);
                if (jsonObj.getInt("size") > 0) {
                    success = true;
                }

            }catch (Exception ex){
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            container.onPostExecute(aBoolean);
        }
    }
}

package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.apihelper.JSonHelper;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;
import ise1005.edu.fpt.vn.myrestaurant.manager.MenuForm;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 10/25/2017.
 */

public class ManagerOrderDetailTask {


    public ManagerOrderDetailTask(String method, String txtSearch, IAsyncTaskHandler container, ListView lv, OrderDetailDTO p, JSONArray jsonArray, HashMap<String,Object> mapValue) {

        if(method.equals("get")){
            new getOrderDetail(txtSearch, container, lv).execute();
        }
        else if(method.equals("create")){
            new createOrderDetail( container, jsonArray, mapValue).execute();
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

        private final String table_id;
        private final IAsyncTaskHandler container;
        private List<OrderDetailDTO> lstMenus;
        private Activity activity;
        private ListView lv;

        public getOrderDetail(String txtSearch, IAsyncTaskHandler container, ListView lv){
            this.table_id = txtSearch;
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
                String json = httpHandler.get(Constants.API_URL + "orderdetail/get/?table_id=" + table_id);
                JSONObject jsonObj = new JSONObject(json);
                JSONArray orderDetailResult = jsonObj.getJSONArray("result");
                lstMenus.clear();

                for(int i=0;i<orderDetailResult.length();i++){
                    JSONObject oneMenu = orderDetailResult.getJSONObject(i);
                    String id = oneMenu.getString("id");
                    String orderId = intiOrderID(table_id);
                    String product_id = oneMenu.getString("product_id");
                    String quantity = oneMenu.getString("quantity");
                    String price = oneMenu.getString("price");
                    String note = oneMenu.getString("note");
                    JSONObject productResult = oneMenu.getJSONObject("product");

                    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setId(Integer.parseInt(id));
                    orderDetailDTO.setNote(note);
                    orderDetailDTO.setOrder_id(Integer.parseInt(orderId));
                    orderDetailDTO.setProduct_id(Integer.parseInt(product_id));
                    orderDetailDTO.setQuantity(Integer.parseInt(quantity));
                    orderDetailDTO.setPrice(Double.parseDouble(price));
                    orderDetailDTO.setProduct(JSonHelper.get(productResult));

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

        public String intiOrderID(String tableID){
            HttpHandler httpHandler = new HttpHandler();
            try{
                String json = httpHandler.get(Constants.API_URL + "order/get/?table_id=" + tableID +"&status=0");
                JSONObject jsonObj = new JSONObject(json);
                JSONArray orderDetailResult = jsonObj.getJSONArray("result");
                lstMenus.clear();
                if (jsonObj.getInt("size") > 0 ){
                    JSONObject oneMenu = orderDetailResult.getJSONObject(0);
                    return oneMenu.getString("id");
                }
            }catch (Exception ex){
                Log.e("Error:", ex.getMessage());
            }
            return "-1";
        }

    }

    class createOrderDetail extends AsyncTask<Void, Void, Boolean> {

        IAsyncTaskHandler container;
        OrderDetailDTO p;
        Activity activity;
        boolean success = false;
        String tableID;
        JSONArray jsonArray;
        HashMap<String,Object> mapValue;

        public createOrderDetail(IAsyncTaskHandler container, JSONArray jsonArray,HashMap<String,Object> mapValue){
            this.container = container;
            this.activity = (Activity)container;
            this.mapValue = mapValue;
            this.jsonArray = jsonArray;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try{
                JSONObject formData = new JSONObject();
                if(!mapValue.get("id").toString().equals("-1")) {
                    formData.put("id", mapValue.get("id"));
                }
                formData.put("user_id", Session.currentUser.getId());
                formData.put("table_id",mapValue.get("tableID"));
                formData.put("order_detail",jsonArray);
                String json = httpHandler.post(Constants.API_URL + "order/create/", formData.toString());
                JSONObject jsonObj = new JSONObject(json);
                if (jsonObj.getInt("size") > 0) {
                    success = true;
                }
                Log.d("order",formData.toString());
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

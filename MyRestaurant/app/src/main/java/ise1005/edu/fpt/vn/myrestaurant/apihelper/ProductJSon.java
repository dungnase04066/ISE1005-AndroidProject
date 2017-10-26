package ise1005.edu.fpt.vn.myrestaurant.apihelper;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ise1005.edu.fpt.vn.myrestaurant.asynctask.HttpHandler;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.UserDTO;

/**
 * Created by Admin on 10/27/2017.
 */

public class ProductJSon {


    public static ProductDTO get(JSONObject obj){
        ProductDTO productDTO = new ProductDTO();
        try {
            productDTO.setDescription(obj.getString("description"));
            productDTO.setName(obj.getString("name"));
            productDTO.setId(Integer.parseInt(obj.getString("id")));
            productDTO.setPrice(Double.parseDouble(obj.getString("price")));
            return productDTO;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}

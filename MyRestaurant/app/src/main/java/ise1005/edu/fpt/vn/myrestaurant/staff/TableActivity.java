package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.TableStaffAdapter;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.TableListTask;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

public class TableActivity extends AppCompatActivity implements IAsyncTaskHandler {
    private ListView listTable;
   // private List<TableDTO> TableList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_table);
        listTable = (ListView) findViewById(R.id.lstTable);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        TableListTask tableListTask = new TableListTask("", this);
        tableListTask.execute();

    }

    @Override
    public void onPostExecute(Object o) {
       // Log.d("--------", lstTable.toString());
        List<TableDTO> lstTable = (List<TableDTO>) o;
        Log.d("ISE1005", lstTable.toString());
//        TableDTO[] tables = new TableDTO[lstTable.size()];
//        Log.d("----", tables.toString());
//        for(int i=0;i<lstTable.size();i++) {
//            tables[i] = lstTable.get(i);
//            Log.d("3333", tables[i].toString());
//        }
//        ArrayAdapter<TableDTO> arrayAdapter = new ArrayAdapter<TableDTO>(this, R.layout.table_list_row, tables);
        TableStaffAdapter arrayAdapter = new TableStaffAdapter(lstTable, this);
        listTable.setAdapter(arrayAdapter);
    }
}

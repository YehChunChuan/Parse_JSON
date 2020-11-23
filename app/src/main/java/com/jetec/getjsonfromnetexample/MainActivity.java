package com.jetec.getjsonfromnetexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName()+"My";
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
//
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //必须先设置LayoutManager
        RecyclerView recyclerView;
        MyAdapter myAdapter;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        catchData();
        //testChineseIndex(); //測試中文索引
//123212313213
        //132123
//789798798
        //Try to github
        //Try Push To Github By Souretree
    }

    private void testChineseIndex() {
        String catchData = "https://datacenter.taichung.gov.tw/Swagger/OpenData/44ff471a-8bda-429d-b5ba-29eace7b05ed?limit=10";
        //"https://datacenter.taichung.gov.tw/swagger/yaml/387290000H";
        new Thread(()->{
            try {


//                JsonParser parser=new JsonParser();
//                JsonElement je=parser.parse(jsonStr);
//                Gson gson=new GsonBuilder().setPrettyPrinting().serializeNulls().create();
//                return gson.toJson(je);



                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String line = in.readLine();
                StringBuffer json = new StringBuffer();
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }
                JSONArray jsonArray= new JSONArray(String.valueOf(json));
                Log.d(TAG, "onCreate: "+jsonArray);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String data = jsonObject.getString("RptName");//"路口");
                    Log.d(TAG, "onCreate: RPT名稱"+data);
                }

            }catch (Exception x){
                Log.d(TAG, "onCreate: "+x);
            }

        }).start();
    }

    private void catchData(){
        String catchData = "https://datacenter.taichung.gov.tw/Swagger/OpenData/44ff471a-8bda-429d-b5ba-29eace7b05ed?limit=10";//https://opendata.taichung.gov.tw/dataset/954b9a7e-59d8-4f68-962f-f6bd222c9e1c/resource/b07b249e-e2f5-4dd7-b2e4-4f1aaa65d5d9/view/f43b70f6-b9ef-437c-a8a6-cabc205dc982";//"https://api.myjson.com/bins/15majc";
        ProgressDialog dialog = ProgressDialog.show(this,"讀取中"
                ,"請稍候",true);
        new Thread(()->{
            try {
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();    //URL連接物件
                InputStream is = connection.getInputStream();                               //輸入串流
                BufferedReader in = new BufferedReader(new InputStreamReader(is));          //緩衝讀取器
                String line = in.readLine();                                                //一次讀一行
                StringBuffer json = new StringBuffer();                                     //字串緩衝器
                while (line != null) {
                    json.append(line);
                    line = in.readLine();
                }

                JSONArray jsonArray= new JSONArray(String.valueOf(json));                   //Json陣列物件
                for (int i =0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);                     //Json物件的物件
                    String RptNo = jsonObject.getString("RptNo");
                    String RptName = jsonObject.getString("RptName");
                    String StatCourseNo = jsonObject.getString("StatCourseNo");
                    String StatCourseName = jsonObject.getString("StatCourseName");
                    String DataDate = jsonObject.getString("DataDate");
                    String PlaceNo = jsonObject.getString("PlaceNo");
                    String PlaceName = jsonObject.getString("PlaceName");
                    String PeriodNo = jsonObject.getString("PeriodNo");
                    String PeriodName = jsonObject.getString("PeriodName");
                    String Complex1 = jsonObject.getString("Complex1");
                    String ComplexName = jsonObject.getString("ComplexName");
                    String Complex2 = jsonObject.getString("Complex2");
                    String Complex2Name = jsonObject.getString("Complex2Name");
                    String Complex3 = jsonObject.getString("Complex3");
                    String Complex3Name = jsonObject.getString("Complex3Name");
                    String Complex4 = jsonObject.getString("Complex4");
                    String Complex4Name = jsonObject.getString("Complex4Name");
                    String Complex5 = jsonObject.getString("Complex5");
                    String Complex5Name = jsonObject.getString("Complex5Name");
                    String DeriveNo = jsonObject.getString("DeriveNo");
                    String DeriveExplain = jsonObject.getString("DeriveExplain");
                    String FValue = jsonObject.getString("FValue");
                    String SValue = jsonObject.getString("SValue");
                    String RptDeptNo = jsonObject.getString("RptDeptNo");
                    String RptDeptName = jsonObject.getString("RptDeptName");
                    String CreateTime = jsonObject.getString("CreateTime");
                    String ModifyTime = jsonObject.getString("ModifyTime");


                    //記得這也要新增近去HashMap中
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("PlaceName",PlaceName);
                    hashMap.put("DataDate",DataDate);
                    hashMap.put("Car",ComplexName);
                    hashMap.put("Type",Complex2Name);
                    hashMap.put("Price",Complex3Name);
                    hashMap.put("StatCourseName",StatCourseName);
                    hashMap.put("CreateTime",CreateTime);
                    arrayList.add(hashMap);
                }
                Log.d(TAG, "catchData: "+arrayList);

                runOnUiThread(()->{
                    dialog.dismiss();
                        RecyclerView recyclerView;
                    MyAdapter myAdapter;
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                    myAdapter = new MyAdapter();
                    recyclerView.setAdapter(myAdapter);
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvPos,tvType,tvPrice,tvCar,tvDateTime,tvStatCourseName,tvCreateTime;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvPos = itemView.findViewById(R.id.textView_pos);
                tvType = itemView.findViewById(R.id.textView_type);
                tvPrice = itemView.findViewById(R.id.textView_price);
                tvCar = itemView.findViewById(R.id.textView_car);
                tvDateTime = itemView.findViewById(R.id.textView_time);
                tvStatCourseName = itemView.findViewById(R.id.textView_StatCourseName);
                tvCreateTime = itemView.findViewById(R.id.textView_CreateTime);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recyclerview_item,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvPos.setText(arrayList.get(position).get("PlaceName"));
            holder.tvType.setText("類型："+arrayList.get(position).get("Type"));
            holder.tvPrice.setText("收費與否："+arrayList.get(position).get("Price"));
            holder.tvCar.setText("停放種類："+arrayList.get(position).get("Car"));
            holder.tvDateTime.setText("新增資料時間："+arrayList.get(position).get("DataDate"));
            holder.tvStatCourseName.setText("宣導事項:："+arrayList.get(position).get("StatCourseName"));
            holder.tvCreateTime.setText("創建時間:："+arrayList.get(position).get("CreateTime"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


    }
}
